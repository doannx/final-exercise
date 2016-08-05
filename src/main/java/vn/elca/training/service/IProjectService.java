package vn.elca.training.service;

import vn.elca.training.dom.Project;
import vn.elca.training.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.ProjectVO;
import vn.elca.training.model.SearchCriteriaVO;
import vn.elca.training.model.SearchResultVO;

public interface IProjectService {
    SearchResultVO<Project> findAll(int nextPage, int num, String sortColName, String sortDirection);

    /**
     * Find project(s) based on name. Search condition is LIKE operator.
     * 
     * @param name
     * @return List<Project>
     */
    SearchResultVO<Project> findByCriteria(SearchCriteriaVO criteria, int currentPage, int num, String sortColName,
            String sortDirection);

    /**
     * Get the information of one project by its id.
     * 
     * @param id
     * @return Project
     */
    Project getById(String id);

    /**
     * Update one project.
     * 
     * @param p
     *            project with new information
     * @param mode
     *            update or add
     * @return id of updated project
     * @exception ProjectNumberAlreadyExistsException
     */
    Long update(ProjectVO p, String mode) throws ProjectNumberAlreadyExistsException;

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
    void delete(Long id);

    /**
     * Clone one project from existing one.
     * 
     * @param id
     *            project's id
     * @return id of clone one
     */
    Long clone(Long id);
    
    Project getByPrjNumber(Integer num);
    
}
