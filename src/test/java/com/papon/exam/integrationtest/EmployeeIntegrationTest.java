package com.papon.exam.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.papon.exam.ExamApplication;
import com.papon.exam.dto.EmployeeDto;

@SpringBootTest(classes = ExamApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String getUrl() {
		return "http://localhost:" + port + "/employees";
	}

	@Test
	@Order(1)
	public void testInsert() {
		EmployeeDto data = new EmployeeDto();
		data.setFirstName("firstName");
		data.setLastName("lastName");

		ResponseEntity<EmployeeDto> response = restTemplate.postForEntity(getUrl(), data, EmployeeDto.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody().getId());
		assertEquals("firstName", response.getBody().getFirstName());
		assertEquals("lastName", response.getBody().getLastName());

	}

	@Test
	@Order(2)
	public void testGetAll() {
		ResponseEntity<List> response = restTemplate.getForEntity(getUrl(), List.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	}

	@Test
	@Order(3)
	public void testGetById() {
		ResponseEntity<EmployeeDto> response = restTemplate.getForEntity(getUrl() + "/1", EmployeeDto.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody().getId());
		assertEquals("firstName", response.getBody().getFirstName());
		assertEquals("lastName", response.getBody().getLastName());
	}

	@Test
	@Order(4)
	public void testUpdate() {
		EmployeeDto data = new EmployeeDto();
		data.setFirstName("firstName2");
		data.setLastName("lastName2");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<EmployeeDto> response = restTemplate.exchange(getUrl() + "/1", HttpMethod.PUT,
				new HttpEntity<EmployeeDto>(data, headers), EmployeeDto.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody().getId());
		assertEquals("firstName2", response.getBody().getFirstName());
		assertEquals("lastName2", response.getBody().getLastName());

	}

	@Test
	@Order(5)
	public void testDeleteById() {
		ResponseEntity<Void> response = restTemplate.exchange(getUrl() + "/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(6)
	public void testGetAllExpectZeroResponse() {
		ResponseEntity<List> response = restTemplate.getForEntity(getUrl(), List.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, response.getBody().size());
	}

	@Test
	@Order(6)
	public void testGetByIdExpect404() {
		ResponseEntity<EmployeeDto> response = restTemplate.getForEntity(getUrl() + "/1", EmployeeDto.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

}
