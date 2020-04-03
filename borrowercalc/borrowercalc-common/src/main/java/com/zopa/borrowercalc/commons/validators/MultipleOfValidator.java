package com.zopa.borrowercalc.commons.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipleOfValidator implements ConstraintValidator<MultipleOf, Number> {

	protected int multipleOf;

	@Override
	public void initialize(MultipleOf multipleOf) {
		this.multipleOf = multipleOf.value();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		if (value.longValue() % multipleOf == 0) {
			return true;
		}
		return false;
	}

}
