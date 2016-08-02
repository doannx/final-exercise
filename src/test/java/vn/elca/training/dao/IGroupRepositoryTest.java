package vn.elca.training.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vn.elca.training.ApplicationLauncher;
import vn.elca.training.config.MyRepositoryConfiguration;
import vn.elca.training.dom.Department;
import vn.elca.training.dom.Project;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ApplicationLauncher.class, MyRepositoryConfiguration.class })
public class IGroupRepositoryTest {
    @Autowired
    private IGroupRepository groupRepo;
    @Autowired
    private IProjectRepository projectRepo;

    @Test
    public void testSaveGroup() {
        Department g = new Department();
        g.setName("XDG");
        assertNull(g.getId());
        this.groupRepo.save(g);
        assertNotNull(g.getId());
        System.out.println(g.getId());
        Project dummyPrj = new Project(123L, "KSTA", new Date(), "NEW", "Helm AG", g);
        this.projectRepo.save(dummyPrj);
        // verify
        List<Department> lst = this.groupRepo.findAll();
        Project savedPrj = this.projectRepo.getOne(123L);
        Assert.assertEquals("XDG", savedPrj.getGroup().getName());
        //
        // System.out.println(this.groupRepo.getOne(7L).getProjects().size());
    }
}
