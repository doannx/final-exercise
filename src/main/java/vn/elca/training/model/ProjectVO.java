package vn.elca.training.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import vn.elca.training.dom.Employee;

public class ProjectVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    private Long id;
    @NotNull
    private Integer number;
    @NotEmpty
    private String name;
    @NotNull
    private Date startDate;
    @NotEmpty
    private String status;
    @NotEmpty
    private String customer;
    @NotEmpty
    private String group;
    private List<Employee> members;
    private Date endDate;
    private Integer version;

    public ProjectVO() {
    }

    public ProjectVO(Long id, Integer number, String name, Date startDate, String status, String customer,
            List<Employee> members, String group, Date endDate) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.startDate = startDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
        this.members = members;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
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

    public String getGroup() {
        return group;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setMembers(List<Employee> members) {
        this.members = members;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
