package vn.elca.training.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {
    private Logger log = Logger.getLogger(MySecurityConfiguration.class);

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ADMIN", "DBA");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/update").access("hasRole('ROLE_ADMIN')").and()
                .httpBasic();
        // ask whether this is an authenticated-user
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/delete/**").authenticated().and().formLogin()
                .loginPage("/loginUrl");
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
                    throws IOException, ServletException {
                log.error(arg2.getMessage());
                arg1.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        });
        // ask whether this authenticated-user is an ADMIN
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/delete/**").access("hasRole('ROLE_ADMIN')");
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest arg0, HttpServletResponse arg1, AccessDeniedException arg2)
                    throws IOException, ServletException {
                log.error(arg2.getMessage());
                arg1.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        });
        http.authorizeRequests().antMatchers("/console/**").permitAll();
        http.formLogin().successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
                    throws IOException, ServletException {
                log.info("onAuthenticationSuccess");
            }
        });
        http.formLogin().failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest arg0, HttpServletResponse arg1,
                    AuthenticationException arg2) throws IOException, ServletException {
                log.info("onAuthenticationFailure");
            }
        });
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
