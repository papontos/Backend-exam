package com.papon.exam.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papon.exam.dto.EmployeeDto;
import com.papon.exam.entity.Employee;
import com.papon.exam.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	public List<EmployeeDto> getAll() {
		return StreamSupport.stream(employeeRepository.findAll().spliterator(), false).map((Employee rs) -> {
			return constuctEmployeeVo(rs);
		}).collect(Collectors.toList());
	}

	public EmployeeDto findById(final Long id) {
		Employee rs = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return constuctEmployeeVo(rs);
	}

	public void deleteById(final Long id) {
		employeeRepository.deleteById(id);
	}

	public void updateEmployee(final EmployeeDto vo) {
		Employee rs = employeeRepository.findById(vo.getEmployeeId()).orElseThrow(() -> new EntityNotFoundException());
		rs.setFirstName(vo.getFirstName());
		rs.setLastName(vo.getLastName());
		employeeRepository.save(rs);
	}
	
	public void insertEmployee(final EmployeeDto vo) {
		Employee rs = new Employee(vo.getFirstName(),vo.getLastName());
		employeeRepository.save(rs);
	}

	private static EmployeeDto constuctEmployeeVo(final Employee rs) {
		EmployeeDto vo = new EmployeeDto();
		vo.setEmployeeId(rs.getId());
		vo.setFirstName(rs.getFirstName());
		vo.setLastName(rs.getLastName());
		return vo;
	}

}
