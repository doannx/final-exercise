package vn.elca.training.util;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MySafeSession {
    private static final Object LOCK = new Object();

    public Object getSessionLock(HttpSession session, String attributeName) {
        Object result = session.getAttribute(attributeName);
        if (result == null) {
            synchronized (LOCK) {
                // read again to ensure there is no one else update it
                result = session.getAttribute(attributeName);
                if (result == null) {
                    // now the value of this attribute is really null
                    result = new Object();
                    session.setAttribute(attributeName, result);
                }
            }
        }
        return result;
    }
}
