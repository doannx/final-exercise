package vn.elca.training.service;

import java.util.Date;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vn.elca.training.dao.IProjectRepository;
import vn.elca.training.dom.Project;
import vn.elca.training.dom.QProject;
import vn.elca.training.model.ProjectVO;

import com.mysema.query.types.Predicate;

/**
 * This is the implementation of IProjectService with few dummy data records.
 * 
 * @author xdg, 20160714
 *
 */
@Service
@Qualifier(value = "demoTransProjectService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DemoTransProjectService implements IProjectService {
    private IProjectRepository projectRepository;

    @Autowired
    public DemoTransProjectService(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        // save some dummy test data
        this.projectRepository.save(new Project("KSTA", new Date()));
        this.projectRepository.save(new Project("LAGAPEO", new Date()));
        this.projectRepository.save(new Project("ZHQUEST", new Date()));
        this.projectRepository.save(new Project("SECUTIX", new Date()));
    }

    @Override
    public List<Project> findAll() {
        // return dummy project list
        return projectRepository.findAll();
    }

    /**
     * Find one project based on its name. Search condition is LIKE operator.
     * 
     * @param name
     * @return null (must re-implement in class implementation)
     */
    @Override
    public List<Project> findByName(String name) {
        Predicate nameAre = QProject.project.name.contains(name);
        return (List<Project>) projectRepository.findAll(nameAre);
    }

    /**
     * Get the information of one project by its id.
     * 
     * @param id
     * @return null (must re-implement in class implementation)
     */
    @Override
    public ProjectVO getById(String id) {
        // return projectRepository.findOne(Long.parseLong(id));
        return new ProjectVO();
    }

    /**
     * Update one project.
     * 
     * @param p
     *            project with new information
     * @return id of updated project
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Long update(Project p) {
        Long updatedId = this.projectRepository.saveAndFlush(p).getId();
        if (p.getId() == 4L) {
            throw new RuntimeException("Roll me back...");
        }
        return updatedId;
    }

    @Override
    public long countAll() {
        return this.projectRepository.count();
    }

    @Override
    public void delete(String id) {
        throw new NotYetImplementedException("ProjectService.delete() is not implemented yet...");
    }

    @Override
    public List<Project> findByNameAndStatus(String name, String status) {
        throw new NotYetImplementedException("ProjectService.findByNameAndStatus() is not implemented yet...");
    }

    @Override
    public List<Project> findByStatus(String status) {
        throw new NotYetImplementedException("ProjectService.findByStatus() is not implemented yet...");
    }

    @Override
    public List<Project> findAll(int currentPage, int num) {
        throw new NotYetImplementedException("ProjectService.findAll() is not implemented yet...");
    }

    @Override
    public List<Project> findByName(String name, int currentPage, int num) {
        throw new NotYetImplementedException("ProjectService.findByName() is not implemented yet...");
    }

    @Override
    public List<Project> findByNameAndStatus(String name, String status, int currentPage, int num) {
        throw new NotYetImplementedException("ProjectService.findByNameAndStatus() is not implemented yet...");
    }

    @Override
    public List<Project> findByStatus(String status, int currentPage, int num) {
        throw new NotYetImplementedException("ProjectService.findByStatus() is not implemented yet...");
    }

    @Override
    public Long update(ProjectVO p) {
        throw new NotYetImplementedException("ProjectService.update() is not implemented yet...");
    }

    @Override
    public Long add(ProjectVO p) {
        throw new NotYetImplementedException("ProjectService.add() is not implemented yet...");
    }

    @Override
    public List<Project> sortByName(List<Project> list, String ordering) {
        throw new NotYetImplementedException("ProjectService.sortByName() is not implemented yet...");
    }

    @Override
    public List<Project> sortById(List<Project> list, String ordering) {
        throw new NotYetImplementedException("ProjectService.sortById() is not implemented yet...");
    }

    @Override
    public List<Project> subListByIndex(List<Project> lst, int nextPage, int num) {
        throw new NotYetImplementedException("ProjectService.subListByIndex() is not implemented yet...");
    }
}
