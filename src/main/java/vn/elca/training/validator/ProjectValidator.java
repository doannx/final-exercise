package vn.elca.training.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.elca.training.model.ProjectVO;
import vn.elca.training.util.Constants;

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "error.startDate", "");
        ProjectVO p = (ProjectVO) target;
        if (Constants.NOT_SELECTED_YET.equals(p.getGroup())) {
            errors.rejectValue("group", "error.group");
        }
        if (p.getEndDate() != null && p.getStartDate() != null) {
            if (p.getEndDate().before(p.getStartDate())) {
                errors.rejectValue("endDate", "error.endDate");
            }
        }
    }
}
