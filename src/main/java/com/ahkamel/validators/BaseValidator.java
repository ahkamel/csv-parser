package com.ahkamel.validators;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * 
 * Contains the Base form validation 
 * @author Ahmed Kamel
 *
 */
@Component
public class BaseValidator {

	public String errorToString(Errors errors) {
		return errors.getAllErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.joining(","));
		
	}
}
