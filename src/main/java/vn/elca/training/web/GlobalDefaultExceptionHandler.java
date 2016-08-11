package vn.elca.training.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
public class GlobalDefaultExceptionHandler extends DefaultHandlerExceptionResolver {
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String errorCode = UUID.randomUUID().toString();
        logger.error("Technical error occurs " + errorCode, ex);
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorCode", errorCode);
        mav.setViewName("myerrorpage");
        return mav;
    }
}
