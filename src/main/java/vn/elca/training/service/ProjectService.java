package vn.elca.training.service;

import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.elca.training.dao.IProjectRepository;
import vn.elca.training.dom.Project;
import vn.elca.training.model.ProjectVO;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    private IProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findByName(String name) {
        throw new NotYetImplementedException("ProjectService.findByName() is not implemented yet...");
    }

    @Override
    public ProjectVO getById(String id) {
        throw new NotYetImplementedException("ProjectService.getById() is not implemented yet...");
    }

    @Override
    public Long update(Project p) {
        throw new NotYetImplementedException("ProjectService.update() is not implemented yet...");
    }

    @Override
    public long countAll() {
        throw new NotYetImplementedException("ProjectService.countAll() is not implemented yet...");
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
