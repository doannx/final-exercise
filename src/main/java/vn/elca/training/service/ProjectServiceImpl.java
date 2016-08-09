package vn.elca.training.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.elca.training.dao.IGroupRepository;
import vn.elca.training.dao.IProjectRepository;
import vn.elca.training.dom.Department;
import vn.elca.training.dom.Project;
import vn.elca.training.dom.QProject;
import vn.elca.training.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.ProjectVO;
import vn.elca.training.model.SearchCriteriaVO;
import vn.elca.training.model.SearchResultVO;
import vn.elca.training.util.StringUtil;

import com.google.common.collect.Lists;
import com.mysema.query.types.expr.BooleanExpression;

@Service
@Qualifier(value = "hibernateProjectService")
public class ProjectServiceImpl implements IProjectService {
    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IGroupRepository groupRepository;
    private Logger log = Logger.getLogger(ProjectServiceImpl.class);

    /**
     * Find all existing project(s). Support for paging and sorting.
     * 
     * @param nextPage
     * @param num
     * @param sortColName
     * @param sortDirection
     * @return SearchResultVO<Project>
     */
    @Override
    public SearchResultVO<Project> findAll(int nextPage, int num, String sortColName, String sortDirection) {
        Pageable page = new PageRequest(nextPage, num, Sort.Direction.fromString(sortDirection), sortColName);
        SearchResultVO<Project> res = new SearchResultVO<Project>();
        res.setLstResult(Lists.newArrayList(this.projectRepository.findAll(page)));
        res.setSize(this.projectRepository.count());
        return res;
    }

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
    @Override
    public SearchResultVO<Project> findByCriteria(SearchCriteriaVO criteria, int nextPage, int num, String sortColName,
            String sortDirection) {
        SearchResultVO<Project> res = new SearchResultVO<Project>();
        BooleanExpression condExp = null;
        // the first search criterion
        if (!"all".equals(criteria.getCreteria().get("text"))) {
            String regex = StringUtil.buildRegexFromcriterion(criteria.getCreteria().get("text").toLowerCase());
            try {
                // filter by [project number]
                Integer id = Integer.parseInt(criteria.getCreteria().get("text"));
                condExp = QProject.project.number.eq(id);
            } catch (NumberFormatException ex) {
                // filter by [project name] and [customer name]
                condExp = QProject.project.name.lower().matches(regex)
                        .or(QProject.project.customer.lower().matches(regex));
            }
        }
        // the second search criterion
        if (!"-1".equals(criteria.getCreteria().get("status"))) {
            condExp = condExp != null ? condExp.and(QProject.project.status.eq(criteria.getCreteria().get("status")))
                    : QProject.project.status.eq(criteria.getCreteria().get("status"));
        }
        // get the result
        if (condExp != null) {
            Pageable page = new PageRequest(nextPage, num, Sort.Direction.fromString(sortDirection), sortColName);
            try {
                res.setSize(this.projectRepository.count(condExp));
                res.setLstResult(Lists.newArrayList(this.projectRepository.findAll(condExp, page)));
            } catch (Exception miscEx) {
                res.setLstResult(new ArrayList<Project>());
                res.setSize(0);
            }
        }
        return res;
    }

    /**
     * Get the information of one project by its id.
     * 
     * @param id
     * @return null (must re-implement in class implementation)
     */
    @Override
    public Project getById(String id) {
        return projectRepository.findOne(Long.parseLong(id));
    }

    /**
     * Update/add one project.
     * 
     * @param p
     *            project with new information
     * @return id of updated project
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = { Throwable.class })
    public Long update(ProjectVO vo, String mode) throws ProjectNumberAlreadyExistsException {
        if ("add".equals(mode) && this.getByPrjNumber(vo.getNumber()) != null) {
            throw new ProjectNumberAlreadyExistsException();
        }
        Project originalEntity = new Project();
        if ("update".equals(mode)) {
            originalEntity.setId(vo.getId());
            originalEntity.setVersion(vo.getVersion());
        }
        Department group = this.groupRepository.getOne(Long.parseLong(vo.getGroup()));
        originalEntity.setName(vo.getName());
        originalEntity.setCustomer(vo.getCustomer());
        originalEntity.setEndDate(vo.getEndDate());
        originalEntity.setFinishingDate(vo.getFinishingDate());
        originalEntity.setGroup(group);
        originalEntity.setNumber(vo.getNumber());
        originalEntity.setMembers(vo.getMembers());
        originalEntity.setStatus(vo.getStatus());
        try {
            return this.projectRepository.save(originalEntity).getId();
        } catch (ObjectOptimisticLockingFailureException ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    /**
     * Delete one project.
     * 
     * @param id
     *            project's id
     * @return void
     */
    @Override
    public void delete(Long id) {
        this.projectRepository.delete(id);
    }

    /**
     * Clone one project from existing one.
     * 
     * @param id
     *            project's id
     * @return id of clone one
     */
    @Override
    @Transactional(rollbackFor = { Throwable.class })
    public Long clone(Long id) {
        Project old = this.projectRepository.findOne(id);
        // get the max id
        Pageable page = new PageRequest(0, 1, Sort.Direction.DESC, "number");
        Integer nextPrjNumber = this.projectRepository.findAll(page).getContent().get(0).getNumber() + 1;
        // create the clone one
        Project clone = new Project(nextPrjNumber,
                old.getName() + "Maint." + Calendar.getInstance().get(Calendar.YEAR), new Date(), "NEW",
                old.getCustomer(), old.getGroup(), null, null);
        this.projectRepository.saveAndFlush(clone);
        // update the old one
        old.setStatus("MAI");
        this.projectRepository.saveAndFlush(old);
        // return result
        return clone.getId();
    }

    /**
     * Get the information of one project by its [number].
     * 
     * @param number
     * @return Project
     */
    @Override
    public Project getByPrjNumber(Integer num) {
        return this.projectRepository.findOne(QProject.project.number.eq(num));
    }
}
