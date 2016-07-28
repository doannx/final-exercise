package vn.elca.training.dom;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private Date finishingDate;
    @Column
    private String status;
    @Column
    private String customer;

    public Project() {
    }

    public Project(String name, Date finishingDate) {
        this.name = name;
        this.finishingDate = finishingDate;
    }

    public Project(Long id, String name, Date finishingDate) {
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
    }

    public Project(Long id, String name, Date finishingDate, String status, String customer) {
        super();
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
        this.status = status;
        this.customer = customer;
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