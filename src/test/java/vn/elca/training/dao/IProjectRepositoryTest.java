package vn.elca.training.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import vn.elca.training.ApplicationLauncher;
import vn.elca.training.dom.Department;
import vn.elca.training.dom.Member;
import vn.elca.training.dom.Project;
import vn.elca.training.dom.QProject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationLauncher.class)
@TransactionConfiguration
public class IProjectRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IGroupRepository groupRepository;
    @Autowired
    private IMemberRepository memberRepository;

    @Test
    @Transactional
    public void testAdd001() {
        Member member1 = new Member("QMV", "FULL NAME OF QMV");
        Member member2 = new Member("HTV", "FULL NAME OF HTV");
        Member member3 = new Member("TQP", "FULL NAME OF TQP");
        Member member4 = new Member("HNH", "FULL NAME OF HNH");
        Member member5 = new Member("NQN", "FULL NAME OF NQN");
        Member member6 = new Member("QKP", "FULL NAME OF QKP");
        Member member7 = new Member("PLH", "FULL NAME OF PLH");
        Member member8 = new Member("HNL", "FULL NAME OF HNL");
        Member member9 = new Member("MKN", "FULL NAME OF MKN");
        Member member10 = new Member("TBH", "FULL NAME OF TBH");
        Member member11 = new Member("TDN", "FULL NAME OF TDN");

        Department dep = new Department("QMV");

        Project prj1 = new Project(111, "EFV", new Date(), "NEW", "Customer 1", dep);
        prj1.getMembers().add(member2);
        prj1.getMembers().add(member3);
        prj1.getMembers().add(member4);
        prj1.getMembers().add(member5);

        member2.getProjects().add(prj1);
        member3.getProjects().add(prj1);
        member4.getProjects().add(prj1);
        member5.getProjects().add(prj1);
        
        Project prj2 = new Project(222, "CXTRANET", new Date(), "NEW", "Customer 2", dep);
        prj2.getMembers().add(member6);
        prj2.getMembers().add(member7);
        prj2.getMembers().add(member8);

        member6.getProjects().add(prj2);
        member7.getProjects().add(prj2);
        member8.getProjects().add(prj2);
        
        Project prj3 = new Project(333, "CRYSTAL BALL", new Date(), "NEW", "Customer 3", dep);
        prj3.getMembers().add(member9);
        prj3.getMembers().add(member10);
        prj3.getMembers().add(member11);

        member9.getProjects().add(prj3);
        member10.getProjects().add(prj3);
        member11.getProjects().add(prj3);
        
        dep.getProjects().add(prj1);
        dep.getProjects().add(prj2);
        dep.getProjects().add(prj3);
        
        dep.setLeader(member1);
        
        this.groupRepository.save(dep);
        
        // verify
        Project verifyPrj = this.projectRepository.findOne(QProject.project.number.eq(111));
        Assert.assertEquals(4, verifyPrj.getMembers().size());
        Assert.assertEquals("EFV", verifyPrj.getName());
    }
    
    @Test
    @Transactional
    public void testAdd002() {
        Member member1 = new Member("QMV", "FULL NAME OF QMV");
        Member member2 = new Member("HTV", "FULL NAME OF HTV");
        Member member3 = new Member("TQP", "FULL NAME OF TQP");
        Member member4 = new Member("HNH", "FULL NAME OF HNH");
        Member member5 = new Member("NQN", "FULL NAME OF NQN");
        Member member6 = new Member("QKP", "FULL NAME OF QKP");
        Member member7 = new Member("PLH", "FULL NAME OF PLH");
        Member member8 = new Member("HNL", "FULL NAME OF HNL");
        Member member9 = new Member("MKN", "FULL NAME OF MKN");
        Member member10 = new Member("TBH", "FULL NAME OF TBH");
        Member member11 = new Member("TDN", "FULL NAME OF TDN");

        Department dep = new Department("QMV");

        Project prj1 = new Project(111, "EFV", new Date(), "NEW", "Customer 1", dep);
        prj1.getMembers().add(member2);
        prj1.getMembers().add(member3);
        prj1.getMembers().add(member4);
        prj1.getMembers().add(member5);

        member2.getProjects().add(prj1);
        member3.getProjects().add(prj1);
        member4.getProjects().add(prj1);
        member5.getProjects().add(prj1);
        
        Project prj2 = new Project(222, "CXTRANET", new Date(), "NEW", "Customer 2", dep);
        prj2.getMembers().add(member6);
        prj2.getMembers().add(member7);
        prj2.getMembers().add(member8);

        member6.getProjects().add(prj2);
        member7.getProjects().add(prj2);
        member8.getProjects().add(prj2);
        
        Project prj3 = new Project(333, "CRYSTAL BALL", new Date(), "NEW", "Customer 3", dep);
        prj3.getMembers().add(member9);
        prj3.getMembers().add(member10);
        prj3.getMembers().add(member11);

        member9.getProjects().add(prj3);
        member10.getProjects().add(prj3);
        member11.getProjects().add(prj3);
        
        dep.getProjects().add(prj1);
        dep.getProjects().add(prj2);
        dep.getProjects().add(prj3);
        
        dep.setLeader(member1);
        
        this.groupRepository.save(dep);
        
        // verify
        Project verifyPrj = this.projectRepository.findOne(QProject.project.number.eq(111));
        Assert.assertEquals(4, verifyPrj.getMembers().size());
        Assert.assertEquals("EFV", verifyPrj.getName());
    }
    
    @Test
    public void testUpdate001() {
        // prepare test data
        Department dep = new Department("FSU15");
        Member member = new Member("XDG999", "NGUYEN XUAN DOAN 1");
        dep.setLeader(member);
        Project dummyPrj = new Project(999, "KSTA999", new Date(), "NEW", "Helm AG", dep);
        dep.getProjects().add(dummyPrj);
        // execute
        this.groupRepository.save(dep);
        // verify
        Project savedPrj = this.projectRepository.findOne(QProject.project.number.eq(999));
        Assert.assertEquals("KSTA999", savedPrj.getName());
    }

    @Test
    @Transactional
    public void testUpdate002() {
        // prepare test data
        Department dep = new Department("FSU15");
        Member member = this.memberRepository.findOne("XDG12");
        dep.setLeader(member);
        Project dummyPrj = new Project(998, "KSTA999", new Date(), "NEW", "Helm AG", dep);
        dep.getProjects().add(dummyPrj);
        dummyPrj.setGroup(dep);
        // execute
        this.groupRepository.save(dep);
        // verify
        Project savedPrj = this.projectRepository.findOne(QProject.project.number.eq(999));
        Assert.assertEquals("KSTA999", savedPrj.getName());
    }

    @Test
    public void testDelete() {
        Project prj = this.projectRepository.findOne(QProject.project.number.eq(998));
        if (prj != null) {
            this.projectRepository.delete(prj);
        }
    }
}
