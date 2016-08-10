package vn.elca.training.model;

public class MemberVO {
    public MemberVO(String visa, String name) {
        super();
        this.visa = visa;
        this.name = name;
    }

    private String visa;
    private String name;

    @Override
    public String toString() {
        return "Member [visa=" + visa + ", name=" + name + "]";
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
