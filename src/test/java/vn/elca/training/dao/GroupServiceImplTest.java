package vn.elca.training.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vn.elca.training.ApplicationLauncher;
import vn.elca.training.config.MyRepositoryConfiguration;
import vn.elca.training.dom.Employee;
import vn.elca.training.dom.Group;
import vn.elca.training.dom.Project;
import vn.elca.training.dom.QGroup;
import vn.elca.training.model.Status;

import com.google.common.collect.Lists;
import com.mysema.query.types.expr.BooleanExpression;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ApplicationLauncher.class, MyRepositoryConfiguration.class })
public class GroupServiceImplTest {
    @Autowired
    private IGroupRepository groupRepo;
    @Autowired
    private IProjectRepository projectRepo;

    @Test
    @Transactional
    public void testCreateCascade001() {
        // new member
        Employee member = new Employee("TC1", "TEST ACC 1", "NGUYEN");
        // new group
        Group g = new Group();
        g.setName("TEST");
        g.setLeader(member);
        this.groupRepo.save(g);
        // new project
        Project dummyPrj = new Project(555, "TEST PRJ", new Date(), Status.NEW, "TEST CUS", g, new ArrayList<Employee>());
        this.projectRepo.save(dummyPrj);
        // verify
        BooleanExpression groupName = QGroup.group.name.eq("TEST");
        Assert.assertEquals("TEST ACC 1", Lists.newArrayList(this.groupRepo.findAll(groupName).iterator()).get(0)
                .getLeader().getFirstName());
    }
    
    @Test
    @Transactional
    public void testCreateCascade002() {
        // new member
        Employee member = new Employee("TC2", "TEST ACC 2", "NGUYEN");
        // new group
        Group g = new Group();
        g.setName("TEST");
        g.setLeader(member);
        this.groupRepo.save(g);
        // new project
        List<Employee> members = new ArrayList<Employee>();
        members.add(member);
        Project dummyPrj = new Project(666, "TEST PRJ 666", new Date(), Status.NEW, "TEST CUS 666", g, members);
        this.projectRepo.save(dummyPrj);
        // verify
        BooleanExpression groupName = QGroup.group.name.eq("TEST");
        Assert.assertEquals("TEST ACC 2", Lists.newArrayList(this.groupRepo.findAll(groupName).iterator()).get(0)
                .getLeader().getFirstName());
    }
}
