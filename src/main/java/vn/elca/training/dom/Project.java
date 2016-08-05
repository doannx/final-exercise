package vn.elca.training.dom;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PROJECT_NUMBER", unique = true)
    private Integer prjNumber;
    @Column(name = "NAME")
    private String name;
    @Column(name = "START_DATE")
    private Date finishingDate;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CUSTOMER")
    private String customer;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department group;
    @Column(name = "END_DATE")
    private Date endDate;
    @Version
    @Column(name = "VERSION")
    private Integer version;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_member", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "visa"))
    private List<Member> members;

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
            List<Member> members) {
        super();
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
        this.members = members;
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

    public Project(Long id, String name, Date finishingDate, String status, String customer, Department group,
            Date endDate, List<Member> members) {
        super();
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
        this.status = status;
        this.customer = customer;
        this.group = group;
        this.endDate = endDate;
        this.members = members;
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

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getPrjNumber() {
        return prjNumber;
    }

    public void setPrjNumber(Integer prjNumber) {
        this.prjNumber = prjNumber;
    }
}