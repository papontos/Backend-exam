package com.papon.exam.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.papon.exam.mapper.ExployeeMapper;
import com.papon.exam.dto.EmployeeDto;
import com.papon.exam.entity.Employee;
import com.papon.exam.repository.EmployeeRepository;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
public class EmployeeServiceTest {

	@MockBean(name = "employeeRepository")
	private EmployeeRepository employeeRepositoryMock;

	@MockBean(name = "employeeMapper")
	private ExployeeMapper employeeMapperMock;

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void testFindAllNoData() {

		List<Employee> mockData = new ArrayList<Employee>();
		given(employeeRepositoryMock.findAll()).willReturn(mockData);

		List<EmployeeDto> actualResponse = employeeService.getAll();

		assertEquals(0, actualResponse.size(), "Response size should be zero");

	}

	@Test
	public void testFindAllWithData() {

		List<Employee> mockDatas = new ArrayList<Employee>();
		Employee mockData1 = new Employee();
		mockData1.setId(1l);
		mockData1.setFirstName("firstName1");
		mockData1.setLastName("lastName1");
		mockDatas.add(mockData1);

		Employee mockData2 = new Employee();
		mockData2.setId(2l);
		mockData2.setFirstName("firstName2");
		mockData2.setLastName("lastName2");
		mockDatas.add(mockData2);

		given(employeeRepositoryMock.findAll()).willReturn(mockDatas);

		List<EmployeeDto> actualResponse = employeeService.getAll();

		verify(employeeMapperMock, times(1)).employeeToEmployeeDto(mockData1);

		assertEquals(2, actualResponse.size(), "Response size should be 2");
	}

	@Test
	public void testFindByIdNotFound() {
		Optional<Employee> mockResp = Optional.empty();

		given(employeeRepositoryMock.findById(1L)).willReturn(mockResp);

		assertThrows(EntityNotFoundException.class, () -> {
			employeeService.findById(1L);
		});
	}

	@Test
	public void testFindById() {

		Employee mockData = new Employee();
		mockData.setId(1l);
		mockData.setFirstName("firstName");
		mockData.setLastName("lastName");
		EmployeeDto mockDto = new EmployeeDto();
		mockDto.setId(1l);
		mockDto.setFirstName("firstName");
		mockDto.setLastName("lastName");

		Optional<Employee> mockResp = Optional.of(mockData);
		given(employeeRepositoryMock.findById(1L)).willReturn(mockResp);

		given(employeeMapperMock.employeeToEmployeeDto(mockData)).willReturn(mockDto);

		employeeService.findById(1l);

		verify(employeeRepositoryMock, times(1)).findById(1l);
		verify(employeeMapperMock, times(1)).employeeToEmployeeDto(mockData);

	}

	@Test
	public void testDelete() {
		employeeService.deleteById(1l);
		verify(employeeRepositoryMock, times(1)).deleteById(1l);
	}

	@Test
	public void testUpdate() {
		Employee mockData = new Employee();
		mockData.setId(1l);
		mockData.setFirstName("firstName");
		mockData.setLastName("lastName");
		EmployeeDto mockDto = new EmployeeDto();
		mockDto.setId(1l);
		mockDto.setFirstName("firstName");
		mockDto.setLastName("lastName");

		Optional<Employee> mockResp = Optional.of(mockData);
		given(employeeRepositoryMock.findById(1L)).willReturn(mockResp);

		employeeService.updateEmployee(mockDto);
		
		verify(employeeRepositoryMock, times(1)).save(mockData);
	}

	@Test
	public void testUpdateNotFound() {
		EmployeeDto mockDto = new EmployeeDto();
		mockDto.setId(1l);
		mockDto.setFirstName("firstName");
		mockDto.setLastName("lastName");
		Optional<Employee> mockResp = Optional.empty();

		given(employeeRepositoryMock.findById(1L)).willReturn(mockResp);

		assertThrows(EntityNotFoundException.class, () -> {
			employeeService.updateEmployee(mockDto);
		});
	}

	@Test
	public void testInsert() {
		EmployeeDto mockDto = new EmployeeDto();
		mockDto.setId(null);
		mockDto.setFirstName("firstName");
		mockDto.setLastName("lastName");
		
		Employee mockData = new Employee();
		mockData.setId(null);
		mockData.setFirstName("firstName");
		mockData.setLastName("lastName");
		
		employeeService.insertEmployee(mockDto);
		verify(employeeRepositoryMock, times(1)).save(Mockito.any());
	}

}
