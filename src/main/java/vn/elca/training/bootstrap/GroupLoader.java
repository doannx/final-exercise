package vn.elca.training.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import vn.elca.training.dao.IGroupRepository;
import vn.elca.training.dom.Group;

@Component
public class GroupLoader implements ApplicationListener<ContextRefreshedEvent> {
    private IGroupRepository groupRepository;
    private Logger log = Logger.getLogger(GroupLoader.class);

    @Autowired
    public void setGroupRepository(IGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Group x = new Group("XDG");
        this.groupRepository.save(x);
        x = new Group("KIM");
        this.groupRepository.save(x);
        x = new Group("VVH");
        this.groupRepository.save(x);
        x = new Group("THU");
        this.groupRepository.save(x);
        x = new Group("TDP");
        this.groupRepository.save(x);
        log.info("Save 5 initial groups...");
    }
}
