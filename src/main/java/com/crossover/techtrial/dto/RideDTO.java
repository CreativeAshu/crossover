package com.crossover.techtrial.dto;

import java.time.LocalDateTime;

import com.crossover.techtrial.model.Person;

public class RideDTO {

	private Long id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Long distance;
	private Person driver;
	private Person rider;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public Person getDriver() {
		return driver;
	}

	public void setDriver(Person driver) {
		this.driver = driver;
	}

	public Person getRider() {
		return rider;
	}

	public void setRider(Person rider) {
		this.rider = rider;
	}

}
