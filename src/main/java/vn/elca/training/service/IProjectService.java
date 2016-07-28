package vn.elca.training.service;

import java.util.List;

import vn.elca.training.dom.Project;
import vn.elca.training.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.ProjectVO;

public interface IProjectService {
    List<Project> findAll();

    List<Project> findAll(int currentPage, int num);

    /**
     * Find project(s) based on name. Search condition is LIKE operator.
     * 
     * @param name
     * @return List<Project>
     */
    List<Project> findByName(String name);

    List<Project> findByName(String name, int currentPage, int num);

    /**
     * Find project(s) based on name & status.
     * 
     * @param name
     * @param status
     * @return List<Project>
     */
    List<Project> findByNameAndStatus(String name, String status);

    List<Project> findByNameAndStatus(String name, String status, int currentPage, int num);

    /**
     * Find project(s) based on status.
     * 
     * @param name
     * @return List<Project>
     */
    List<Project> findByStatus(String status);

    List<Project> findByStatus(String status, int currentPage, int num);

    /**
     * Get the information of one project by its id.
     * 
     * @param id
     * @return ProjectVO
     */
    ProjectVO getById(String id);

    /**
     * Update one project.
     * 
     * @param p
     *            project with new information
     * @return id of updated project
     */
    Long update(Project p);

    Long update(ProjectVO p);

    /**
     * Count all existing projects.
     * 
     * @return the number of projects
     */
    long countAll();

    /**
     * Delete one project.
     * 
     * @param id
     *            project's id
     * @return void
     */
    void delete(String id);

    /**
     * Add one project.
     * 
     * @param p
     *            new project with new information
     * @return id of updated project
     * @exception ProjectNumberAlreadyExistsException
     */
    Long add(ProjectVO p) throws ProjectNumberAlreadyExistsException;

    /**
     * Sort by [name].
     * 
     * @param unsorted
     *            list
     * @param ordering
     * @return sorted list
     */
    List<Project> sortByName(List<Project> list, String ordering);

    /**
     * Sort by [id].
     * 
     * @param unsorted
     *            list
     * @param ordering
     * @return sorted list
     */
    List<Project> sortById(List<Project> list, String ordering);

    /**
     * Get sub list from input list based on index (paging).
     * 
     * @param lst
     * @param nextPage
     * @param num
     * @return sub list
     */
    List<Project> subListByIndex(List<Project> lst, int nextPage, int num);
}
