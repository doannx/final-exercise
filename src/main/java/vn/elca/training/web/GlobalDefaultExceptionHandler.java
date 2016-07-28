package vn.elca.training.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
public class GlobalDefaultExceptionHandler extends DefaultHandlerExceptionResolver {
    private static final String DEFAULT_ERROR_VIEW = "myerrorpage";

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ResponseStatus responseStatusAnnotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        return buildModelAndViewErrorPage(request, response, ex,
                responseStatusAnnotation != null ? responseStatusAnnotation.value() : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping("*")
    public ModelAndView fallbackHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return buildModelAndViewErrorPage(request, response, null, HttpStatus.NOT_FOUND);
    }

    private ModelAndView buildModelAndViewErrorPage(HttpServletRequest request, HttpServletResponse response,
            Exception ex, HttpStatus httpStatus) {

        ModelAndView mav = new ModelAndView();
        if (ex != null) {
            mav.addObject("errorCode", ex.getMessage());
        }
        mav.addObject("errorURL", request.getRequestURL().toString());
        mav.addObject("errorStatus", httpStatus.value());
        mav.setViewName(this.DEFAULT_ERROR_VIEW);
        return mav;
    }
}
