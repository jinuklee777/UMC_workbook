package umc.spring.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import umc.spring.validation.annotation.CheckPage;

public class PageRangeValidator implements ConstraintValidator<CheckPage, Long> {
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return false;
    }
}
