package com.papon.exam.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.papon.exam.dto.EmployeeDto;
import com.papon.exam.service.EmployeeService;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeControllerTest {

	@Autowired
	private EmployeeController employeeController;

	@MockBean(name = "employeeService")
	private EmployeeService employeeService;

	@Test
	public void testGetAll() {
		List<EmployeeDto> mockData = new ArrayList<EmployeeDto>();

		EmployeeDto data1 = new EmployeeDto();
		data1.setId(1L);
		data1.setFirstName("firstName");
		data1.setLastName("lastName");
		mockData.add(data1);

		given(employeeService.getAll()).willReturn(mockData);

		ResponseEntity<List<EmployeeDto>> response = employeeController.getEmployees();

		verify(employeeService, times(1)).getAll();
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetAllException() {

		given(employeeService.getAll()).willThrow(new RuntimeException());

		ResponseEntity<List<EmployeeDto>> response = employeeController.getEmployees();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}

	@Test
	public void testFindById() {

		EmployeeDto data1 = new EmployeeDto();
		data1.setId(1L);
		data1.setFirstName("firstName");
		data1.setLastName("lastName");

		given(employeeService.findById(1L)).willReturn(data1);

		ResponseEntity<EmployeeDto> response = employeeController.getEmployees(data1.getId());

		verify(employeeService, times(1)).findById(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testFindByIdError() {

		given(employeeService.findById(1L)).willThrow(new RuntimeException());

		ResponseEntity<EmployeeDto> response = employeeController.getEmployees(1L);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}

	@Test
	public void testFindByIdNotFound() {

		given(employeeService.findById(1L)).willThrow(new EntityNotFoundException());

		ResponseEntity<EmployeeDto> response = employeeController.getEmployees(1L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

	}

	@Test
	public void testDeleteEmployee() {

		ResponseEntity<Void> response = employeeController.deleteEmployee(1L);

		verify(employeeService, times(1)).deleteById(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testDeleteEmployeeError() {

		Mockito.doThrow(new RuntimeException()).when(employeeService).deleteById(1L);

		ResponseEntity<Void> response = employeeController.deleteEmployee(1L);

		verify(employeeService, times(1)).deleteById(1L);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testUpdate() {

		EmployeeDto data1 = new EmployeeDto();
		data1.setId(1L);
		data1.setFirstName("firstName");
		data1.setLastName("lastName");

		given(employeeService.updateEmployee(data1)).willReturn(data1);

		ResponseEntity<EmployeeDto> response = employeeController.updateEmployee(1L, data1);

		verify(employeeService, times(1)).updateEmployee(data1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateError() {
		EmployeeDto data1 = new EmployeeDto();
		data1.setId(1L);
		data1.setFirstName("firstName");
		data1.setLastName("lastName");

		given(employeeService.updateEmployee(data1)).willThrow(new RuntimeException());

		ResponseEntity<EmployeeDto> response = employeeController.updateEmployee(1L, data1);

		verify(employeeService, times(1)).updateEmployee(data1);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}

	@Test
	public void testUpdateNotFound() {
		EmployeeDto data1 = new EmployeeDto();
		data1.setId(1L);
		data1.setFirstName("firstName");
		data1.setLastName("lastName");

		given(employeeService.updateEmployee(data1)).willThrow(new EntityNotFoundException());

		ResponseEntity<EmployeeDto> response = employeeController.updateEmployee(1L, data1);

		verify(employeeService, times(1)).updateEmployee(data1);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

	}

	@Test
	public void testInsert() {

		EmployeeDto data1 = new EmployeeDto();
		data1.setFirstName("firstName");
		data1.setLastName("lastName");

		given(employeeService.insertEmployee(data1)).willReturn(data1);

		ResponseEntity<EmployeeDto> response = employeeController.insertEmployee(data1);

		verify(employeeService, times(1)).insertEmployee(data1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testInsertError() {
		EmployeeDto data1 = new EmployeeDto();
		data1.setFirstName("firstName");
		data1.setLastName("lastName");

		given(employeeService.insertEmployee(data1)).willThrow(new RuntimeException());

		ResponseEntity<EmployeeDto> response = employeeController.insertEmployee(data1);

		verify(employeeService, times(1)).insertEmployee(data1);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}

}
