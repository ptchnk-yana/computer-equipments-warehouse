package onpu.diplom.mironov.cew;

import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import onpu.diplom.mironov.cew.bean.AbstractBean;

public class CewUtil {

    public static final int DEFAULT_SMALL_WIDTH = 16;
    public static final int DEFAULT_SMALL_HEIGHT = 16;

    public static ImageIcon createScaledImageIcon(String path) {
        return createScaledImageIcon(path, DEFAULT_SMALL_WIDTH, DEFAULT_SMALL_HEIGHT);
    }

    public static ImageIcon createScaledImageIcon(String path, int w, int h) {
        try {
            return new ImageIcon(new ImageIcon(path).getImage()
                    .getScaledInstance(w, h, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            return null;
        }
    }

    public static <K, V> Map<K, V> mapOf(Object... objects) {
        Map<K, V> map = new HashMap<K, V>();
        for (int i = 0; i + 1 < objects.length; i += 2) {
            map.put((K) objects[i], (V) objects[i + 1]);
        }
        return map;
    }

    public static <T extends AbstractBean> Map<Long, T> toMap(List<T> list) {
        Map<Long, T> map = new HashMap<Long, T>();
        if (list != null) {
            for (T t : list) {
                map.put(t.getId(), t);
            }
        }
        return map;
    }
}
