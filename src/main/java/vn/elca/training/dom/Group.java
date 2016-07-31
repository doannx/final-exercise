package vn.elca.training.dom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Depart")
public class Group {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    public Group() {
    }

    public Group(String name) {
        super();
        this.name = name;
    }

    public Group(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
