/**
 * 
 */
package com.crossover.techtrial.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.service.PersonService;
import com.crossover.techtrial.service.PersonServiceImpl;

/**
 * @author ashutosh
 *
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PersonServiceTest {

	@Mock
	PersonRepository personRepository;

	@Autowired
	@InjectMocks
	PersonService personService = new PersonServiceImpl();

	@Test
	public void TestNotExsistReturnNull() {
		when(personRepository.findById(101L)).thenReturn(Optional.empty());
		assertNull(personService.findById(101l));
	}

	@Test
	public void TestExsistReturnPerson() {
		when(personRepository.findById(anyLong())).thenReturn(Optional.of(new Person()));
		Person person = personService.findById(3L);
		assertNotNull(person);
	}

	@Test
	public void testGetAllEmpty() {
		when(personRepository.findAll()).thenReturn(Collections.emptyList());
		assertEquals(0, personService.getAll().size());
	}

	@Test
	public void testGetAllHasElements() {
		List<Person> list = Arrays.asList(new Person(), new Person(), new Person());
		when(personRepository.findAll()).thenReturn(list);
		assertEquals(3, personService.getAll().size());
	}

	@Test
	public void testSavePerson() {
		Person person = new Person();
		person.setEmail("test@test.com");
		person.setName("Ashutosh");
		person.setRegistrationNumber("200");

		when(personRepository.save(person)).thenReturn(person);
		assertEquals(person, personService.save(person));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSavePersonWithoutEmail() {
		Person person = new Person();
		person.setName("Ashutosh");
		person.setRegistrationNumber("200");
		when(personRepository.save(person)).thenThrow(new ConstraintViolationException(null));
		personService.save(person);
	}

}
