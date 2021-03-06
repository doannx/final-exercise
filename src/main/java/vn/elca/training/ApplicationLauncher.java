package vn.elca.training;

import java.util.Properties;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import vn.elca.training.config.MyRepositoryConfiguration;
import vn.elca.training.config.MySecurityConfiguration;
import vn.elca.training.service.IGroupService;
import vn.elca.training.service.IProjectService;
import vn.elca.training.validator.ProjectValidator;
import vn.elca.training.web.ApplicationController;
import vn.elca.training.web.GlobalDefaultExceptionHandler;
import vn.elca.training.web.SessionScopedController;
import vn.elca.training.web.UpdatationController;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = { ApplicationLauncher.class, ApplicationController.class, IProjectService.class,
        SessionScopedController.class, UpdatationController.class, GlobalDefaultExceptionHandler.class,
        ProjectValidator.class, IGroupService.class })
@Import(value = { MySecurityConfiguration.class, MyRepositoryConfiguration.class })
@PropertySource({ "classpath:/application.properties", "classpath:/message.properties",
        "classpath:/message_fr.properties" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationLauncher extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationLauncher.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public static MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("message");
        return resourceBundleMessageSource;
    }

    @Bean
    ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    @Bean
    public SimpleUrlHandlerMapping sampleServletMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MAX_VALUE - 2);
        Properties urlProperties = new Properties();
        urlProperties.put("/index", "sessionScopedController");
        mapping.setMappings(urlProperties);
        return mapping;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("locale");
        registry.addInterceptor(localeChangeInterceptor);
    }
}
