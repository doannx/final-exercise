package vn.elca.training.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.elca.training.dao.IGroupRepository;
import vn.elca.training.dao.IProjectRepository;
import vn.elca.training.dom.Employee;
import vn.elca.training.dom.Group;
import vn.elca.training.dom.Project;
import vn.elca.training.dom.QProject;
import vn.elca.training.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.ProjectVO;
import vn.elca.training.model.SearchCriteriaVO;
import vn.elca.training.model.SearchResultVO;
import vn.elca.training.model.Status;
import vn.elca.training.util.Constants;
import vn.elca.training.util.StringUtil;

import com.google.common.collect.Lists;
import com.mysema.query.types.expr.BooleanExpression;

@Service
@Qualifier(value = "hibernateProjectService")
@Transactional(rollbackFor = { Throwable.class })
public class ProjectServiceImpl implements IProjectService {
    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IGroupRepository groupRepository;

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
        if (criteria.getCreteria().get("number") != null) {
            // filter by [project number]
            Integer id = Integer.parseInt(criteria.getCreteria().get("number"));
            condExp = QProject.project.number.eq(id);
        } else if (criteria.getCreteria().get("name") != null) {
            String regex = StringUtil.buildRegexFromcriterion(criteria.getCreteria().get("name").toLowerCase());
            // filter by [project name] and [customer name]
            condExp = QProject.project.name.lower().matches(regex).or(QProject.project.customer.lower().matches(regex));
        }
        // the second search criterion
        if (!Constants.NOT_SELECTED_YET.equals(criteria.getCreteria().get("status"))) {
            condExp = condExp != null ? condExp.and(QProject.project.status.eq(Status.valueOf(criteria.getCreteria()
                    .get("status")))) : QProject.project.status
                    .eq(Status.valueOf(criteria.getCreteria().get("status")));
        }
        // get the result
        if (condExp != null) {
            Pageable page = new PageRequest(nextPage, num, Sort.Direction.fromString(sortDirection), sortColName);
            res.setSize(this.projectRepository.count(condExp));
            res.setLstResult(Lists.newArrayList(this.projectRepository.findAll(condExp, page)));
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
    public Project getById(Long id) {
        return projectRepository.findOne(id);
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
    public Long update(ProjectVO vo) throws ProjectNumberAlreadyExistsException {
        if (vo.getId() == null && this.getByPrjNumber(vo.getNumber()) != null) {
            throw new ProjectNumberAlreadyExistsException();
        }
        Project originalEntity = new Project();
        if (vo.getId() != null) {
            originalEntity.setId(vo.getId());
            originalEntity.setVersion(vo.getVersion());
        }
        Group group = this.groupRepository.getOne(Long.parseLong(vo.getGroup()));
        originalEntity.setName(vo.getName());
        originalEntity.setCustomer(vo.getCustomer());
        originalEntity.setEndDate(vo.getEndDate());
        originalEntity.setStartDate(vo.getStartDate());
        originalEntity.setGroup(group);
        originalEntity.setNumber(vo.getNumber());
        originalEntity.setStatus(Status.valueOf(vo.getStatus()));
        if (vo.getMembers() != null) {
            originalEntity.getMembers().clear();
            originalEntity.getMembers().addAll(vo.getMembers());
        }
        return this.projectRepository.save(originalEntity).getId();
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
    public Long clone(Long id) {
        Project old = this.projectRepository.findOne(id);
        // get the max id
        Pageable page = new PageRequest(0, 1, Sort.Direction.DESC, "number");
        Integer nextPrjNumber = this.projectRepository.findAll(page).getContent().get(0).getNumber() + 1;
        // create the clone one
        Project clone = new Project(nextPrjNumber,
                old.getName() + "Maint." + Calendar.getInstance().get(Calendar.YEAR), new Date(), Status.NEW,
                old.getCustomer(), old.getGroup(), null, new ArrayList<Employee>());
        this.projectRepository.saveAndFlush(clone);
        // update the old one
        old.setStatus(Status.MAI);
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
