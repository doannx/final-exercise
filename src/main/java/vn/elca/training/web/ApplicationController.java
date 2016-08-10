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
import org.springframework.context.MessageSource;
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
import vn.elca.training.model.SearchCriteriaVO;
import vn.elca.training.model.SearchResultVO;
import vn.elca.training.model.UserPreference;
import vn.elca.training.service.IProjectService;
import vn.elca.training.util.StringUtil;

@Controller
@SessionAttributes("lstOfCurrentPage")
public class ApplicationController {
    private static final String DEFAULT_SORT_COLUMN = "id";
    private static final String DEFAULT_SORT_ORDER = "asc";
    private static final String ALL_STATUS_CRITERIA = "-1";
    private static final String ALL_TEXT_CRITERIA = "all";
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

    @RequestMapping(value = "/", method = { RequestMethod.GET })
    ModelAndView main() {
        SearchResultVO<Project> resultVo = getSearchResult(0, ALL_TEXT_CRITERIA, ALL_STATUS_CRITERIA, DEFAULT_SORT_ORDER, DEFAULT_SORT_COLUMN);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("currentPage", "1");
        model.put("beginIndex", "1");
        model.put("recordsPerPage", recordsPerPage);
        model.put("totalPage", this.getTotalPage((int) resultVo.getSize(), Integer.valueOf(recordsPerPage)));
        model.put("lstOfCurrentPage", resultVo.getLstResult());
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

    @RequestMapping(value = "/loginUrl", method = RequestMethod.GET)
    public String loginPage() {
        return "loginpage";
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
    List<Project> query(HttpSession session,
            @RequestParam(value = "name", defaultValue = ALL_TEXT_CRITERIA) String searchCriteria,
            @RequestParam(value = "status", defaultValue = ALL_STATUS_CRITERIA) String status, Model model,
            Locale locale) {
        this.userPref.setUserCriterion(searchCriteria);
        // in case search criteria is not [all] => save it into session
        if (!ALL_TEXT_CRITERIA.equals(this.userPref.getUserCriterion())) {
            session.setAttribute("TEXT_SEARCH_CRITERIA", searchCriteria);
        } else {
            session.removeAttribute("TEXT_SEARCH_CRITERIA");
        }
        if (!ALL_STATUS_CRITERIA.equals(status)) {
            session.setAttribute("STATUS_SEARCH_CRITERIA", status);
        } else {
            session.removeAttribute("STATUS_SEARCH_CRITERIA");
        }
        // update the search criteria object
        SearchResultVO<Project> resultVo = getSearchResult(0, searchCriteria, status, DEFAULT_SORT_ORDER, DEFAULT_SORT_COLUMN);
        // return the search result
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
    String count(HttpSession session) {
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
    List<Project> paging(HttpSession session, @PathVariable Integer page, Model model, Locale locale) {
        String searchCriteria = (session.getAttribute("TEXT_SEARCH_CRITERIA") != null ? session.getAttribute(
                "TEXT_SEARCH_CRITERIA").toString() : ALL_TEXT_CRITERIA);
        String status = (session.getAttribute("STATUS_SEARCH_CRITERIA") != null ? session.getAttribute(
                "STATUS_SEARCH_CRITERIA").toString() : ALL_STATUS_CRITERIA);
        String sortOrdering = (session.getAttribute("SORT_ORDERING") != null ? session.getAttribute("SORT_ORDERING")
                .toString() : DEFAULT_SORT_ORDER);
        String sortName = (session.getAttribute("SORT_NAME") != null ? session.getAttribute("SORT_NAME").toString()
                : DEFAULT_SORT_COLUMN);
        // return the search result
        SearchResultVO<Project> resultVo = getSearchResult(page - 1, searchCriteria, status, sortOrdering, sortName);
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
        String searchCriteria = (session.getAttribute("TEXT_SEARCH_CRITERIA") != null ? session.getAttribute(
                "TEXT_SEARCH_CRITERIA").toString() : ALL_TEXT_CRITERIA);
        String status = (session.getAttribute("STATUS_SEARCH_CRITERIA") != null ? session.getAttribute(
                "STATUS_SEARCH_CRITERIA").toString() : ALL_STATUS_CRITERIA);
        String sortOrdering = DEFAULT_SORT_ORDER;
        if (session.getAttribute("SORT_ORDERING") != null) {
            sortOrdering = session.getAttribute("SORT_ORDERING").toString();
            sortOrdering = (DEFAULT_SORT_ORDER.equals(sortOrdering) ? "desc" : DEFAULT_SORT_ORDER);
        } else {
            if (DEFAULT_SORT_COLUMN.equals(colName)) {
                sortOrdering = "desc";
            }
        }
        session.setAttribute("SORT_ORDERING", sortOrdering);
        session.setAttribute("SORT_NAME", colName);
        // return the search result
        SearchResultVO<Project> resultVo = getSearchResult(0, searchCriteria, status, sortOrdering, colName);
        model.addAttribute("lstOfCurrentPage", resultVo.getLstResult());
        return this.multilingualForStatus(resultVo.getLstResult(), locale);
    }

    /**
     * Delete one specific project by its [id].
     * 
     * @param id
     * @return update project list
     */
    @RequestMapping("/delete")
    @ResponseBody
    String delete(@RequestParam(value = "prjIds[]") List<Long> prjIds) {
        Project verifyPrj;
        for (Long id : prjIds) {
            verifyPrj = this.projectService.getById(String.valueOf(id));
            if ("NEW".equals(verifyPrj.getStatus().toUpperCase())) {
                this.projectService.delete(id);
            } else {
                return "fail";
            }
        }
        // callback to main() to re-display [home] view
        return "success";
    }

    /**
     * Clone new project from one specific project by its [id].
     * 
     * @param id
     * @return result of clone
     */
    @RequestMapping("/clone")
    @ResponseBody
    String clone(@RequestParam(value = DEFAULT_SORT_COLUMN) Long id) {
        return this.projectService.clone(id) != -1 ? "success" : "fail";
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
        if (!"".equals(filterCondition.get(DEFAULT_SORT_COLUMN))) {
            for (Iterator<Project> iterator = lst.iterator(); iterator.hasNext();) {
                p = iterator.next();
                if (p.getId() != Long.parseLong(filterCondition.get(DEFAULT_SORT_COLUMN))) {
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
                dateValue = dateFormat.format(p.getStartDate()).toString();
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

    /**
     * Get the total page based on search result [pagination].
     * 
     * @param numOfRecords
     * @param recordsPerPage
     * @return total page
     */
    private int getTotalPage(int numOfRecords, int recordsPerPage) {
        int totalPage = numOfRecords / Integer.valueOf(recordsPerPage);
        if (numOfRecords % Integer.valueOf(recordsPerPage) > 0) {
            totalPage++;
        }
        return totalPage;
    }

    /**
     * Apply [multilingual] for [status].
     * 
     * @param lst
     * @param locale
     * @return lst with [multilingual] status.
     */
    private List<Project> multilingualForStatus(List<Project> lst, Locale locale) {
        String newSta = messageSource.getMessage("status.new", null, locale);
        String finSta = messageSource.getMessage("status.fin", null, locale);
        String plaSta = messageSource.getMessage("status.pla", null, locale);
        String inpSta = messageSource.getMessage("status.inp", null, locale);
        String maiSta = messageSource.getMessage("status.mai", null, locale);
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
            case "INP":
                p.setStatus(inpSta);
                break;
            case "MAI":
                p.setStatus(maiSta);
                break;
            default:
                p.setStatus(newSta);
                break;
            }
        }
        return lst;
    }

    /**
     * Get search result.
     * 
     * @param page
     * @param searchCriteria
     * @param status
     * @param sortOrdering
     * @param sortName
     * @return SearchResultVO<Project>
     */
    private SearchResultVO<Project> getSearchResult(Integer page, String searchCriteria, String status,
            String sortOrdering, String sortName) {
        SearchResultVO<Project> resultVo;
        SearchCriteriaVO criteriaVo;
        if (ALL_TEXT_CRITERIA.equals(searchCriteria) && ALL_STATUS_CRITERIA.equals(status)) {
            resultVo = projectService.findAll(page, Integer.valueOf(this.recordsPerPage).intValue(), sortName,
                    sortOrdering);
        } else {
            criteriaVo = new SearchCriteriaVO();
            criteriaVo.getCreteria().put("text", searchCriteria);
            criteriaVo.getCreteria().put("status", status);
            resultVo = projectService.findByCriteria(criteriaVo, page, Integer.valueOf(this.recordsPerPage).intValue(),
                    sortName, sortOrdering);
        }
        return resultVo;
    }
}
