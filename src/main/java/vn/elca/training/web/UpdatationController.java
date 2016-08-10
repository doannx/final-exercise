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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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

import vn.elca.training.dom.Employee;
import vn.elca.training.dom.Group;
import vn.elca.training.dom.Project;
import vn.elca.training.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.ProjectVO;
import vn.elca.training.model.StatusVO;
import vn.elca.training.service.IGroupService;
import vn.elca.training.service.IMemberService;
import vn.elca.training.service.IProjectService;
import vn.elca.training.validator.ProjectValidator;

@Controller
public class UpdatationController {
    /**
     * Declare the [hibernateProjectService].
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
    private Map<String, Employee> lstMemberCache;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.addValidators(this.validator);
        binder.registerCustomEditor(List.class, "members", new CustomCollectionEditor(List.class) {
            protected Object convertElement(Object element) {
                if (element instanceof Employee) {
                    return element;
                }
                if (element instanceof String) {
                    Employee staff = lstMemberCache.get(element);
                    return staff;
                }
                return null;
            }
        });
    }

    @ModelAttribute("allGroups")
    public List<Group> populateGroups(Locale locale) {
        List<Group> groups = new ArrayList<Group>();
        groups.addAll(this.groupService.findAll());
        return groups;
    }

    @ModelAttribute("allStatus")
    public List<StatusVO> populateStatus(Locale locale) {
        List<StatusVO> status = new ArrayList<StatusVO>();
        status.add(new StatusVO("FIN", messageSource.getMessage("status.fin", null, locale)));
        status.add(new StatusVO("INP", messageSource.getMessage("status.inp", null, locale)));
        status.add(new StatusVO("NEW", messageSource.getMessage("status.new", null, locale)));
        status.add(new StatusVO("MAI", messageSource.getMessage("status.mai", null, locale)));
        status.add(new StatusVO("PLA", messageSource.getMessage("status.pla", null, locale)));
        return status;
    }

    @ModelAttribute("allMember")
    public List<Employee> populateMembers() {
        List<Employee> members = this.memberService.findAll();
        this.lstMemberCache = new HashMap<String, Employee>();
        for (Employee m : members) {
            m.setDisplayName(m.getVisa() + ":" + m.getFirstName() + " " + m.getLastName());
            this.lstMemberCache.put(String.valueOf(m.getId()), m);
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
        ProjectVO vo = new ProjectVO(entity.getId(), entity.getNumber(), entity.getName(), entity.getStartDate(),
                entity.getStatus(), entity.getCustomer(), entity.getMembers(),
                String.valueOf(entity.getGroup().getId()), entity.getEndDate());
        vo.setVersion(entity.getVersion());
        model.addAttribute("project", vo);
        session.setAttribute("UPDATE_MODE", "update");
        session.setAttribute("ERROR_STATUS", false);
        session.setAttribute("ERROR_CONTENT", "");
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
        session.setAttribute("ERROR_STATUS", false);
        session.setAttribute("ERROR_CONTENT", "");
        return "update";
    }

    /**
     * Add/Update project information.
     * 
     * @param projectVO
     * @return [home] view after updating changes.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String save(@ModelAttribute("project") @Valid ProjectVO vo, BindingResult result, HttpSession session,
            SessionStatus status, Locale locale) throws ProjectNumberAlreadyExistsException {
        String mode = session.getAttribute("UPDATE_MODE").toString();
        // validate
        if (result.hasErrors()) {
            session.setAttribute("ERROR_STATUS", true);
            session.setAttribute("ERROR_CONTENT", messageSource.getMessage("error.all", null, locale));
            return "update";
        } else {
            // once more check in [add] mode
            if ("add".equals(mode)) {
                if (this.projectService.getByPrjNumber(vo.getNumber()) != null) {
                    result.addError(new FieldError("project", "number", messageSource.getMessage("error.idduplicate",
                            null, locale)));
                    return "update";
                }
            }
        }
        session.setAttribute("ERROR_STATUS", false);
        session.setAttribute("ERROR_CONTENT", "");
        // store the project change in database
        try {
            projectService.update(vo, mode);
        } catch (ObjectOptimisticLockingFailureException ex) {
            session.setAttribute("ERROR_STATUS", true);
            session.setAttribute("ERROR_CONTENT", messageSource.getMessage("error.concurrency", null, locale));
            return "update";
        }
        // mark session complete
        status.setComplete();
        return "redirect:/";
    }

    /**
     * Exception handler for ProjectNumberAlreadyExistsException.
     * 
     * @param req
     * @param ex
     * @return [myerrorpage]
     */
    @ExceptionHandler(ProjectNumberAlreadyExistsException.class)
    public ModelAndView handleTypeMismatchException(HttpServletRequest req, ProjectNumberAlreadyExistsException ex) {
        ModelAndView model = new ModelAndView();
        model.addObject("errorCode", ex.getMessage());
        model.addObject("errorURL", req.getRequestURL().toString());
        model.setViewName("myerrorpage");
        return model;
    }
}