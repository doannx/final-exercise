package vn.elca.training.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import vn.elca.training.model.UserPreference;
import vn.elca.training.util.MySafeSession;

@Controller
public class SessionScopedController extends AbstractController {
    @Autowired
    private UserPreference userPref;
    @Autowired
    private MySafeSession safeSession;

    SessionScopedController() {
        System.out.println("setSynchronizeOnSession");
        this.setSynchronizeOnSession(true);
    }

    @RequestMapping("/testSessionScope")
    String detail(HttpSession session, Model model) {
        model.addAttribute("VALUE_FROM_SESSION_SCOPED_BEAN", this.userPref.getUserCriterion());
        // test thread-safe
        System.out.println("test thread-safe session:" + this.safeSession.getSessionLock(session, "SEARCH_CRITERIA"));
        // test session scoped Bean
        System.out.println("test session scoped bean:" + userPref.getUserCriterion());
        return "sessionscopedbean";
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("handleRequestInternal");
        return null;
    }
}
