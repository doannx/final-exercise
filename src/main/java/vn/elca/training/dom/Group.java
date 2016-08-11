package vn.elca.training.dom;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "\"GROUP\"")
public class Group extends Root {
    private String name;
    private Set<Project> projects = new HashSet<Project>();
    private Employee leader;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "GROUP_LEADER_ID", nullable = true)
    public Employee getLeader() {
        return leader;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    public Set<Project> getProjects() {
        return projects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }
}
