/**
 * 
 */
package com.crossover.techtrial.service;

import java.time.LocalDateTime;
import java.util.Collection;

import com.crossover.techtrial.dto.TopRidesDTO;
import com.crossover.techtrial.model.Ride;

/**
 * RideService for rides.
 * 
 * @author crossover
 *
 */
public interface RideService {

	public Ride save(Ride ride);

	public Ride findById(Long rideId);

	public Collection<TopRidesDTO> findRidesBetweenTime(LocalDateTime startTime, LocalDateTime endTime, Long count);

}
