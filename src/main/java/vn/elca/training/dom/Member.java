package vn.elca.training.dom;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Member {
    @Id
    private String visa;
    @Column(nullable = false)
    private String name;
    @ManyToMany(mappedBy = "members")
    @JsonBackReference
    private List<Project> projects = new ArrayList<Project>();

    public Member() {
    }

    public Member(String visa, String name) {
        super();
        this.visa = visa;
        this.name = name;
    }

    public String getVisa() {
        return visa;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
