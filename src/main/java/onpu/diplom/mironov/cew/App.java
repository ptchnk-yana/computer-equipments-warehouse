package onpu.diplom.mironov.cew;

import com.tacitknowledge.util.migration.MigrationException;
import com.tacitknowledge.util.migration.jdbc.DataSourceMigrationContext;
import com.tacitknowledge.util.migration.jdbc.DatabaseType;
import com.tacitknowledge.util.migration.jdbc.JdbcMigrationLauncher;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import onpu.diplom.mironov.cew.actions.AboutAction;
import onpu.diplom.mironov.cew.actions.AbstractCewAction;
import onpu.diplom.mironov.cew.actions.ActionEnum;
import onpu.diplom.mironov.cew.actions.DeleteAction;
import onpu.diplom.mironov.cew.actions.DeviceListAction;
import onpu.diplom.mironov.cew.actions.DeviceTypeListAction;
import onpu.diplom.mironov.cew.actions.ExitAction;
import onpu.diplom.mironov.cew.actions.MainTalbeSelectionListener;
import onpu.diplom.mironov.cew.actions.NewDeviceAction;
import onpu.diplom.mironov.cew.actions.NewDeviceTypeAction;
import onpu.diplom.mironov.cew.actions.NewRoomAction;
import onpu.diplom.mironov.cew.actions.NewUserAction;
import onpu.diplom.mironov.cew.actions.PrintAction;
import onpu.diplom.mironov.cew.actions.RoomListAction;
import onpu.diplom.mironov.cew.actions.SaveAction;
import onpu.diplom.mironov.cew.actions.UserListAction;
import onpu.diplom.mironov.cew.actions.WhoAmIAction;
import onpu.diplom.mironov.cew.bean.User;
import onpu.diplom.mironov.cew.bean.UserPrivilege;
import onpu.diplom.mironov.cew.dao.DeviceDao;
import onpu.diplom.mironov.cew.dao.DeviceTypeDao;
import onpu.diplom.mironov.cew.dao.RoomDao;
import onpu.diplom.mironov.cew.dao.UserDao;
import onpu.diplom.mironov.cew.dao.jdbi.DeviceJdbiDao;
import onpu.diplom.mironov.cew.dao.jdbi.DeviceTypeJdbiDao;
import onpu.diplom.mironov.cew.dao.jdbi.RoomJdbiDao;
import onpu.diplom.mironov.cew.dao.jdbi.UserJdbiDao;
import onpu.diplom.mironov.cew.model.MainTableModel;
import onpu.diplom.mironov.cew.view.AuthenticationPanel;
import onpu.diplom.mironov.cew.view.MainFrame;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;

