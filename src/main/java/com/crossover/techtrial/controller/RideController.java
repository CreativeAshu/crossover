/**
 * 
 */
package com.crossover.techtrial.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.techtrial.dto.RideDTO;
import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.dto.TopRidesDTO;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.service.PersonService;
import com.crossover.techtrial.service.RideService;

/**
 * RideController for Ride related APIs.
 * 
 * @author crossover
 *
 */
@RestController
public class RideController {

	@Autowired
	RideService rideService;

	@Autowired
	PersonService personService;

	@PostMapping(path = "/api/ride")
	public ResponseEntity<Ride> createNewRide(@RequestBody RideDTO rideDTO) {

		if (rideDTO == null)
			return ResponseEntity.badRequest().build();

		// Check driver / rider existing or not
		if (personService.findById(rideDTO.getDriver().getId()) == null
				&& personService.findById(rideDTO.getRider().getId()) == null)
			return ResponseEntity.badRequest().build();

		if (rideDTO.getEndTime().isAfter(rideDTO.getStartTime()))
			return ResponseEntity.badRequest().build();

		Ride ride = new Ride();
		ride.setId(rideDTO.getId());
		ride.setDriver(rideDTO.getDriver());
		ride.setRider(rideDTO.getRider());
		ride.setDistance(rideDTO.getDistance());
		ride.setStartTime(rideDTO.getStartTime());
		ride.setEndTime(rideDTO.getEndTime());
		return ResponseEntity.ok(rideService.save(ride));
	}

	@GetMapping(path = "/api/ride/{rideId}")
	public ResponseEntity<RideDTO> getRideById(@PathVariable(name = "rideId", required = true) Long rideId) {
		Ride ride = rideService.findById(rideId);
		if (ride != null) {
			RideDTO rideDTO = new RideDTO();
			rideDTO.setId(ride.getId());
			rideDTO.setStartTime(ride.getStartTime());
			rideDTO.setEndTime(ride.getEndTime());
			rideDTO.setDriver(ride.getDriver());
			rideDTO.setRider(ride.getRider());
			rideDTO.setDistance(ride.getDistance());
			return ResponseEntity.ok(rideDTO);
		}

		return ResponseEntity.notFound().build();
	}

	/**
	 * This API returns the top 5 drivers with their email,name, total minutes,
	 * maximum ride duration in minutes. Only rides that starts and ends within the
	 * mentioned durations should be counted. Any rides where either start or
	 * endtime is outside the search, should not be considered.
	 * 
	 * DONT CHANGE METHOD SIGNATURE AND RETURN TYPES
	 * 
	 * @return
	 */
	@GetMapping(path = "/api/top-rides")
	public ResponseEntity<List<TopDriverDTO>> getTopDriver(@RequestParam(value = "max", defaultValue = "5") Long count,
			@RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startTime,
			@RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endTime) {
		List<TopDriverDTO> topDrivers = new ArrayList<TopDriverDTO>();

		Collection<TopRidesDTO> rides = rideService.findRidesBetweenTime(startTime, endTime, count);

		for (TopRidesDTO ride : rides) {

			Person driver = personService.findById(ride.getDriverId());
			topDrivers.add(new TopDriverDTO(driver.getName(), driver.getEmail(), ride.getTotalRideDurationInMinutes(),
					ride.getMaxRideDurationInMinutes(), ride.getAverageDistance()));
		}
		return ResponseEntity.ok(topDrivers);
	}

}
