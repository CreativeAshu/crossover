/**
 * 
 */
package com.crossover.techtrial.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;

/**
 * @author ashutosh
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoriesTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	PersonRepository personRepository;

	Long personID;

	@Before
	public void insertCompany() {
		Person person = new Person();
		person.setEmail("test@test.com");
		person.setName("Ashutosh");
		person.setRegistrationNumber("200");
		person = entityManager.persist(person);
		personID = person.getId();
	}

	@Test
	public void testFindByID() {
		Optional<Person> personOpt = personRepository.findById(personID);
		assertTrue(personOpt.isPresent());
		Person person = personOpt.get();
		assertEquals(person.getName(), "Ashutosh");
		assertEquals(person.getRegistrationNumber(), "200");
		assertEquals(person.getEmail(), "test@test.com");
	}

	@Test
	public void testNotFindByID() {
		Optional<Person> personOpt = personRepository.findById(2L);
		assertFalse(personOpt.isPresent());
	}
}
