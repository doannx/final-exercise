package vn.elca.training.model;

import java.io.Serializable;

public class StatusVO implements Serializable {
    private static final long serialVersionUID = 5340895214623283980L;

    public StatusVO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;

    @Override
    public String toString() {
        return "Status [id=" + id + ", name=" + name + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
