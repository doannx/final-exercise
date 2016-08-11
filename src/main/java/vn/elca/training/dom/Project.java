package vn.elca.training.dom;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import vn.elca.training.model.Status;

@Entity
public class Project extends Root {
    private Integer number;
    private String name;
    private Date startDate;
    private Date endDate;
    private Status status;
    private String customer;
    private Group group;
    private Set<Employee> members = new HashSet<Employee>();
    private String sttMultilingual;

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
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    @Column(name = "CUSTOMER")
    public String getCustomer() {
        return customer;
    }

    @ManyToOne
    public Group getGroup() {
        return group;
    }

    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_member", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    public
            Set<Employee> getMembers() {
        return members;
    }

    @Transient
    public String getSttMultilingual() {
        return sttMultilingual;
    }

    public void setSttMultilingual(String sttMultilingual) {
        this.sttMultilingual = sttMultilingual;
    }

    public void setMembers(Set<Employee> members) {
        this.members = members;
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

    public void setStatus(Status status) {
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

    public Project(Integer number, String name, Date startDate, Status status, String customer, Group group) {
        this.number = number;
        this.name = name;
        this.startDate = startDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
    }

    public Project(Integer number, String name, Date startDate, Status status, String customer, Group group,
            Date endDate) {
        this(number, name, startDate, status, customer, group);
        this.endDate = endDate;
    }

    public Project(Integer number, String name, Date startDate, Status status, String customer, Group group,
            List<Employee> members) {
        this(number, name, startDate, status, customer, group);
        this.members = new HashSet<Employee>(members);
    }

    public Project(Integer number, String name, Date startDate, Status status, String customer, Group group,
            Date endDate, List<Employee> members) {
        this(number, name, startDate, status, customer, group, endDate);
        this.members = new HashSet<Employee>(members);
    }
}