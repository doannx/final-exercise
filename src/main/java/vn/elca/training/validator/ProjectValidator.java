package vn.elca.training.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.elca.training.model.ProjectVO;

@Component
public class ProjectValidator implements Validator {
    public boolean supports(Class clazz) {
        return ProjectVO.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "error.number", "");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name", "");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer", "error.customer", "");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "group", "error.group", "");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "error.status", "");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "finishingDate", "error.finishingDate", "");
        ProjectVO p = (ProjectVO) target;
        if ("-1".equals(p.getGroup())) {
            errors.rejectValue("group", "error.group");
        }
        if (p.getEndDate() != null && p.getFinishingDate() != null) {
            if (p.getEndDate().before(p.getFinishingDate())) {
                errors.rejectValue("endDate", "error.endDate");
            }
        }
    }
}
