package vn.elca.training.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import vn.elca.training.dao.IMemberRepository;
import vn.elca.training.dom.Employee;

@Component
public class MemberLoader implements ApplicationListener<ContextRefreshedEvent> {
    private IMemberRepository memberRepository;
    private Logger log = Logger.getLogger(MemberLoader.class);

    @Autowired
    public void setMemberRepository(IMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Employee x = new Employee("XDG1", "NGUYEN XUAN DOAN 1");
        this.memberRepository.save(x);
        x = new Employee("XDG2", "NGUYEN XUAN DOAN 2");
        this.memberRepository.save(x);
        x = new Employee("XDG3", "NGUYEN XUAN DOAN 3");
        this.memberRepository.save(x);
        x = new Employee("XDG4", "NGUYEN XUAN DOAN 4");
        this.memberRepository.save(x);
        x = new Employee("XDG5", "NGUYEN XUAN DOAN 5");
        this.memberRepository.save(x);
        x = new Employee("XDG6", "NGUYEN XUAN DOAN 6");
        this.memberRepository.save(x);
        x = new Employee("XDG7", "NGUYEN XUAN DOAN 7");
        this.memberRepository.save(x);
        x = new Employee("XDG8", "NGUYEN XUAN DOAN 8");
        this.memberRepository.save(x);
        x = new Employee("XDG9", "NGUYEN XUAN DOAN 9");
        this.memberRepository.save(x);
        x = new Employee("XDG10", "NGUYEN XUAN DOAN 10");
        this.memberRepository.save(x);
        x = new Employee("XDG11", "NGUYEN XUAN DOAN 11");
        this.memberRepository.save(x);
        x = new Employee("XDG12", "NGUYEN XUAN DOAN 12");
        this.memberRepository.save(x);
        log.info("Save 12 initial Members...");
    }
}
