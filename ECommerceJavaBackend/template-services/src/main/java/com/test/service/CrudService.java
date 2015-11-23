package com.test.service;

public interface CrudService <T> {
	
	T get(T entity);	
	T save(T entity); // throws ServiceException;
}
