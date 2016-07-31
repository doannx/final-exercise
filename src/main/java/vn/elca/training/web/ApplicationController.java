package vn.elca.training.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import vn.elca.training.dom.Project;
import vn.elca.training.model.UserPreference;
import vn.elca.training.service.IProjectService;
import vn.elca.training.util.StringUtil;

@Controller
@SessionAttributes("lstOfCurrentPage")
public class ApplicationController {
    /**
     * Declare the [DummyProjectService].
     */
    @Autowired
    @Qualifier(value = "dummyProjectService")
    private IProjectService projectService;
    @Value("${records.per.page}")
    private String recordsPerPage;
    @Autowired
    private UserPreference userPref;

    @RequestMapping(value = "/", method = { RequestMethod.GET })
    ModelAndView main() {
        int numOfRecords = (int) projectService.countAll();
        int totalPage = numOfRecords / Integer.valueOf(recordsPerPage);
        System.out.println(numOfRecords % Integer.valueOf(recordsPerPage));
        if (numOfRecords % Integer.valueOf(recordsPerPage) > 0) {
            totalPage++;
        }
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("currentPage", "1");
        model.put("beginIndex", "1");
        model.put("recordsPerPage", recordsPerPage);
        model.put("totalPage", totalPage);
        return new ModelAndView("search", model);
    }

    /**
     * Multilingual.
     * 
     * @param locale
     * @param model
     * @return
     */
    @RequestMapping(value = "/{lang}", method = RequestMethod.GET)
    public String multilingual(Locale locale, Model model) {
        return "search";
    }

    /**
     * Search project list by name or [all].
     * 
     * @param request
     * @param searchCriteria
     * @return List<Project>
     */
    @RequestMapping("/query")
    @ResponseBody
    List<Project> query(HttpSession session, @RequestParam(value = "name", defaultValue = "all") String searchCriteria,
            @RequestParam(value = "status", defaultValue = "-1") String status, Model model) {
        this.userPref.setUserCriterion(searchCriteria);
        List<Project> lstOfCurrentPage;
        // in case search criteria is not [all] => save it into session
        if (!"all".equals(this.userPref.getUserCriterion())) {
            session.setAttribute("TEXT_SEARCH_CRITERIA", searchCriteria);
        } else {
            session.removeAttribute("TEXT_SEARCH_CRITERIA");
        }
        if (!"-1".equals(status)) {
            session.setAttribute("STATUS_SEARCH_CRITERIA", status);
        } else {
            session.removeAttribute("STATUS_SEARCH_CRITERIA");
        }
        // return the search result
        if (!"all".equals(searchCriteria) && !"-1".equals(status)) {
            lstOfCurrentPage = projectService.findByNameAndStatus(searchCriteria, status, 1,
                    Integer.valueOf(this.recordsPerPage).intValue());
        } else if (!"all".equals(searchCriteria)) {
            lstOfCurrentPage = projectService.findByName(searchCriteria, 1, Integer.valueOf(this.recordsPerPage)
                    .intValue());
        } else if (!"-1".equals(status)) {
            lstOfCurrentPage = projectService.findByStatus(status, 1, Integer.valueOf(this.recordsPerPage).intValue());
        } else {
            lstOfCurrentPage = projectService.findAll(1, Integer.valueOf(this.recordsPerPage).intValue());
        }
        model.addAttribute("lstOfCurrentPage", lstOfCurrentPage);
        return lstOfCurrentPage;
    }

    /**
     * Paging.
     * 
     * @param http
     *            session
     * @param next
     *            page
     * @return List<Project>
     */
    @RequestMapping("/paging/{page}")
    @ResponseBody
    List<Project> paging(HttpSession session, @PathVariable String page, Model model) {
        String searchCriteria = (session.getAttribute("TEXT_SEARCH_CRITERIA") != null ? session.getAttribute(
                "TEXT_SEARCH_CRITERIA").toString() : "all");
        String status = (session.getAttribute("STATUS_SEARCH_CRITERIA") != null ? session.getAttribute(
                "STATUS_SEARCH_CRITERIA").toString() : "-1");
        String sortOrdering = (session.getAttribute("SORT_ORDERING") != null ? session.getAttribute("SORT_ORDERING")
                .toString() : "asc");
        String sortName = (session.getAttribute("SORT_NAME") != null ? session.getAttribute("SORT_NAME").toString()
                : "id");
        // return the search result
        List<Project> lst;
        if (!"all".equals(searchCriteria) && !"-1".equals(status)) {
            lst = projectService.findByNameAndStatus(searchCriteria, status);
        } else if (!"all".equals(searchCriteria)) {
            lst = projectService.findByName(searchCriteria);
        } else if (!"-1".equals(status)) {
            lst = projectService.findByStatus(status);
        } else {
            lst = projectService.findAll();
        }
        // sort
        if ("id".equals(sortName)) {
            lst = projectService.sortById(lst, sortOrdering);
        } else if ("name".equals(sortName)) {
            lst = projectService.sortByName(lst, sortOrdering);
        }
        // paging
        List<Project> lstOfCurrentPage = projectService.subListByIndex(lst, Integer.valueOf(page).intValue(), Integer
                .valueOf(this.recordsPerPage).intValue());
        model.addAttribute("lstOfCurrentPage", lstOfCurrentPage);
        return lstOfCurrentPage;
    }

