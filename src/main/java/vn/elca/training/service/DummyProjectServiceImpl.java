package vn.elca.training.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import vn.elca.training.dom.Project;
import vn.elca.training.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.MemberVO;
import vn.elca.training.model.ProjectVO;
import vn.elca.training.util.StringUtil;

/**
 * This is the implementation of IProjectService with few dummy data records.
 * 
 * @author xdg, 20160714
 *
 */
@Service
@Qualifier(value = "dummyProjectService")
public class DummyProjectServiceImpl implements IProjectService {
    private Map<Long, ProjectVO> projectRepository;

    public DummyProjectServiceImpl() {
        projectRepository = new HashMap<Long, ProjectVO>();
        // save some dummy test data
        List<MemberVO> members = new ArrayList<MemberVO>();
        members.add(new MemberVO("XDG1", "XDG1: NGUYEN XUAN DOAN 1"));
        members.add(new MemberVO("XDG2", "XDG2: NGUYEN XUAN DOAN 2"));
        members.add(new MemberVO("XDG3", "XDG3: NGUYEN XUAN DOAN 3"));
        members.add(new MemberVO("XDG4", "XDG4: NGUYEN XUAN DOAN 4"));
        members.add(new MemberVO("XDG5", "XDG5: NGUYEN XUAN DOAN 5"));
        ProjectVO dummyPrj = new ProjectVO(1L, "KSTA", new Date(), "NEW", "CANON", members, "1");
        projectRepository.put(dummyPrj.getId(), dummyPrj);
        dummyPrj = new ProjectVO(2L, "LAGAPEO", new Date(), "NEW", "KGB", members, "1");
        projectRepository.put(dummyPrj.getId(), dummyPrj);
        dummyPrj = new ProjectVO(3L, "ZHQUEST", new Date(), "NEW", "MGB Tourism", members, "1");
        projectRepository.put(dummyPrj.getId(), dummyPrj);
        dummyPrj = new ProjectVO(4L, "SECUTIX", new Date(), "NEW", "Les Retaitest Popularies", members, "1");
        projectRepository.put(dummyPrj.getId(), dummyPrj);
        dummyPrj = new ProjectVO(5L, "Honda@", new Date(), "NEW", "HONDA", members, "1");
        projectRepository.put(dummyPrj.getId(), dummyPrj);
        dummyPrj = new ProjectVO(6L, "FZ16", new Date(), "NEW", "YAMAHA", members, "1");
        projectRepository.put(dummyPrj.getId(), dummyPrj);
        dummyPrj = new ProjectVO(7L, "946", new Date(), "NEW", "VESPA", members, "1");
        projectRepository.put(dummyPrj.getId(), dummyPrj);
        dummyPrj = new ProjectVO(8L, "Libery", new Date(), "NEW", "VESPA", members, "1");
        projectRepository.put(dummyPrj.getId(), dummyPrj);
    }

    /**
     * Convert from ProjectVO to Project.
     * 
     * @param vo
     * @return Project object
     */
    private Project convert(ProjectVO vo) {
        return new Project(vo.getId(), vo.getName(), vo.getFinishingDate(), vo.getStatus(), vo.getCustomer());
    }

    @Override
    public List<Project> findAll() {
        List<Project> lst = new ArrayList<Project>();
        for (ProjectVO p : this.projectRepository.values()) {
            lst.add(this.convert(p));
        }
        // return dummy project list
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
        // loop through dummy data and select the right ones
        for (ProjectVO p : this.projectRepository.values()) {
            if (p.getName().toLowerCase().matches(regex) || p.getCustomer().toLowerCase().matches(regex)
                    || String.valueOf(p.getId()).matches(regex)) {
                lstResult.add(this.convert(p));
            }
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
        for (ProjectVO p : this.projectRepository.values()) {
            if (p.getStatus().equals(status)) {
                lstResult.add(this.convert(p));
            }
        }
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
        // find by [name]
        List<Project> lstResultByName = this.findByName(name);
        // filter by [status]
        for (Project p : lstResultByName) {
            if (p.getStatus().equals(status)) {
                lstResult.add(p);
            }
        }
        return this.sortById(lstResult, "asc");
    }

    @Override
    public List<Project> findAll(int nextPage, int num) {
        // return dummy project list
        List<Project> lst = this.findAll();
        int beginIndex = (nextPage - 1) * num;
        int endIndex = beginIndex + num;
        endIndex = (endIndex >= lst.size() ? lst.size() : endIndex);
        return this.sortById(lst.subList(beginIndex, endIndex), "asc");
    }

    @Override
    public List<Project> findByName(String name, int nextPage, int num) {
        // return dummy project list
        List<Project> lst = this.findByName(name);
        int beginIndex = (nextPage - 1) * num;
        int endIndex = beginIndex + num;
        endIndex = (endIndex >= lst.size() ? lst.size() : endIndex);
        return this.sortById(lst.subList(beginIndex, endIndex), "asc");
    }

    @Override
    public List<Project> findByNameAndStatus(String name, String status, int nextPage, int num) {
        // return dummy project list
        List<Project> lst = this.findByNameAndStatus(name, status);
        int beginIndex = (nextPage - 1) * num;
        int endIndex = beginIndex + num;
        endIndex = (endIndex >= lst.size() ? lst.size() : endIndex);
        return this.sortById(lst.subList(beginIndex, endIndex), "asc");
    }

    @Override
    public List<Project> findByStatus(String status, int nextPage, int num) {
        // return dummy project list
        List<Project> lst = this.findByStatus(status);
        int beginIndex = (nextPage - 1) * num;
        int endIndex = beginIndex + num;
        endIndex = (endIndex >= lst.size() ? lst.size() : endIndex);
        return this.sortById(lst.subList(beginIndex, endIndex), "asc");
    }

    /**
     * Get the information of one project by its id.
     * 
     * @param id
     * @return null (must re-implement in class implementation)
     */
    @Override
    public ProjectVO getById(String id) {
        return projectRepository.get(Long.parseLong(id));
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
    public Long update(ProjectVO p) {
        return (this.projectRepository.put(p.getId(), p).getId());
    }

    @Override
    public Long update(Project p) {
        throw new NotYetImplementedException("ProjectService.findByStatus() is not implemented yet...");
    }

    /**
     * Count all existing projects.
     * 
     * @return the number of projects
     */
    @Override
    public long countAll() {
        return this.projectRepository.size();
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
        this.projectRepository.remove(Long.parseLong(id));
    }

    @Override
    public Long add(ProjectVO p) throws ProjectNumberAlreadyExistsException {
        if (this.projectRepository.containsKey(p.getId())) {
            throw new ProjectNumberAlreadyExistsException();
        }
        this.projectRepository.put(p.getId(), p);
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