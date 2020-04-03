package com.zopa.borrowercalc.commons.validators;

import java.io.File;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileExistsValidator implements ConstraintValidator<FileExists, File> {

	@Override
	public boolean isValid(File f, ConstraintValidatorContext context) {
		if (f == null) {
			return true;
		}
		if (f.exists()) {
			return true;
		}
		return false;
	}

}
