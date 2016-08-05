package vn.elca.training.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import vn.elca.training.convertor.GroupEditor;
import vn.elca.training.convertor.StatusEditor;
import vn.elca.training.dom.Department;
import vn.elca.training.dom.Member;
import vn.elca.training.dom.Project;
import vn.elca.training.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.GroupVO;
import vn.elca.training.model.ProjectVO;
import vn.elca.training.model.StatusVO;
import vn.elca.training.service.IGroupService;
import vn.elca.training.service.IMemberService;
import vn.elca.training.service.IProjectService;
import vn.elca.training.validator.ProjectValidator;

@Controller
public class UpdatationController {
    /**
     * Declare the [DummyProjectService].
     */
    @Autowired
    @Qualifier(value = "hibernateProjectService")
    private IProjectService projectService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    MessageSource messageSource;
    @Autowired
    private ProjectValidator validator;
    private Map<String, Member> lstMemberCache;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(GroupVO.class, new GroupEditor());
        binder.registerCustomEditor(StatusVO.class, new StatusEditor());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(List.class, "members", new CustomCollectionEditor(List.class) {
            protected Object convertElement(Object element) {
                if (element instanceof Member) {
                    System.out.println("Converting from Member to Member: " + element);
                    return element;
                }
                if (element instanceof String) {
                    Member staff = lstMemberCache.get(element);
                    System.out.println("Looking up member for visa " + element + ": " + staff);
                    return staff;
                }
                System.out.println("Don't know what to do with: " + element);
                return null;
            }
        });
    }

    @ModelAttribute("allGroups")
    public List<Department> populateGroups(Locale locale) {
        List<Department> groups = new ArrayList<Department>();
        groups.add(new Department(-1L, messageSource.getMessage("ddl.selectgroup", null, locale)));
        groups.addAll(this.groupService.findAll());
        return groups;
    }

    @ModelAttribute("allStatus")
    public List<StatusVO> populateStatus(Locale locale) {
        List<StatusVO> status = new ArrayList<StatusVO>();
        status.add(new StatusVO("", messageSource.getMessage("ddl.selectstatus", null, locale)));
        status.add(new StatusVO("FIN", messageSource.getMessage("status.fin", null, locale)));
        status.add(new StatusVO("INP", messageSource.getMessage("status.inp", null, locale)));
        status.add(new StatusVO("NEW", messageSource.getMessage("status.new", null, locale)));
        status.add(new StatusVO("MAI", messageSource.getMessage("status.mai", null, locale)));
        status.add(new StatusVO("PLA", messageSource.getMessage("status.pla", null, locale)));
        return status;
    }

    @ModelAttribute("allMember")
    public List<Member> populateMembers() {
        List<Member> members = this.memberService.findAll();
        this.lstMemberCache = new HashMap<String, Member>();
        for (Member m : this.memberService.findAll()) {
            this.lstMemberCache.put(String.valueOf(m.getVisa()), m);
        }
        return members;
    }

    /**
     * Get the details of one specific project by its [id].
     * 
     * @param id
     * @param model
     * @return [update] view and [ProjectVO] object in [model]
     */
    @RequestMapping("/detail/{id}")
    String detail(@PathVariable String id, HttpSession session, Model model) {
        Project entity = projectService.getById(id);
        ProjectVO vo = new ProjectVO(entity.getId(), entity.getName(), entity.getFinishingDate(), entity.getStatus(),
                entity.getCustomer(), entity.getMembers(), String.valueOf(entity.getGroup().getId()),
                entity.getEndDate());
        vo.setVersion(entity.getVersion());
        model.addAttribute("project", vo);
        session.setAttribute("UPDATE_MODE", "update");
        return "update";
    }

    /**
     * Prepare new empty form.
     * 
     * @param model
     * @return [update] view and new empty [ProjectVO] object in [model]
     */
    @RequestMapping("/add")
    String addNew(HttpSession session, Model model) {
        ProjectVO p = new ProjectVO();
        p.setStatus("NEW");
        model.addAttribute("project", p);
        session.setAttribute("UPDATE_MODE", "add");
        return "update";
    }

    /**
     * Add/Update project information.
     * 
     * @param projectVO
     * @return [home] view after updating changes.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("project") ProjectVO vo, BindingResult result, HttpSession session,
            SessionStatus status, Locale locale) throws ProjectNumberAlreadyExistsException {
        String mode = session.getAttribute("UPDATE_MODE").toString();
        // validate
        validator.validate(vo, result);
        if (result.hasErrors()) {
            session.setAttribute("ERROR_STATUS", true);
            return "update";
        } else {
            // once more check in [add] mode
            if ("add".equals(mode)) {
                if (this.projectService.getById(vo.getId().toString()) != null) {
                    result.addError(new FieldError("project", "id", messageSource.getMessage("error.idduplicate", null,
                            locale)));
                    return "update";
                }
            }
        }
        session.setAttribute("ERROR_STATUS", false);
        // store the project change in database
        projectService.update(vo, mode);
        // mark session complete
        status.setComplete();
        return "redirect:/";
    }

    @ExceptionHandler(ProjectNumberAlreadyExistsException.class)
    public ModelAndView handleTypeMismatchException(HttpServletRequest req, ProjectNumberAlreadyExistsException ex) {
        ModelAndView model = new ModelAndView();
        model.addObject("errorCode", ex.getMessage());
        model.addObject("errorURL", req.getRequestURL().toString());
        model.setViewName("myerrorpage");
        return model;
    }
}