    /**
     * Sorting.
     * 
     * @param http
     *            session
     * @param next
     *            page
     * @return List<Project>
     */
    @RequestMapping("/sort/{colName}")
    @ResponseBody
    List<Project> sort(HttpSession session, @PathVariable String colName, Model model) {
        // process session variables
        String searchCriteria = (session.getAttribute("TEXT_SEARCH_CRITERIA") != null ? session.getAttribute(
                "TEXT_SEARCH_CRITERIA").toString() : "all");
        String status = (session.getAttribute("STATUS_SEARCH_CRITERIA") != null ? session.getAttribute(
                "STATUS_SEARCH_CRITERIA").toString() : "-1");
        String sortOrdering = "asc";
        if (session.getAttribute("SORT_ORDERING") != null) {
            sortOrdering = session.getAttribute("SORT_ORDERING").toString();
            sortOrdering = ("asc".equals(sortOrdering) ? "desc" : "asc");
        } else {
            if ("id".equals(colName)) {
                sortOrdering = "desc";
            }
        }
        session.setAttribute("SORT_ORDERING", sortOrdering);
        session.setAttribute("SORT_NAME", colName);
        // return the search result
        List<Project> lst;
        if (!"all".equals(searchCriteria) && !"-1".equals(status)) {
            lst = projectService.findByNameAndStatus(searchCriteria, status);
        } else if (!"all".equals(searchCriteria)) {
            lst = projectService.findByName(searchCriteria);
        } else if (!"-1".equals(status)) {
            lst = projectService.findByStatus(status);
        } else {
            lst = projectService.findAll();
        }
        // sort
        if ("id".equals(colName)) {
            lst = projectService.sortById(lst, sortOrdering);
        } else if ("name".equals(colName)) {
            lst = projectService.sortByName(lst, sortOrdering);
        }
        // paging
        List<Project> lstOfCurrentPage = projectService.subListByIndex(lst, 1, Integer.valueOf(this.recordsPerPage)
                .intValue());
        model.addAttribute("lstOfCurrentPage", lstOfCurrentPage);
        return lstOfCurrentPage;
    }

    /**
     * Delete one specific project by its [id].
     * 
     * @param id
     * @return update project list
     */
    @PreAuthorize("hasRole('ADMIN'")
    @RequestMapping("/delete/{id}")
    ModelAndView delete(@PathVariable String id) {
        projectService.delete(id);
        // callback to main() to re-display [home] view
        return main();
    }

    /**
     * Filtering.
     * 
     * @param http
     *            session
     * @param next
     *            page
     * @return List<Project>
     */
    @RequestMapping("/filter")
    @ResponseBody
    List<Project> filter(@ModelAttribute("lstOfCurrentPage") List<Project> lstOfCurrentPage,
            @RequestParam("name") String name, @RequestParam Map<String, String> filterCondition) {
        List<Project> lst = new ArrayList<Project>(lstOfCurrentPage);
        Project p;
        String regex = "";
        if (!"".equals(filterCondition.get("id"))) {
            for (Iterator<Project> iterator = lst.iterator(); iterator.hasNext();) {
                p = iterator.next();
                if (p.getId() != Long.parseLong(filterCondition.get("id"))) {
                    iterator.remove();
                }
            }
        }
        if (!"".equals(filterCondition.get("name"))) {
            regex = StringUtil.buildRegexFromcriterion("*" + filterCondition.get("name") + "*");
            for (Iterator<Project> iterator = lst.iterator(); iterator.hasNext();) {
                p = iterator.next();
                if (!p.getName().toLowerCase().matches(regex)) {
                    iterator.remove();
                }
            }
        }
        if (!"".equals(filterCondition.get("status"))) {
            regex = StringUtil.buildRegexFromcriterion("*" + filterCondition.get("status") + "*");
            for (Iterator<Project> iterator = lst.iterator(); iterator.hasNext();) {
                p = iterator.next();
                if (!p.getStatus().toLowerCase().matches(regex)) {
                    iterator.remove();
                }
            }
        }
        if (!"".equals(filterCondition.get("customer"))) {
            regex = StringUtil.buildRegexFromcriterion("*" + filterCondition.get("customer") + "*");
            for (Iterator<Project> iterator = lst.iterator(); iterator.hasNext();) {
                p = iterator.next();
                if (!p.getCustomer().toLowerCase().matches(regex)) {
                    iterator.remove();
                }
            }
        }
        if (!"".equals(filterCondition.get("startdate"))) {
            regex = StringUtil.buildRegexFromcriterion("*" + filterCondition.get("startdate") + "*");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String dateValue = "";
            for (Iterator<Project> iterator = lst.iterator(); iterator.hasNext();) {
                p = iterator.next();
                dateValue = dateFormat.format(p.getFinishingDate()).toString();
                if (!dateValue.toLowerCase().matches(regex)) {
                    iterator.remove();
                }
            }
        }
        // paging
        return lst;
    }

    /**
     * Convert from [String] type to [Date] type for [finishingDate] parameter. The input formatting of [finishingDate]
     * should be [dd/MM/yyyy"].
     * 
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
