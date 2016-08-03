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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import vn.elca.training.bootstrap.ProjectLoader;
import vn.elca.training.dom.Project;
import vn.elca.training.model.SearchResultVO;
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
    @Qualifier(value = "hibernateProjectService")
    private IProjectService projectService;
    @Value("${records.per.page}")
    private String recordsPerPage;
    @Autowired
    private UserPreference userPref;
    @Autowired
    MessageSource messageSource;
    private Logger log = Logger.getLogger(ApplicationController.class);

    @RequestMapping(value = "/", method = { RequestMethod.GET })
    ModelAndView main() {
        int numOfRecords = (int) projectService.countAll();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("currentPage", "1");
        model.put("beginIndex", "1");
        model.put("recordsPerPage", recordsPerPage);
        model.put("totalPage", this.getTotalPage(numOfRecords, Integer.valueOf(recordsPerPage)));
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
            @RequestParam(value = "status", defaultValue = "-1") String status, Model model, Locale locale) {
        this.userPref.setUserCriterion(searchCriteria);
        SearchResultVO<Project> resultVo;
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
            resultVo = projectService.findByNameAndStatus(searchCriteria, status, 0,
                    Integer.valueOf(this.recordsPerPage).intValue(), "id", "asc");
        } else if (!"all".equals(searchCriteria)) {
            resultVo = projectService.findByName(searchCriteria, 0, Integer.valueOf(this.recordsPerPage).intValue(),
                    "id", "asc");
        } else if (!"-1".equals(status)) {
            resultVo = projectService.findByStatus(status, 0, Integer.valueOf(this.recordsPerPage).intValue(), "id",
                    "asc");
        } else {
            resultVo = projectService.findAll(0, Integer.valueOf(this.recordsPerPage).intValue(), "id", "asc");
        }
        model.addAttribute("lstOfCurrentPage", resultVo.getLstResult());
        session.setAttribute("TOTAL_PAGE_OF_LATEST_QUERY",
                this.getTotalPage((int) resultVo.getSize(), Integer.valueOf(recordsPerPage)));
        return this.multilingualForStatus(resultVo.getLstResult(), locale);
    }

    /**
     * Get the [count] of latest query.
     * 
     * @param session
     * @return [count] value
     */
    @RequestMapping("/count")
    @ResponseBody
    String query(HttpSession session) {
        return session.getAttribute("TOTAL_PAGE_OF_LATEST_QUERY").toString();
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
    List<Project> paging(HttpSession session, @PathVariable String page, Model model, Locale locale) {
        SearchResultVO<Project> resultVo;
        String searchCriteria = (session.getAttribute("TEXT_SEARCH_CRITERIA") != null
                ? session.getAttribute("TEXT_SEARCH_CRITERIA").toString() : "all");
        String status = (session.getAttribute("STATUS_SEARCH_CRITERIA") != null
                ? session.getAttribute("STATUS_SEARCH_CRITERIA").toString() : "-1");
        String sortOrdering = (session.getAttribute("SORT_ORDERING") != null
                ? session.getAttribute("SORT_ORDERING").toString() : "asc");
        String sortName = (session.getAttribute("SORT_NAME") != null ? session.getAttribute("SORT_NAME").toString()
                : "id");
        // return the search result
        if (!"all".equals(searchCriteria) && !"-1".equals(status)) {
            resultVo = projectService.findByNameAndStatus(searchCriteria, status, 0,
                    Integer.valueOf(this.recordsPerPage).intValue() - 1, sortName, sortOrdering);
        } else if (!"all".equals(searchCriteria)) {
            resultVo = projectService.findByName(searchCriteria, Integer.valueOf(page),
                    Integer.valueOf(this.recordsPerPage).intValue() - 1, sortName, sortOrdering);
        } else if (!"-1".equals(status)) {
            resultVo = projectService.findByStatus(status, Integer.valueOf(page) - 1,
                    Integer.valueOf(this.recordsPerPage).intValue(), sortName, sortOrdering);
        } else {
            resultVo = projectService.findAll(Integer.valueOf(page) - 1,
                    Integer.valueOf(this.recordsPerPage).intValue(), sortName, sortOrdering);
        }
        model.addAttribute("lstOfCurrentPage", resultVo.getLstResult());
        return this.multilingualForStatus(resultVo.getLstResult(), locale);
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
    List<Project> sort(HttpSession session, @PathVariable String colName, Model model, Locale locale) {
        // process session variables
        String searchCriteria = (session.getAttribute("TEXT_SEARCH_CRITERIA") != null
                ? session.getAttribute("TEXT_SEARCH_CRITERIA").toString() : "all");
        String status = (session.getAttribute("STATUS_SEARCH_CRITERIA") != null
                ? session.getAttribute("STATUS_SEARCH_CRITERIA").toString() : "-1");
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
        List<Project> lstOfCurrentPage = projectService.subListByIndex(lst, 1,
                Integer.valueOf(this.recordsPerPage).intValue());
        model.addAttribute("lstOfCurrentPage", lstOfCurrentPage);
        return this.multilingualForStatus(lstOfCurrentPage, locale);
    }

    /**
     * Delete one specific project by its [id].
     * 
     * @param id
     * @return update project list
     */
    @RequestMapping("/delete/{id}")
    ModelAndView delete(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "unknown";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        log.info("Username: " + username);
        projectService.delete(id);
        // callback to main() to re-display [home] view
        return main();
    }

    @RequestMapping("/deleteitems")
    @ResponseBody
    String deleteMultiplePrj(@RequestParam(value = "prjIds[]") List<Long> prjIds) {
        for (Long id : prjIds) {
            this.projectService.delete(id);
        }
        // callback to main() to re-display [home] view
        return "success";
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
     * Convert from [String] type to [Date] type for [finishingDate] parameter.
     * The input formatting of [finishingDate] should be [dd/MM/yyyy"].
     * 
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    private int getTotalPage(int numOfRecords, int recordsPerPage) {
        int totalPage = numOfRecords / Integer.valueOf(recordsPerPage);
        System.out.println(numOfRecords % Integer.valueOf(recordsPerPage));
        if (numOfRecords % Integer.valueOf(recordsPerPage) > 0) {
            totalPage++;
        }
        return totalPage;
    }

    private List<Project> multilingualForStatus(List<Project> lst, Locale locale) {
        String newSta = messageSource.getMessage("status.new", null, locale);
        String finSta = messageSource.getMessage("status.fin", null, locale);
        String plaSta = messageSource.getMessage("status.pla", null, locale);
        String inpSta = messageSource.getMessage("status.inp", null, locale);
        for (Project p : lst) {
            switch (p.getStatus()) {
            case "NEW":
                p.setStatus(newSta);
                break;
            case "FIN":
                p.setStatus(finSta);
                break;
            case "PLA":
                p.setStatus(plaSta);
                break;
            default:
                p.setStatus(inpSta);
                break;
            }
        }
        return lst;
    }
}
