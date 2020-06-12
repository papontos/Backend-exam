package com.papon.exam.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.papon.exam.dto.EmployeeDto;
import com.papon.exam.entity.Employee;

@Mapper(componentModel = "spring")
public interface ExployeeMapper {
	
	
	@Mapping(source = "id", target = "id")
	@Mapping(source = "firstName", target = "firstName")
	@Mapping(source = "lastName", target = "lastName")
	EmployeeDto employeeToEmployeeDto(Employee employee);

}
