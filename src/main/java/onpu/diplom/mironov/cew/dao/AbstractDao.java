package onpu.diplom.mironov.cew.dao;

import java.util.List;
import onpu.diplom.mironov.cew.bean.AbstractBean;

public interface AbstractDao<T extends AbstractBean> {

    void add(T t);

    void delete(T t);

    T get(long id);

    List<T> list();

    Class<T> getBeanClass();
    
}
