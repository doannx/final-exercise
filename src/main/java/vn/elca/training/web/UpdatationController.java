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
import vn.elca.training.dom.Group;
import vn.elca.training.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.GroupVO;
import vn.elca.training.model.MemberVO;
import vn.elca.training.model.ProjectVO;
import vn.elca.training.model.StatusVO;
import vn.elca.training.service.IGroupService;
import vn.elca.training.service.IProjectService;
import vn.elca.training.validator.ProjectValidator;

@Controller
public class UpdatationController {
    /**
     * Declare the [DummyProjectService].
     */
    @Autowired
    @Qualifier(value = "dummyProjectService")
    private IProjectService projectService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    MessageSource messageSource;
    @Autowired
    private ProjectValidator validator;
    private Map<String, MemberVO> lstMemberCache;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(GroupVO.class, new GroupEditor());
        binder.registerCustomEditor(StatusVO.class, new StatusEditor());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(List.class, "members", new CustomCollectionEditor(List.class) {
            protected Object convertElement(Object element) {
                if (element instanceof MemberVO) {
                    System.out.println("Converting from MemberVO to MemberVO: " + element);
                    return element;
                }
                if (element instanceof String) {
                    MemberVO staff = lstMemberCache.get(element);
                    System.out.println("Looking up member for visa " + element + ": " + staff);
                    return staff;
                }
                System.out.println("Don't know what to do with: " + element);
                return null;
            }
        });
    }

    @ModelAttribute("allGroups")
    public List<Group> populateGroups(Locale locale) {
        List<Group> groups = new ArrayList<Group>();
        groups.add(new Group(-1L, messageSource.getMessage("ddl.selectgroup", null, locale)));
        groups.addAll(this.groupService.findAll());
        return groups;
    }

    @ModelAttribute("allStatus")
    public List<StatusVO> populateStatus(Locale locale) {
        List<StatusVO> status = new ArrayList<StatusVO>();
        status.add(new StatusVO("", messageSource.getMessage("ddl.selectstatus", null, locale)));
        status.add(new StatusVO("NEW", messageSource.getMessage("status.new", null, locale)));
        status.add(new StatusVO("FIN", messageSource.getMessage("status.fin", null, locale)));
        status.add(new StatusVO("PLA", messageSource.getMessage("status.pla", null, locale)));
        status.add(new StatusVO("INP", messageSource.getMessage("status.inp", null, locale)));
        return status;
    }

    @ModelAttribute("allMember")
    public List<MemberVO> populateMembers() {
        List<MemberVO> members = new ArrayList<MemberVO>();
        members.add(new MemberVO("XDG1", "XDG1: NGUYEN XUAN DOAN 1"));
        members.add(new MemberVO("XDG2", "XDG2: NGUYEN XUAN DOAN 2"));
        members.add(new MemberVO("XDG3", "XDG3: NGUYEN XUAN DOAN 3"));
        members.add(new MemberVO("XDG4", "XDG4: NGUYEN XUAN DOAN 4"));
        members.add(new MemberVO("XDG5", "XDG5: NGUYEN XUAN DOAN 5"));
        members.add(new MemberVO("XDG6", "XDG6: NGUYEN XUAN DOAN 6"));
        members.add(new MemberVO("XDG7", "XDG7: NGUYEN XUAN DOAN 7"));
        members.add(new MemberVO("XDG8", "XDG8: NGUYEN XUAN DOAN 8"));
        members.add(new MemberVO("XDG9", "XDG9: NGUYEN XUAN DOAN 9"));
        members.add(new MemberVO("XDG10", "XDG10: NGUYEN XUAN DOAN 10"));
        members.add(new MemberVO("XDG11", "XDG11: NGUYEN XUAN DOAN 11"));
        members.add(new MemberVO("XDG12", "XDG12: NGUYEN XUAN DOAN 12"));
        this.lstMemberCache = new HashMap<String, MemberVO>();
        for (MemberVO m : members) {
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
        model.addAttribute("project", projectService.getById(id));
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
    public String submitForm(@ModelAttribute("project") ProjectVO projectVO, BindingResult result, HttpSession session,
            SessionStatus status, Locale locale) throws ProjectNumberAlreadyExistsException {
        String mode = session.getAttribute("UPDATE_MODE").toString();
        // validate
        validator.validate(projectVO, result);
        if (result.hasErrors()) {
            session.setAttribute("ERROR_STATUS", true);
            return "update";
        } else {
            // once more check in [add] mode
            if ("add".equals(mode)) {
                if (this.projectService.getById(projectVO.getId().toString()) != null) {
                    result.addError(new FieldError("project", "id",
                            messageSource.getMessage("error.idduplicate", null, locale)));
                    return "update";
                }
            }
        }
        session.setAttribute("ERROR_STATUS", false);
        // Store the employee information in database
        if ("update".equals(mode)) {
            projectService.update(projectVO);
        } else {
            projectService.add(projectVO);
        }
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
