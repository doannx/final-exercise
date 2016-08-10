package vn.elca.training.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import vn.elca.training.dao.IEmployeeRepository;
import vn.elca.training.dom.Employee;

@Component
public class MemberLoader implements ApplicationListener<ContextRefreshedEvent> {
    private IEmployeeRepository memberRepository;
    private Logger log = Logger.getLogger(MemberLoader.class);

    @Autowired
    public void setMemberRepository(IEmployeeRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
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
        log.info("Save 12 initial Members...");
    }
}
