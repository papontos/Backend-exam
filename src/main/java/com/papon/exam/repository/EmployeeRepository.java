package com.papon.exam.repository;

import org.springframework.data.repository.CrudRepository;

import com.papon.exam.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
}
