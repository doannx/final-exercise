package vn.elca.training.dom;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends Root {
    private String visa;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Set<Project> projects = new HashSet<Project>();

    public Employee() {
    }

    public Employee(String visa, String firstName, String lastName) {
        super();
        this.visa = visa;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Column(name = "VISA", unique = true)
    public String getVisa() {
        return visa;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    @Column(name = "BIRTH_DATE")
    public Date getBirthDate() {
        return birthDate;
    }

    @ManyToMany(mappedBy = "members")
    @JsonBackReference
    public Set<Project> getProjects() {
        return projects;
    }

    @Transient
    public String getDisplayName() {
        return this.visa + ":" + this.firstName + " " + this.lastName;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