/**
 * Entry point into application
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);
        String lnfClass = null;
        File baseDir = new File(new File(System.getProperty("user.home")), 
                ".mironov_dyplom");

        int lnfIndex = argsList.indexOf("--lnf");
        if (lnfIndex > 0 && argsList.size() > lnfIndex + 1) {
            lnfClass = argsList.get(lnfIndex + 1);
        }

        int dataIndex = argsList.indexOf("--data");
        if (dataIndex > 0 && argsList.size() > dataIndex + 1) {
            baseDir = new File(argsList.get(dataIndex + 1));
        }

        if (argsList.contains("--help")) {
            System.out.println("Use: java [-cp libs] -jar computer-equipments-"
                    + "warehouse-1.1-SNAPSHOT.jar [--lnf LoockAndFeelClass] [-"
                    + "-data path] [--help]");
            System.out.println("\t--lnf LoockAndFeelClass\t set loock and feel"
                    + ". See more on https://docs.oracle.com/javase/tutorial/u"
                    + "iswing/lookandfeel/plaf.html");
            System.out.println("\t--data path\t set paht to data storage (loca"
                    + "tion of database files). By default it's " + baseDir);
            System.out.println("\t--help\t display this message and exit");
        } else {
            new App().run(lnfClass, baseDir);
        }
    }

    public App() {
    }

    public void run(String lnfClass, File baseDir) {
        initLnf(lnfClass);

        Properties text = loadTexts();
        Properties appProperties = loadAppProperties();

        JdbcConnectionPool ds = JdbcConnectionPool.create(
                appProperties.getProperty("jdbc.url"),
                appProperties.getProperty("jdbc.username"),
                appProperties.getProperty("jdbc.password"));
        runAutopatcher(ds, appProperties);
        DBI dbi = new DBI(ds);

        UserDao userDao = new UserJdbiDao(dbi);
        DeviceTypeDao deviceTypeDao = new DeviceTypeJdbiDao(dbi);
        RoomDao roomDao = new RoomJdbiDao(dbi);
        DeviceDao deviceDao = new DeviceJdbiDao(dbi);

        createDefaultAdminIfItNotExist(userDao, text);

        User currentUser = authentication(userDao, text);

        MainFrame mainFrame = new MainFrame();
        MainTableModel mainTableModel = new MainTableModel(text, currentUser,
                creatSspecialPrivilagesForColumnsMap());

        Map<ActionEnum, AbstractCewAction> actions
                = new EnumMap<ActionEnum, AbstractCewAction>(ActionEnum.class);
        MainTalbeSelectionListener selectionListener = new MainTalbeSelectionListener(
                mainFrame, actions, currentUser, mainTableModel);
        fillActionsMap(actions, currentUser, mainFrame, mainTableModel, text, 
                selectionListener, userDao, roomDao, deviceTypeDao, deviceDao);

        mainFrame.init(text, toolBarActions(actions), menuActions(text, actions), 
                actions.get(ActionEnum.EXIT), mainTableModel, selectionListener);

        selectionListener.valueChanged(null);
        mainFrame.setVisible(true);
    }

    public void runAutopatcher(JdbcConnectionPool ds, Properties appProperties) {
        JdbcMigrationLauncher launcher = new JdbcMigrationLauncher();
        launcher.setPatchPath("db/patches");
        launcher.setReadOnly(false);
        DataSourceMigrationContext context = new DataSourceMigrationContext();
        context.setDataSource(ds);
        context.setDatabaseType(new DatabaseType(appProperties.getProperty("migration.jdbc.type")));
        context.setSystemName(appProperties.getProperty("migration.jdbc.system.name"));
        launcher.addContext(context);
        try {
            launcher.doMigrations();
        } catch (MigrationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void fillActionsMap(Map<ActionEnum, AbstractCewAction> actions,
            User currentUser, MainFrame mainFrame, MainTableModel mainTableModel,
            Properties text, MainTalbeSelectionListener selectionListener,
            UserDao userDao, RoomDao roomDao, DeviceTypeDao deviceTypeDao,
            DeviceDao deviceDao) {
        actions.put(ActionEnum.ABOUT, new AboutAction(currentUser, mainFrame,
                mainTableModel, actions, text));
        actions.put(ActionEnum.DEVICE_LIST, new DeviceListAction(currentUser,
                mainFrame, mainTableModel, actions, text, deviceDao));
        actions.put(ActionEnum.DEVICE_TYPE_LIST, new DeviceTypeListAction(
                currentUser, mainFrame, mainTableModel, actions, text, deviceTypeDao));
        actions.put(ActionEnum.EXIT, new ExitAction(currentUser, mainFrame,
                mainTableModel, actions, text));
        actions.put(ActionEnum.NEW_DEVICE, new NewDeviceAction(currentUser,
                mainFrame, mainTableModel, actions, text, deviceDao));
        actions.put(ActionEnum.NEW_DEVICE_TYPE, new NewDeviceTypeAction(currentUser,
                mainFrame, mainTableModel, actions, text, deviceTypeDao));
        actions.put(ActionEnum.NEW_ROOM, new NewRoomAction(currentUser, mainFrame,
                mainTableModel, actions, text, roomDao, userDao));
        actions.put(ActionEnum.NEW_USER, new NewUserAction(currentUser, mainFrame,
                mainTableModel, actions, text, userDao));
        actions.put(ActionEnum.PRINT, new PrintAction(currentUser, mainFrame,
                mainTableModel, actions, text));
        actions.put(ActionEnum.ROOM_LIST, new RoomListAction(currentUser, mainFrame,
                mainTableModel, actions, text, roomDao));
        actions.put(ActionEnum.SAVE, new SaveAction(currentUser, mainFrame,
                mainTableModel, actions, text));
        actions.put(ActionEnum.USER_LIST, new UserListAction(currentUser, mainFrame,
                mainTableModel, actions, text, userDao));
        actions.put(ActionEnum.WHO_AM_I, new WhoAmIAction(currentUser, mainFrame,
                mainTableModel, actions, text));
        actions.put(ActionEnum.DELETE, new DeleteAction(currentUser, mainFrame,
                mainTableModel, actions, text, selectionListener, userDao, roomDao,
                deviceTypeDao, deviceDao));
    }

    private Map<String, UserPrivilege> creatSspecialPrivilagesForColumnsMap() {
        Map<String, UserPrivilege> specialPrivilagesForColumns
                = new HashMap<String, UserPrivilege>();
        specialPrivilagesForColumns.put("userId", UserPrivilege.ADMIN);
        specialPrivilagesForColumns.put("userName", UserPrivilege.ADMIN);
        specialPrivilagesForColumns.put("userPrivilege", UserPrivilege.ADMIN);
        specialPrivilagesForColumns.put("deviceTypeId", UserPrivilege.ADMIN);
        specialPrivilagesForColumns.put("roomId", UserPrivilege.ADMIN);
        return specialPrivilagesForColumns;
    }

    private User authentication(UserDao userDao, Properties text) throws HeadlessException {
        User currentUser = null;
        List<User> users = userDao.list();
        Map<String, User> userPswds = new HashMap<String, User>();
        for (User user : users) {
            userPswds.put(user.getName(), user);
        }
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("name"));
        JTextField jTextField = new JTextField("");
        panel.add(jTextField);
        panel.add(new JLabel("password"));
        JTextField jTextField1 = new JTextField("");
        panel.add(jTextField1);
        AuthenticationPanel panel1 = new AuthenticationPanel();
        while (currentUser == null && JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(
                null,
                panel1,
                text.getProperty("authentication.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{text.getProperty("yes"), text.getProperty("no")},
                null)) {
            String nameText = panel1.getNameText();
            User existingUser = userPswds.get(nameText);
            if (existingUser == null
                    || !existingUser.getPassword().equals(panel1.getPasswordText())) {
                JOptionPane.showMessageDialog(null,
                        text.getProperty("authentication.wrong"),
                        text.getProperty("authentication.title"),
                        JOptionPane.ERROR_MESSAGE);
            } else {
                currentUser = existingUser;
            }
        }
        if (currentUser == null) {
            System.exit(0);
        }
        return currentUser;
    }

    private void createDefaultAdminIfItNotExist(UserDao userDao, Properties text) {
        List<User> users = userDao.list();
        if (users.isEmpty()) {
            NewUserAction newUserAction = new NewUserAction(
                    new User(null, null, UserPrivilege.ADMIN), null, null, null,
                    text, userDao);
            JOptionPane.showMessageDialog(null, text.getProperty("noUser"),
                    text.getProperty("authentication.title"), JOptionPane.INFORMATION_MESSAGE);
            User defaultAdmin = null;
            while (defaultAdmin == null) {
                defaultAdmin = newUserAction.createUser(users);
            }
            defaultAdmin.setPrivilege(UserPrivilege.ADMIN);
            userDao.add(defaultAdmin);
        }
    }

    private Properties loadTexts() {
        Properties text = new Properties();
        InputStream inStream = getClass().getResourceAsStream("/i18n/ru.properties");
        try {
            text.load(inStream);
            inStream.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
        return text;
    }

    private Properties loadAppProperties() {
        Properties properties = new Properties();
        InputStream inStream = getClass().getResourceAsStream("/application.properties");
        try {
            properties.load(inStream);
            inStream.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
        StrSubstitutor substitutor = new StrSubstitutor((Map)System.getProperties());
        for(String key : properties.stringPropertyNames()) {
            properties.setProperty(key, substitutor.replace(properties.getProperty(key)));
        }
        return properties;
    }

    private void initLnf(String lnfClass) {
        try {
            if (lnfClass == null) {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } else {
                UIManager.setLookAndFeel(lnfClass);
            }
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (UnsupportedLookAndFeelException ex) {
        }
    }

    private List<? extends AbstractAction> toolBarActions(
            Map<ActionEnum, AbstractCewAction> actions) {
        return Arrays.asList(actions.get(ActionEnum.USER_LIST), 
                actions.get(ActionEnum.ROOM_LIST), 
                actions.get(ActionEnum.DEVICE_LIST),
                actions.get(ActionEnum.NEW_USER),
                actions.get(ActionEnum.NEW_ROOM),
                actions.get(ActionEnum.NEW_DEVICE),
                actions.get(ActionEnum.WHO_AM_I));
    }

    private Map<String, List<? extends AbstractAction>> menuActions(
            Properties text, Map<ActionEnum, AbstractCewAction> actions) {
        Map<String, List<? extends AbstractAction>> menu = 
                new LinkedHashMap<String, List<? extends AbstractAction>>();
        menu.put(text.getProperty("menu.file"), Arrays.asList(
                actions.get(ActionEnum.SAVE), 
                actions.get(ActionEnum.PRINT),
                actions.get(ActionEnum.EXIT)));
        menu.put(text.getProperty("menu.list"), Arrays.asList(
                actions.get(ActionEnum.USER_LIST), 
                actions.get(ActionEnum.ROOM_LIST),
                actions.get(ActionEnum.DEVICE_TYPE_LIST),
                actions.get(ActionEnum.DEVICE_LIST)));
        menu.put(text.getProperty("menu.new"), Arrays.asList(
                actions.get(ActionEnum.NEW_USER), 
                actions.get(ActionEnum.NEW_ROOM),
                actions.get(ActionEnum.NEW_DEVICE_TYPE),
                actions.get(ActionEnum.NEW_DEVICE),
                actions.get(ActionEnum.DELETE)));
        menu.put(text.getProperty("menu.help"), Arrays.asList(
                actions.get(ActionEnum.WHO_AM_I), 
                actions.get(ActionEnum.ABOUT)));
        return menu;
    }
}
