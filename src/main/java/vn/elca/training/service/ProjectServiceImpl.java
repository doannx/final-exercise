package vn.elca.training.service;

import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mysema.query.types.expr.BooleanExpression;

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

@Service
@Qualifier(value = "hibernateProjectService")
public class ProjectServiceImpl implements IProjectService {
    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IGroupRepository groupRepository;

    @Override
    public SearchResultVO<Project> findAll(int nextPage, int num, String sortColName, String sortDirection) {
        Pageable page = new PageRequest(nextPage, num, Sort.Direction.fromString(sortDirection), sortColName);
        SearchResultVO<Project> res = new SearchResultVO<Project>();
        res.setLstResult(Lists.newArrayList(this.projectRepository.findAll(page)));
        res.setSize(this.projectRepository.count());
        return res;
    }

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
                Long id = Long.parseLong(criteria.getCreteria().get("text"));
                condExp = QProject.project.id.eq(id);
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
     * Update one project.
     * 
     * @param p
     *            project with new information
     * @return id of updated project
     * @throws Exception
     */
    @Override
    public Long update(ProjectVO vo, String mode) throws ProjectNumberAlreadyExistsException {
        Project originalEntity = new Project();
        if (this.projectRepository.findOne(vo.getId()) != null) {
            if ("add".equals(mode)) {
                throw new ProjectNumberAlreadyExistsException();
            } else {
                originalEntity = this.projectRepository.findOne(vo.getId());
            }
        }
        Department group = this.groupRepository.getOne(Long.parseLong(vo.getGroup()));
        originalEntity.setName(vo.getName());
        originalEntity.setCustomer(vo.getCustomer());
        originalEntity.setEndDate(vo.getEndDate());
        originalEntity.setFinishingDate(vo.getFinishingDate());
        originalEntity.setGroup(group);
        originalEntity.setId(vo.getId());
        originalEntity.setMembers(vo.getMembers());
        originalEntity.setStatus(vo.getStatus());
        originalEntity.setVersion(vo.getVersion());
        return this.projectRepository.saveAndFlush(originalEntity).getId();
    }

    /**
     * Count all existing projects.
     * 
     * @return the number of projects
     */
    @Override
    public long countAll() {
        return this.projectRepository.count();
    }

    @Override
    public void delete(Long id) {
        this.projectRepository.delete(id);
    }
}
