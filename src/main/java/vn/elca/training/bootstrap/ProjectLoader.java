package vn.elca.training.bootstrap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import vn.elca.training.dao.IGroupRepository;
import vn.elca.training.dao.IMemberRepository;
import vn.elca.training.dao.IProjectRepository;
import vn.elca.training.dom.Department;
import vn.elca.training.dom.Member;
import vn.elca.training.dom.Project;

@Component
public class ProjectLoader implements ApplicationListener<ContextRefreshedEvent> {
    private IProjectRepository projectRepository;
    private IGroupRepository groupRepository;
    private IMemberRepository memberRepository;

    @Autowired
    public void setGroupRepository(IGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Autowired
    public void setProjectRepository(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Autowired
    public void setMemberRepository(IMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    private Logger log = Logger.getLogger(ProjectLoader.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Department group = new Department("KTG");
        this.groupRepository.save(group);
        Member member = new Member("KIM", "NGUYEN KIM THY");
        this.memberRepository.save(member);
        List<Member> members = new ArrayList<Member>();
        members.add(member);
        Project dummyPrj = new Project(1L, "KSTA", new Date(), "NEW", "Helm AG", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(2L, "LAGAPEO", new Date(), "NEW", "Lanxess AG", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(3L, "ZHQUEST", new Date(), "NEW", "Agravis Raiffeisen AG", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(4L, "SECUTIX", new Date(), "NEW", "Les Retaitest Popularies", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(5L, "Telekommunikation", new Date(), "NEW", "Volkswagen AG", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(6L, "Pharma", new Date(), "INP", "E.ON SE", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(7L, "Pharmahandel", new Date(), "INP", "BMW AG", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(8L, "Energieversorger", new Date(), "INP", "Schwarz Beteiligungs GmbH", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(9L, "Maschinenbau", new Date(), "INP", "BASF SE", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(10L, "Touristik", new Date(), "INP", "Siemens AG", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(11L, "Automobilzulieferer", new Date(), "PLN", "Deutsche Telekom AG", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(12L, "Medien", new Date(), "PLN", "Deutsche Post DHL Group", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(13L, "Finanzdienstleistungen", new Date(), "PLN", "Rewe Group", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(14L, "Konsumgüter", new Date(), "FIN", "BP Europa SE", group);
        this.projectRepository.save(dummyPrj);
        dummyPrj = new Project(15L, "Baustoffe", new Date(), "FIN", "Edeka Zentrale AG & Co. KG", group);
        this.projectRepository.save(dummyPrj);
        log.info("Init 15 dummy projects...");
    }
}
