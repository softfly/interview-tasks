package com.bskyb.internettv.parental_control_service;

import javax.validation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.IllegalArgumentException;

public class CanWatchMovieValidation {

    protected static ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    protected static Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    @NotNull(groups = Level1.class)
    @NotEmpty(groups = Level2.class)
    @Pattern(regexp = "U|PG|12|15|18|18", groups = Level3.class)
    String customerParentalControlLevel;

    @NotNull(groups = Level1.class)
    @NotEmpty(groups = Level2.class)
    @Size(max = 255, groups = Level3.class)
    String movieId;

    public static void validateCustomerParentalControlLevel(String customerParentalControlLevel)
            throws IllegalArgumentException {
        validateValue("customerParentalControlLevel", customerParentalControlLevel);
    }

    public static void validateMovieId(String movieId) throws IllegalArgumentException {
        validateValue("movieId", movieId);
    }

    protected static void validateValue(String propertyName, String value) throws IllegalArgumentException {
        for (ConstraintViolation<?> error : VALIDATOR.validateValue(CanWatchMovieValidation.class, propertyName, value,
                Order.class)) {
            throw new IllegalArgumentException(error.getPropertyPath() + " " + error.getMessage());
        }
    }

    @GroupSequence(value = {Level1.class, Level2.class, Level3.class})
    public interface Order {
    }

    public interface Level1 {
    }

    public interface Level2 {
    }

    public interface Level3 {
    }

}
