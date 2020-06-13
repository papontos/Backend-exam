package com.papon.exam.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papon.exam.dto.EmployeeDto;
import com.papon.exam.entity.Employee;
import com.papon.exam.mapper.ExployeeMapper;
import com.papon.exam.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	ExployeeMapper employeeMapper;

	public List<EmployeeDto> getAll() {
		return StreamSupport.stream(employeeRepository.findAll().spliterator(), false).map((Employee employee) -> {
			return employeeMapper.employeeToEmployeeDto(employee);
		}).collect(Collectors.toList());
	}

	public EmployeeDto findById(final Long id) {
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return employeeMapper.employeeToEmployeeDto(employee);
	}

	public void deleteById(final Long id) {
		employeeRepository.deleteById(id);
	}

	public EmployeeDto updateEmployee(final EmployeeDto dto) {
		Employee employee = employeeRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException());
		employee.setFirstName(dto.getFirstName());
		employee.setLastName(dto.getLastName());
		employeeRepository.save(employee);
		return employeeMapper.employeeToEmployeeDto(employee);
	}
	
	public EmployeeDto insertEmployee(final EmployeeDto dto) {
		Employee employee = new Employee(dto.getFirstName(),dto.getLastName());
		employeeRepository.save(employee);
		return employeeMapper.employeeToEmployeeDto(employee);
	}

	
}
