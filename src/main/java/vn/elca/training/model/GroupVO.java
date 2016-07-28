package vn.elca.training.model;

public class GroupVO {
    public GroupVO(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "Group [id=" + id + ", name=" + name + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
