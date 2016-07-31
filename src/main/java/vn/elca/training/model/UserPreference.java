package vn.elca.training.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserPreference implements Serializable {
    private String userCriterion = "";

    public String getUserCriterion() {
        return userCriterion;
    }

    public void setUserCriterion(String userCriterion) {
        this.userCriterion = userCriterion;
    }
}
