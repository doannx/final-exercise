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

import vn.elca.training.ApplicationLauncher;
import vn.elca.training.dom.Department;
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

    @Test
    public void testCountAll() {
        Assert.assertEquals(15, projectRepository.count());
    }

    @Test
    public void testFindAll() {
        Assert.assertEquals(15, projectRepository.findAll().size());
    }

    @Test
    public void testUpdate() {
        // prepare test data
        Department dep = new Department("FSU15");
        Project dummyPrj = new Project(999, "KSTA999", new Date(), "NEW", "Helm AG", dep);
        // execute
        this.projectRepository.save(dummyPrj);
        // verify
        Project savedPrj = this.projectRepository.findOne(QProject.project.number.eq(999));
        Assert.assertEquals("KST999", savedPrj.getName());
    }
}
