package onpu.diplom.mironov.cew.dao;

import onpu.diplom.mironov.cew.bean.*;

import java.util.List;

public interface RequestDao extends AbstractDao<Request>{

    List<Request> findByUser(User user);

    void update(Request request);
}
