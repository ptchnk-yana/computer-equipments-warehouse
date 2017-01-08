package onpu.diplom.mironov.cew.bean;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractBean implements Serializable{
    protected long id;

    public AbstractBean() {
        this(System.currentTimeMillis());
    }

    public AbstractBean(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractBean other = (AbstractBean) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AbstractBean{" + "id=" + id + '}';
    }

    
}
