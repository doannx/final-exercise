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
import vn.elca.training.dom.Project;

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
        projectRepository.save(new Project("KSTA", new Date()));
        projectRepository.save(new Project("LAGAPEO", new Date()));
        projectRepository.save(new Project("ZHQUEST", new Date()));
        projectRepository.save(new Project("SECUTIX", new Date()));
        Assert.assertEquals(8, projectRepository.count());
    }

    @Test
    public void testFindOneWithQueryDSL() {
        final String PROJECT_NAME = "KSTA";
        projectRepository.save(new Project(PROJECT_NAME, new Date()));
        //Project project = new JPAQuery(em).from(QProject.project).where(QProject.project.name.eq(PROJECT_NAME))
        //        .singleResult(QProject.project);
        //Assert.assertEquals(PROJECT_NAME, project.getName());
    }
}
