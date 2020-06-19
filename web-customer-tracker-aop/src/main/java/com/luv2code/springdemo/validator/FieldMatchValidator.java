package com.luv2code.springdemo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class FieldMatchValidator
		implements
			ConstraintValidator<FieldMatch, Object> {
	private String firstFieldName;
	private String secondFieldName;
	private String message;

	@Override
	public void initialize(FieldMatch constraintAnnotation) {
		// get Info from annotation
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
		message = constraintAnnotation.message();

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid = true;
		try {
			final Object firstObj = BeanUtils.getProperty(value,
					firstFieldName);// firstFieldName hold the name of the field
									// not its value, firstObj will hold the
									// value
			final Object secondObj = BeanUtils.getProperty(value,
					secondFieldName);

			valid = firstObj == null && secondObj == null
					|| firstObj != null && firstObj.equals(secondObj);
		} catch (final Exception ignore) {
			// ignore
		}

		if (!valid) {
			context.buildConstraintViolationWithTemplate(message)
					.addPropertyNode(firstFieldName).addConstraintViolation()
					.disableDefaultConstraintViolation();
		}

		return valid;
	}

}
