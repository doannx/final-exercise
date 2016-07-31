package vn.elca.training.dom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {
    @Id
    private String visa;

    @Column(nullable = false)
    private String name;

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

}
