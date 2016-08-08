package vn.elca.training.dom;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "\"GROUP\"")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "ID")),
        @AttributeOverride(name = "version", column = @Column(name = "VERSION")) })
public class Department extends Root {
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<Project>();
    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "GROUP_LEADER_ID", nullable = true)
    private Employee leader;

    public Department() {
    }

    public Department(String name) {
        super();
        this.name = name;
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

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }
}
