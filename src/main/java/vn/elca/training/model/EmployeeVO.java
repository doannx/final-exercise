package vn.elca.training.model;

public class EmployeeVO {
    public EmployeeVO(Long id, String visa, String firstName, String lastName) {
        super();
        this.id = id;
        this.displayName = visa + ":" + firstName + " " + lastName;
    }

    private Long id;
    private String displayName;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
