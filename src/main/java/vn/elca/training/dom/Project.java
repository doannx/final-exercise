package vn.elca.training.dom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class Project extends Root {
    @Column(name = "PROJECT_NUMBER", unique = true)
    private Integer number;
    @Column(name = "NAME")
    private String name;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CUSTOMER")
    private String customer;
    @Transient
    private Long groupId;
    @ManyToOne
    private Group group;
    @Column(name = "END_DATE")
    private Date endDate;
    @ManyToMany(cascade = CascadeType.ALL)
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Project() {
    }

    public Project(String name, Date finishingDate) {
        this.name = name;
        this.startDate = finishingDate;
    }

    public Project(Integer number, String name, Date finishingDate, String status, String customer, Group group) {
        super();
        this.number = number;
        this.name = name;
        this.startDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
    }

    public Project(Integer number, String name, Date finishingDate, String status, String customer, Group group,
            List<Employee> members) {
        super();
        this.number = number;
        this.name = name;
        this.startDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
        this.members = members;
    }

    public Project(Integer number, String name, Date finishingDate, String status, String customer, Group group,
            Date endDate) {
        super();
        this.number = number;
        this.name = name;
        this.startDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
        this.endDate = endDate;
    }

    public Project(Integer number, String name, Date finishingDate, String status, String customer, Group group,
            Date endDate, List<Employee> members) {
        super();
        this.number = number;
        this.name = name;
        this.startDate = finishingDate;
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
        return startDate;
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
        this.startDate = finishingDate;
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