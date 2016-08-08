package vn.elca.training.dom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "ID")),
        @AttributeOverride(name = "version", column = @Column(name = "VERSION")) })
public class Project extends Root {
    @Column(name = "PROJECT_NUMBER", unique = true)
    private Integer number;
    @Column(name = "NAME")
    private String name;
    @Column(name = "START_DATE")
    private Date finishingDate;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CUSTOMER")
    private String customer;
    @Transient
    private Long groupId;
    @ManyToOne
    private Department group;
    @Column(name = "END_DATE")
    private Date endDate;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "project_member", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private List<Employee> members = new ArrayList<Employee>();

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Department getGroup() {
        return group;
    }

    public void setGroup(Department group) {
        this.group = group;
    }

    public Project() {
    }

    public Project(String name, Date finishingDate) {
        this.name = name;
        this.finishingDate = finishingDate;
    }

    public Project(Integer number, String name, Date finishingDate, String status, String customer, Department group) {
        super();
        this.number = number;
        this.name = name;
        this.finishingDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
    }

    public Project(Integer number, String name, Date finishingDate, String status, String customer, Department group,
            List<Employee> members) {
        super();
        this.number = number;
        this.name = name;
        this.finishingDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
        this.members = members;
    }

    public Project(Integer number, String name, Date finishingDate, String status, String customer, Department group,
            Date endDate) {
        super();
        this.number = number;
        this.name = name;
        this.finishingDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
        this.endDate = endDate;
    }

    public Project(Integer number, String name, Date finishingDate, String status, String customer, Department group,
            Date endDate, List<Employee> members) {
        super();
        this.number = number;
        this.name = name;
        this.finishingDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
        this.endDate = endDate;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public Date getFinishingDate() {
        return finishingDate;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomer() {
        return customer;
    }

    public List<Employee> getMembers() {
        return members;
    }

    public void setMembers(List<Employee> members) {
        this.members = members;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFinishingDate(Date finishingDate) {
        this.finishingDate = finishingDate;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer prjNumber) {
        this.number = prjNumber;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}