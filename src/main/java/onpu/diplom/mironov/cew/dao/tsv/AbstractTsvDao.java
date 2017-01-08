package onpu.diplom.mironov.cew.dao.tsv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import onpu.diplom.mironov.cew.CewUtil;
import onpu.diplom.mironov.cew.bean.AbstractBean;
import onpu.diplom.mironov.cew.dao.AbstractDao;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public abstract class AbstractTsvDao<T extends AbstractBean> implements AbstractDao<T> {

    public static final CsvPreference DEFAULT_CSV_PREFERENCE = CsvPreference.STANDARD_PREFERENCE;

    protected final File file;
    protected final CsvPreference csvPreference;
    protected final CellProcessor[] processors;
    protected final String[] header;
    protected Map<Long, T> cache = null;
    protected final Object cacheAnchor = new Object();

    public AbstractTsvDao(File file) {
        this(file, DEFAULT_CSV_PREFERENCE);
    }

    public AbstractTsvDao(File file, CsvPreference csvPreference) {
        this.file = file;
        this.csvPreference = csvPreference;
        this.processors = getProcessors();
        this.header = getHeader();
        file.getParentFile().mkdirs();
    }

    @Override
    public List<T> list() {
        if (cache == null) {
            synchronized (cacheAnchor) {
                if (cache == null) {
                    ICsvBeanReader beanReader = null;
                    try {
                        beanReader = new CsvBeanReader(new FileReader(file), csvPreference);
                        beanReader.getHeader(true);

                        List<T> list = new ArrayList<T>();

                        T t;
                        while ((t = beanReader.read(getBeanClass(), header, processors)) != null) {
                            list.add(t);
                        }
                        cache = CewUtil.toMap(list);
                    } catch (FileNotFoundException ex) {
                        cache = CewUtil.toMap(new ArrayList<T>());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        if (beanReader != null) {
                            try {
                                beanReader.close();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<T>(cache.values());
    }

    @Override
    public T get(long id) {
        list();// to init cache
        return cache.get(id);
    }

    @Override
    public void add(T t) {
        List<T> list = list();
        for(T o : list) {
            if (o.equals(t)) {
                throw new IllegalArgumentException("Element already exist");
            }
        }
        list.add(t);
        set(list);
    }

    @Override
    public void delete(T t) {
        List<T> list = list();
        for (Iterator<T> it = list.iterator(); it.hasNext();) {
            if(it.next().equals(t)) {
                it.remove();
            }
        }
        set(list);
    }

    protected void set(List<T> list) {
        ICsvBeanWriter beanWriter = null;
        try {
            beanWriter = new CsvBeanWriter(new FileWriter(file), csvPreference);

            beanWriter.writeHeader(header);

            for (final T customer : list) {
                beanWriter.write(customer, header, processors);
            }

            synchronized (cacheAnchor) {
                cache = null;
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    protected abstract CellProcessor[] getProcessors();

    protected abstract String[] getHeader();

}
