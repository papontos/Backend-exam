package com.papon.exam.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import com.papon.exam.dto.EmployeeDto;
import com.papon.exam.entity.Employee;

@SpringBootTest
public class EmployeeMapperTest {
	@Test
	public void testEmployeeMapper() {
		ExployeeMapper mapper = Mappers.getMapper(ExployeeMapper.class);
		
		Long empId = 99L;
		String empFName = "FName";
		String empLName = "LName";
		
		Employee employee = new Employee();
		employee.setId(empId);
		employee.setFirstName(empFName);
		employee.setLastName(empLName);
		EmployeeDto dto = mapper.employeeToEmployeeDto(employee);
		
		
		assertNotNull(dto);
		assertEquals(empId,dto.getId());
		assertEquals(empFName,dto.getFirstName());
		assertEquals(empLName,dto.getLastName());
		
	}
	
	@Test
	public void testEmployeeMapperWithNullEmployee() {
		ExployeeMapper mapper = Mappers.getMapper(ExployeeMapper.class);
		
		EmployeeDto dto = mapper.employeeToEmployeeDto(null);
		
		assertNull(dto);
		
	}
}
