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
    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<Project> projects = new HashSet<Project>();
    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "GROUP_LEADER_ID", nullable = true)
    private Employee leader;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }
}
