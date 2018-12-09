/**
 * 
 */
package com.crossover.techtrial.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.RideRepository;

/**
 * @author ashutosh
 *
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RideServiceTest {

	@Mock
	RideRepository rideRepository;

	@Autowired
	@InjectMocks
	RideService rideService = new RideServiceImpl();

	@Test
	public void TestNotExsistReturnNull() {
		when(rideRepository.findById(101L)).thenReturn(Optional.empty());
		assertNull(rideService.findById(101l));
	}

	@Test
	public void TestExistReturnRide() {
		when(rideRepository.findById(anyLong())).thenReturn(Optional.of(new Ride()));
		Ride ride = rideService.findById(3L);
		assertNotNull(ride);
	}

	/*@Test
	public void testGetAllRideBetweenDates() {
		Collection<RideDTO> list = Arrays.asList(new RideDTO(), new RideDTO(), new RideDTO());
		when(rideRepository.findTopRides(LocalDateTime.now(), LocalDateTime.now(), 5L)).thenReturn(list);
		assertEquals(5, rideService.findRidesBetweenTime(LocalDateTime.now(), LocalDateTime.now(), 5L).size());
	}*/

	@Test
	public void testSaveRide() {
		Ride ride = new Ride();
		ride.setStartTime(LocalDateTime.now());
		ride.setEndTime(LocalDateTime.now());
		ride.setDistance(100L);
		ride.setDriver(new Person());
		ride.setRider(new Person());

		when(rideRepository.save(ride)).thenReturn(ride);
		assertEquals(ride, rideService.save(ride));
	}

}
