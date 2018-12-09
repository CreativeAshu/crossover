/**
 * 
 */
package com.crossover.techtrial.controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.service.PersonService;

/**
 * @author kshah
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

	MockMvc mockMvc;

	@Mock
	private PersonController personController;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	PersonRepository personRepository;

	Long id;

	@Before
	public void savePerson() {
		Person person = new Person();
		person.setEmail("ashutosh@gmail.com");
		personRepository.save(person);
		id = person.getId();
	}

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
	}

	@Test
	public void testPanelShouldBeRegistered() throws Exception {
		HttpEntity<Object> person = getHttpEntity("{\"name\": \"test 1\", \"email\": \"test10000000000001@gmail.com\","
				+ " \"registrationNumber\": \"41DCT\",\"registrationDate\":\"2018-08-08T12:12:12\" }");
		ResponseEntity<Person> response = template.postForEntity("/api/person", person, Person.class);
		// Delete this user
		personRepository.deleteById(response.getBody().getId());
		Assert.assertEquals("test 1", response.getBody().getName());
		Assert.assertEquals(200, response.getStatusCode().value());
	}

	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}

	@Test
	public void testGetPersonByID() {
		ResponseEntity<Person> response = template.getForEntity("/api/person/" + id, Person.class);
		assertEquals("ashutosh@gmail.com", response.getBody().getEmail());
	}

	@Test
	public void testGetAll() {
		ResponseEntity<List> response = template.getForEntity("/api/person/", List.class);
		assertNotEquals(0, response.getBody().size());
	}

	@TestConfiguration
	class MockInjectionConfiguration {

		@Bean
		public PersonService service() {
			return Mockito.mock(PersonService.class);
		}
	}
}
