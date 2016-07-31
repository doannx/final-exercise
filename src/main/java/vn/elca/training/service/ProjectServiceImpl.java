package vn.elca.training.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mysema.query.types.expr.BooleanExpression;

import vn.elca.training.dao.IProjectRepository;
import vn.elca.training.dom.Project;
import vn.elca.training.dom.QProject;
import vn.elca.training.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.ProjectVO;
import vn.elca.training.model.SearchResultVO;
import vn.elca.training.util.StringUtil;

@Service
@Qualifier(value = "hibernateProjectService")
public class ProjectServiceImpl implements IProjectService {
    @Autowired
    private IProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        List<Project> lst = this.projectRepository.findAll();
        return this.sortById(lst, "asc");
    }

    /**
     * Find project(s) based on name. Search condition is LIKE operator.
     * 
     * @param name
     * @return List<Project>
     */
    @Override
    public List<Project> findByName(String name) {
        List<Project> lstResult = new ArrayList<Project>();
        String regex = StringUtil.buildRegexFromcriterion(name);
        // filter by [project name] or [customer name] or [project number]
        BooleanExpression prjAndCus = QProject.project.name.matches(regex).or(QProject.project.customer.matches(regex));
        try {
            Long id = Long.parseLong(name);
            BooleanExpression prjId = QProject.project.id.eq(id);
            lstResult = Lists.newArrayList(this.projectRepository.findAll(prjAndCus.or(prjId)));
        } catch (NumberFormatException ex) {
            lstResult = Lists.newArrayList(this.projectRepository.findAll(prjAndCus));
        }
        return this.sortById(lstResult, "asc");
    }

    /**
     * Find projects based on status.
     * 
     * @param name
     * @return List<Project>
     */
    @Override
    public List<Project> findByStatus(String status) {
        List<Project> lstResult = new ArrayList<Project>();
        // filter by [status]
        BooleanExpression prjStatus = QProject.project.status.eq(status);
        lstResult = Lists.newArrayList(this.projectRepository.findAll(prjStatus));
        return this.sortById(lstResult, "asc");
    }

    /**
     * Find project(s) based on name & status.
     * 
     * @param name
     * @param status
     * @return List<Project>
     */
    @Override
    public List<Project> findByNameAndStatus(String name, String status) {
        List<Project> lstResult = new ArrayList<Project>();
        String regex = StringUtil.buildRegexFromcriterion(name);
        // filter by [project name] or [customer name] or [project number]
        BooleanExpression prjAndCus = QProject.project.name.matches(regex).or(QProject.project.customer.matches(regex));
        BooleanExpression prjStatus = QProject.project.status.eq(status);
        try {
            Long id = Long.parseLong(name);
            BooleanExpression prjId = QProject.project.id.eq(id);
            lstResult = Lists.newArrayList(this.projectRepository.findAll(prjAndCus.or(prjId).and(prjStatus)));
        } catch (NumberFormatException ex) {
            lstResult = Lists.newArrayList(this.projectRepository.findAll(prjAndCus.and(prjStatus)));
        }
        return this.sortById(lstResult, "asc");
    }

    @Override
    public SearchResultVO<Project> findAll(int nextPage, int num) {
        Pageable page = new PageRequest(nextPage, num, Sort.Direction.ASC, "id");
        SearchResultVO<Project> res = new SearchResultVO<Project>();
        res.setLstResult(Lists.newArrayList(this.projectRepository.findAll(page)));
        res.setSize(this.projectRepository.count());
        return res;
    }

    @Override
    public SearchResultVO<Project> findByName(String name, int nextPage, int num) {
        SearchResultVO<Project> res = new SearchResultVO<Project>();
        String regex = StringUtil.buildRegexFromcriterion(name);

        Pageable page = new PageRequest(nextPage, num, Sort.Direction.ASC, "id");
        // filter by [project number]
        try {
            Long id = Long.parseLong(name);
            BooleanExpression prjId = QProject.project.id.eq(id);
            res.setSize(this.projectRepository.count(prjId));
            res.setLstResult(Lists.newArrayList(this.projectRepository.findAll(prjId, page)));
        } catch (NumberFormatException ex) {
            try {
                // filter by [project name] or [customer name]
                BooleanExpression prjAndCus = QProject.project.name.matches(regex)
                        .or(QProject.project.customer.matches(regex));
                res.setLstResult(Lists.newArrayList(this.projectRepository.findAll(prjAndCus, page)));
                res.setSize(this.projectRepository.count(prjAndCus.or(prjAndCus)));
            } catch (Exception miscEx) {
                res.setLstResult(new ArrayList<Project>());
                res.setSize(0);
            }
        }
        return res;
    }

    @Override
    public SearchResultVO<Project> findByNameAndStatus(String name, String status, int nextPage, int num) {
        SearchResultVO<Project> res = new SearchResultVO<Project>();
        String regex = StringUtil.buildRegexFromcriterion(name);
        Pageable page = new PageRequest(nextPage, num, Sort.Direction.ASC, "id");
        // filter by [project name] or [customer name] or [project number]
        BooleanExpression prjAndCus = QProject.project.name.matches(regex).or(QProject.project.customer.matches(regex));
        BooleanExpression prjStatus = QProject.project.status.eq(status);
        try {
            Long id = Long.parseLong(name);
            BooleanExpression prjId = QProject.project.id.eq(id);
            res.setLstResult(
                    Lists.newArrayList(this.projectRepository.findAll(prjAndCus.or(prjId).and(prjStatus), page)));
            res.setSize(this.projectRepository.count(prjStatus));
        } catch (NumberFormatException ex) {
            res.setLstResult(Lists.newArrayList(this.projectRepository.findAll(prjAndCus.and(prjStatus), page)));
            res.setSize(this.projectRepository.count(prjAndCus.and(prjStatus)));
        }
        return res;
    }

    @Override
    public SearchResultVO<Project> findByStatus(String status, int nextPage, int num) {
        SearchResultVO<Project> res = new SearchResultVO<Project>();
        Pageable page = new PageRequest(nextPage, num, Sort.Direction.ASC, "id");
        // filter by [status]
        BooleanExpression prjStatus = QProject.project.status.eq(status);
        res.setLstResult(Lists.newArrayList(this.projectRepository.findAll(prjStatus, page)));
        res.setSize(this.projectRepository.count(prjStatus));
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
    public Long update(Project p) {
        return this.projectRepository.saveAndFlush(p).getId();

    }

    @Override
    public Long update(ProjectVO p) {
        throw new NotYetImplementedException("ProjectService.update() is not implemented yet...");
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

    /**
     * Sort the project list based on id follow by ascending.
     * 
     * @param lst
     * @return
     */
    public List<Project> sortById(List<Project> lst, final String ordering) {
        lst.sort(new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                if ("asc".equals(ordering)) {
                    return (int) (o1.getId() - o2.getId());
                } else {
                    return (int) (o2.getId() - o1.getId());
                }
            }
        });
        return lst;
    }

    @Override
    public void delete(String id) {
        this.projectRepository.delete(Long.parseLong(id));
    }

    @Override
    public Long add(Project p) throws ProjectNumberAlreadyExistsException {
        this.projectRepository.saveAndFlush(p);
        return p.getId();
    }

    @Override
    public List<Project> sortByName(List<Project> lst, final String ordering) {
        lst.sort(new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                if ("asc".equals(ordering)) {
                    return (int) (o1.getName().compareTo(o2.getName()));
                } else {
                    return (int) (o2.getName().compareTo(o1.getName()));
                }
            }
        });
        return lst;
    }

    @Override
    public List<Project> subListByIndex(List<Project> lst, int nextPage, int num) {
        int beginIndex = (nextPage - 1) * num;
        int endIndex = beginIndex + num;
        endIndex = (endIndex >= lst.size() ? lst.size() : endIndex);
        return lst.subList(beginIndex, endIndex);
    }
}
