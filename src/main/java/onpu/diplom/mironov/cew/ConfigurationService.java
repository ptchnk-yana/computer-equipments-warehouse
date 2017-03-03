package onpu.diplom.mironov.cew;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import static onpu.diplom.mironov.cew.CewUtil.checkNotNull;

public class ConfigurationService {
    public static final String CONFIG_FILE_PROPERTY_KEY = "config.file";
    public static final String CONFIG_FILE_NAME = "config.properties";

    public static final String PROPERTY_ORGANIZATION = "organization";

    private final Properties applicationProperties;
    private final Properties applicationConfiguration;
    private final File configFile;

    public ConfigurationService(Properties applicationProperties, File baseDir) {
        this.applicationProperties = (Properties) checkNotNull(applicationProperties, 
                "applicationProperties").clone();
        this.configFile = getConfigFile(checkNotNull(baseDir, "baseDir"));
        this.applicationConfiguration = loadConfProperties();
    }

    public String getOrganization() {
        return get(PROPERTY_ORGANIZATION);
    }

    public void setOrganization(String organizationName) {
        set(PROPERTY_ORGANIZATION, organizationName);
    }

    public String get(String propertyName) {
        return applicationConfiguration.getProperty(
                checkNotNull(propertyName, "propertyName"),
                applicationProperties.getProperty(propertyName));
    }

    public void set(String propertyName, String propertyValue) {
        applicationConfiguration.setProperty(
                checkNotNull(propertyName, "propertyName"), 
                checkNotNull(propertyValue, "propertyName"));
        storeConfProperties();
    }

    private File getConfigFile(File baseDir) {
        return applicationProperties.containsKey(CONFIG_FILE_PROPERTY_KEY)
                ? new File(applicationProperties.getProperty(CONFIG_FILE_PROPERTY_KEY))
                : new File(baseDir, CONFIG_FILE_NAME);
    }

    private Properties loadConfProperties() {
        try (InputStream input = FileUtils.openInputStream(configFile)){
            return loadProperties(input, false);
        } catch (IOException ex) {
            // use default values
            return new Properties();
        }
    }

    private Properties storeConfProperties() {
        try(FileOutputStream output = FileUtils.openOutputStream(this.configFile)) {
            this.applicationConfiguration.store(output,"Updated on " + new Date());
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
        return this.applicationConfiguration;
    }

    public static Properties loadProperties(InputStream input, boolean throwException) {
        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException ex) {
            if (throwException) {
                throw new IllegalArgumentException(ex);
            }
        }
        return properties;
    }

    public static Properties substitutWithSystemProperties(Properties properties) {
        StrSubstitutor substitutor = new StrSubstitutor((Map)System.getProperties());
        for(String key : checkNotNull(properties, "properties").stringPropertyNames()) {
            properties.setProperty(key, substitutor.replace(properties.getProperty(key)));
        }
        return properties;
    }
}
