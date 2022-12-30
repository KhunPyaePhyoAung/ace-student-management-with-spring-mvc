package me.khun.studentmanagement.model.service.validator;

import me.khun.studentmanagement.model.service.exception.InvalidFieldsException;

public interface Validator<T> {
	InvalidFieldsException validate(T t);
}
