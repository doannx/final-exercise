package vn.elca.training.dom;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Project extends Root {

    private Integer number;
    private String name;
    private Date startDate;
    private Date endDate;
    private String status;
    private String customer;
    @ManyToOne
    private Group group;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_member", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id") , inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id") )
    private Set<Employee> members = new HashSet<Employee>();

    @Column(name = "PROJECT_NUMBER", unique = true)
    public Integer getNumber() {
        return number;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    @Column(name = "START_DATE")
    public Date getStartDate() {
        return startDate;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    @Column(name = "CUSTOMER")
    public String getCustomer() {
        return customer;
    }

    public Group getGroup() {
        return group;
    }

    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Project() {
    }

    public Project(Integer number, String name, Date startDate, String status, String customer, Group group) {
        this.number = number;
        this.name = name;
        this.startDate = startDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
    }

    public Project(Integer number, String name, Date startDate, String status, String customer, Group group,
            Date endDate) {
        this(number, name, startDate, status, customer, group);
        this.endDate = endDate;
    }

    public Project(Integer number, String name, Date startDate, String status, String customer, Group group,
            List<Employee> members) {
        this(number, name, startDate, status, customer, group);
        this.members = new HashSet<Employee>(members);
    }

    public Project(Integer number, String name, Date startDate, String status, String customer, Group group,
            Date endDate, List<Employee> members) {
        this(number, name, startDate, status, customer, group, endDate);
        this.members = new HashSet<Employee>(members);
    }

    public List<Employee> getMembers() {
        return new ArrayList<Employee>(this.members);
    }

    public void setMembers(List<Employee> members) {
        this.members = new HashSet<Employee>(members);
    }
}