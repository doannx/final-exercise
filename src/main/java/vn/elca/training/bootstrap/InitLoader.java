package vn.elca.training.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.elca.training.dao.IEmployeeRepository;
import vn.elca.training.dao.IGroupRepository;
import vn.elca.training.dom.Employee;
import vn.elca.training.dom.Group;

@Component
public class InitLoader implements InitializingBean {

    private IEmployeeRepository memberRepository;
    private IGroupRepository groupRepository;
    private static final Logger LOGGER = Logger.getLogger(InitLoader.class);

    @Autowired
    public void setMemberRepository(IEmployeeRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setGroupRepository(IGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
        Employee x = new Employee("XDG", "XUAN DOAN", "NGUYEN");
        this.memberRepository.save(x);
        x = new Employee("XDG2", "XUAN DOAN 2", "NGUYEN");
        this.memberRepository.save(x);
        x = new Employee("XDG3", "XUAN DOAN 3", "NGUYEN");
        this.memberRepository.save(x);
        x = new Employee("XDG4", "XUAN DOAN 4", "NGUYEN");
        this.memberRepository.save(x);
        x = new Employee("XDG5", "XUAN DOAN 5", "NGUYEN");
        this.memberRepository.save(x);
        x = new Employee("XDG6", "XUAN DOAN 6", "NGUYEN");
        this.memberRepository.save(x);
        x = new Employee("XDG7", "XUAN DOAN 7", "NGUYEN");
        this.memberRepository.save(x);
        x = new Employee("XDG8", "XUAN DOAN 8", "NGUYEN");
        this.memberRepository.save(x);
        x = new Employee("XDG9", "XUAN DOAN 9", "NGUYEN");
        this.memberRepository.save(x);
        x = new Employee("XDG10", "XUAN DOAN 10", "NGUYEN");
        this.memberRepository.save(x);
        x = new Employee("XDG11", "XUAN DOAN 11", "NGUYEN");
        this.memberRepository.save(x);
        x = new Employee("XDG12", "XUAN DOAN 12", "NGUYEN");
        this.memberRepository.save(x);
        LOGGER.info("Save 12 initial Members...");
        
        Group g = new Group("XDG");
        this.groupRepository.save(g);
        g = new Group("KIM");
        this.groupRepository.save(g);
        g = new Group("VVH");
        this.groupRepository.save(g);
        g = new Group("THU");
        this.groupRepository.save(g);
        g = new Group("TDP");
        this.groupRepository.save(g);
        LOGGER.info("Save 5 initial groups...");
        LOGGER.info("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
    }
}
