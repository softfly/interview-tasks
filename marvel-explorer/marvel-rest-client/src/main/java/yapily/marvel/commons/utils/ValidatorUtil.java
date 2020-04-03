package yapily.marvel.commons.utils;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

@Component
public class ValidatorUtil {

    @Autowired
    private Validator validator;

    public boolean valid(Object o) {
        Objects.requireNonNull(validator);
        DataBinder binder = new DataBinder(o);
        binder.setValidator(validator);
        binder.validate();
        BindingResult results = binder.getBindingResult();

        for (ObjectError e : results.getAllErrors()) {
            if (e instanceof FieldError) {
                FieldError fe = (FieldError) e;
                throw new IllegalArgumentException(new StringBuilder(fe.getField()).append(" ").append(e.getDefaultMessage()).toString());
            } else {
                throw new IllegalArgumentException(e.getDefaultMessage());
            }
        }

        return !results.hasErrors();
    }

}
