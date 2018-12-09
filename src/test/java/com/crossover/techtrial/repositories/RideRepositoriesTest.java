/**
 * 
 */
package com.crossover.techtrial.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.crossover.techtrial.model.Ride;

/**
 * @author ashutosh
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class RideRepositoriesTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	RideRepository rideRepository;

	Long rideID;

	@Before
	public void insertRide() {
		Ride ride = new Ride();
		ride.setStartTime(LocalDateTime.now());
		ride.setEndTime(LocalDateTime.now());
		ride.setDistance(15L);
		ride = entityManager.persist(ride);
		rideID = ride.getId();
	}

	@Test
	public void testFindByID() {
		Optional<Ride> rideOpt = rideRepository.findById(rideID);
		assertTrue(rideOpt.isPresent());
		Ride ride = rideOpt.get();
		assertEquals(rideID, ride.getId());
		assertEquals(new Long(15), ride.getDistance());
	}

	@Test
	public void testNotFindByID() {
		Optional<Ride> rideOpt = rideRepository.findById(2L);
		assertFalse(rideOpt.isPresent());
	}
}
