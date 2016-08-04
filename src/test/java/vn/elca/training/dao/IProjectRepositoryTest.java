package vn.elca.training.dao;

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
}
