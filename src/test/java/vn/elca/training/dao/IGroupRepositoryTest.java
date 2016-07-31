package vn.elca.training.dao;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vn.elca.training.ApplicationLauncher;
import vn.elca.training.config.MyRepositoryConfiguration;
import vn.elca.training.dom.Department;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ApplicationLauncher.class, MyRepositoryConfiguration.class })
public class IGroupRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    
    private IGroupRepository groupRepository;

    @Autowired
    public void setGroupRepository(IGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Test
    public void testSaveGroup() {
        Department g = new Department();
        g.setName("XDG");
        
        assertNull(g.getId());
        this.groupRepository.save(g);
        assertNotNull(g.getId());
        
    }
}
