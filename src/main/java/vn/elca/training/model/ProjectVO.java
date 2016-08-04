package vn.elca.training.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import vn.elca.training.dom.Member;

public class ProjectVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private Date finishingDate;
    @NotEmpty
    private String status;
    @NotEmpty
    private String customer;
    @NotEmpty
    private String group;
    private List<Member> members;
    private Date endDate;

    public ProjectVO() {
    }

    public ProjectVO(String name, Date finishingDate) {
        this.name = name;
        this.finishingDate = finishingDate;
    }

    public ProjectVO(Long id, String name, Date finishingDate) {
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
    }

    public ProjectVO(Long id, String name, Date finishingDate, String status, String customer, List<Member> members,
            String group) {
        super();
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.members = members;
        this.group = group;
    }

    public ProjectVO(Long id, String name, Date finishingDate, String status, String customer, List<Member> members,
            String group, Date endDate) {
        super();
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
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

    public Date getFinishingDate() {
        return finishingDate;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomer() {
        return customer;
    }

    public List<Member> getMembers() {
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

    public void setFinishingDate(Date finishingDate) {
        this.finishingDate = finishingDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
