package vn.elca.training.dom;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Project {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private Date finishingDate;
    @Column
    private String status;
    @Column
    private String customer;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department group;
    @Column
    private Date endDate;
    
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

    
    public Project(Long id, String name, Date finishingDate, String status, String customer, Department group) {
        super();
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
    }

    public Project(Long id, String name, Date finishingDate, String status, String customer, Department group,
            Date endDate) {
        super();
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
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
}