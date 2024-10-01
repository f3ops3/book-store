package springweb.courseproject.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AuthorValidator implements ConstraintValidator<Author, String> {
    private static final String VALIDATION_PATTERN
            = "^([a-zA-Z]{2,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)";

    @Override
    public boolean isValid(String author, ConstraintValidatorContext constraintValidatorContext) {
        return author != null && Pattern.compile(VALIDATION_PATTERN).matcher(author).matches();
    }
}
