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
import vn.elca.training.dom.Department;
import vn.elca.training.dom.Member;
import vn.elca.training.dom.Project;
import vn.elca.training.dom.QDepartment;

import com.google.common.collect.Lists;
import com.mysema.query.types.expr.BooleanExpression;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ApplicationLauncher.class, MyRepositoryConfiguration.class })
public class IGroupRepositoryTest {
    @Autowired
    private IGroupRepository groupRepo;
    @Autowired
    private IProjectRepository projectRepo;
    @Autowired
    private IMemberRepository memberRepo;

    @Test
    @Transactional
    public void testSaveGroup() {
        // new member
        Member member = new Member("TC1", "TEST ACC 1");
        this.memberRepo.save(member);
        // new group
        Department g = new Department();
        g.setName("TEST");
        g.setLeader(member);
        this.groupRepo.save(g);
        // new project
        List<Member> members = new ArrayList<Member>();
        members.add(member);
        Project dummyPrj = new Project(123, "TEST PRJ", new Date(), "NEW", "TEST CUS", g, members);
        this.projectRepo.save(dummyPrj);
        // verify
        BooleanExpression groupName = QDepartment.department.name.eq("TEST");
        Assert.assertEquals("TEST ACC 1", Lists.newArrayList(this.groupRepo.findAll(groupName).iterator()).get(0)
                .getLeader().getName());
    }
}
