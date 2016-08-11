package vn.elca.training.service;

import vn.elca.training.dom.Project;
import vn.elca.training.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.ProjectVO;
import vn.elca.training.model.SearchCriteriaVO;
import vn.elca.training.model.SearchResultVO;

public interface IProjectService {
    /**
     * Find all existing project(s). Support for paging and sorting.
     * 
     * @param nextPage
     * @param num
     * @param sortColName
     * @param sortDirection
     * @return SearchResultVO<Project>
     */
    SearchResultVO<Project> findAll(int nextPage, int num, String sortColName, String sortDirection);

    /**
     * Find project(s) based on [multiple search criteria]. Support for paging and sorting.
     * 
     * @param criteria
     * @param currentPage
     * @param num
     * @param sortColName
     * @param sortDirection
     * @return SearchResultVO<Project>
     */
    SearchResultVO<Project> findByCriteria(SearchCriteriaVO criteria, int currentPage, int num, String sortColName,
            String sortDirection);

    /**
     * Get the information of one project by its id.
     * 
     * @param id
     * @return Project
     */
    Project getById(Long id);

    /**
     * Update/add one project.
     * 
     * @param p
     *            project with new information
     * @return id of updated project
     * @exception ProjectNumberAlreadyExistsException
     */
    Long update(ProjectVO p) throws ProjectNumberAlreadyExistsException;

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

    /**
     * Get the information of one project by its [number].
     * 
     * @param number
     * @return Project
     */
    Project getByPrjNumber(Integer num);
}
