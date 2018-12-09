/**
 * 
 */
package com.crossover.techtrial.repositories;

import com.crossover.techtrial.dto.TopRidesDTO;
import com.crossover.techtrial.model.Ride;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author crossover
 *
 */
@RestResource(exported = false)
public interface RideRepository extends CrudRepository<Ride, Long> {

	@Query(value = "SELECT ride.driver_id as DriverId, SUM(TIMESTAMPDIFF(MINUTE, ride.start_time, ride.end_time)) as TotalRideDurationInMinutes, MAX(TIMESTAMPDIFF(MINUTE, ride.start_time, ride.end_time)) as MaxRideDurationInMinutes, AVG(ride.distance) as AverageDistance FROM Ride ride WHERE ride.start_time >= :startTime AND ride.end_time <= :endTime GROUP BY ride.driver_id  Limit 0, :count", nativeQuery = true)
	Collection<TopRidesDTO> findTopRides(@Param("startTime") LocalDateTime startTime,
			@Param("endTime") LocalDateTime endTime, @Param("count") Long count);
}
