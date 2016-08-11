package vn.elca.training.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import vn.elca.training.dom.Employee;
import vn.elca.training.dom.Group;
import vn.elca.training.dom.Project;
import vn.elca.training.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.ProjectVO;
import vn.elca.training.model.Status;
import vn.elca.training.model.StatusVO;
import vn.elca.training.service.IEmployeeService;
import vn.elca.training.service.IGroupService;
import vn.elca.training.service.IProjectService;
import vn.elca.training.util.Constants;
import vn.elca.training.validator.ProjectValidator;

import com.google.common.collect.Lists;

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
    private IEmployeeService memberService;
    @Autowired
    MessageSource messageSource;
    private Map<String, Employee> lstMemberCache;
    private static final Logger LOGGER = Logger.getLogger(UpdatationController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.addValidators(new ProjectValidator());
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
        status.add(new StatusVO(Status.FIN.toString(), messageSource.getMessage("status.fin", null, locale)));
        status.add(new StatusVO(Status.INP.toString(), messageSource.getMessage("status.inp", null, locale)));
        status.add(new StatusVO(Status.NEW.toString(), messageSource.getMessage("status.new", null, locale)));
        status.add(new StatusVO(Status.MAI.toString(), messageSource.getMessage("status.mai", null, locale)));
        status.add(new StatusVO(Status.PLA.toString(), messageSource.getMessage("status.pla", null, locale)));
        return status;
    }

    @ModelAttribute("allMember")
    public List<Employee> populateMembers() {
        List<Employee> members = this.memberService.findAll();
        this.lstMemberCache = new HashMap<String, Employee>();
        for (Employee m : members) {
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
    String detail(@PathVariable Long id, HttpSession session, Model model) {
        Project entity = projectService.getById(id);
        ProjectVO vo = new ProjectVO(entity.getId(), entity.getNumber(), entity.getName(), entity.getStartDate(),
                entity.getStatus().toString(), entity.getCustomer(), Lists.newArrayList(entity.getMembers()),
                String.valueOf(entity.getGroup().getId()), entity.getEndDate());
        vo.setVersion(entity.getVersion());
        model.addAttribute("project", vo);
        model.addAttribute("mode", "update");
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
        p.setStatus(Status.NEW.toString());
        model.addAttribute("project", p);
        return "update";
    }

    /**
     * Add/Update project information.
     * 
     * @param projectVO
     * @return [home] view after updating changes.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    String save(@ModelAttribute("project") @Valid ProjectVO vo, BindingResult result, HttpSession session,
            SessionStatus status, Locale locale, Model model) throws ProjectNumberAlreadyExistsException {
        // check whether there is validation error
        if (result.hasErrors()) {
            model.addAttribute("errorStatus", true);
            model.addAttribute("errorContent", messageSource.getMessage("error.all", null, locale));
            return "update";
        } else {
            // once more check in [add] mode
            if (vo.getId() == null) {
                if (this.projectService.getByPrjNumber(vo.getNumber()) != null) {
                    result.addError(new FieldError("project", "number", messageSource.getMessage("error.idduplicate",
                            null, locale)));
                    return "update";
                }
            }
        }
        // store the project change in database
        try {
            projectService.update(vo);
        } catch (ObjectOptimisticLockingFailureException ex1) {
            LOGGER.error(ex1.getMessage());
            model.addAttribute("errorStatus", true);
            model.addAttribute("errorContent", messageSource.getMessage("error.concurrency", null, locale));
            return "update";
        } catch (ProjectNumberAlreadyExistsException ex2) {
            LOGGER.error("ProjectNumberAlreadyExists");
            model.addAttribute("errorStatus", true);
            model.addAttribute("errorContent", messageSource.getMessage("error.idduplicate", null, locale));
            return "update";
        }
        // mark session complete
        status.setComplete();
        return "redirect:/";
    }
}