package com.eipulse.teamproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.meetingroom.MeetingRoom;
import com.eipulse.teamproject.meetingroom.ReservResonse;
import com.eipulse.teamproject.meetingroom.Reservations;
import com.eipulse.teamproject.service.MeetingRoomService;
import com.eipulse.teamproject.service.ReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationsController {
	@Autowired
	private ReservationService reservationServiceImp;

	@Autowired
	private MeetingRoomService meetingRoomServiceImp;

	@GetMapping("/get")
	public List<Reservations> getAllReservations() {
		List<Reservations> rooms = reservationServiceImp.getReservations();
		rooms.forEach(reservations -> {
			MeetingRoom byId = meetingRoomServiceImp.findById(reservations.getRoomId());
			reservations.setName(byId.getName());
			reservations.setCapacity(byId.getCapacity());
		});
		return rooms;

	}

	@GetMapping(produces = "application/json")
	public List<Reservations> getFutureReservations() {
		List<Reservations> rooms = reservationServiceImp.getFutureBookings();
		rooms.forEach(reservations -> {
			MeetingRoom byId = meetingRoomServiceImp.findById(reservations.getRoomId());
			reservations.setName(byId.getName());
			reservations.setCapacity(byId.getCapacity());
		});
		return rooms;

	}

	@GetMapping("/get/{name}")
	public List<Reservations> getAllReservationsByName(@PathVariable("name") String name) {
		List<Reservations> rooms = reservationServiceImp.findByName(name);
		return rooms;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Reservations> getUserById(@PathVariable("id") Integer id) {
		System.out.println("Fetching Reservation with id " + id);
		Reservations reservation = reservationServiceImp.findById(id);
		if (reservation == null) {
			return new ResponseEntity<Reservations>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Reservations>(reservation, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
		Reservations reservation = reservationServiceImp.findById(id);
		if (reservation == null) {
			return new ResponseEntity<String>("No such Reservation", HttpStatus.NOT_FOUND);
		}
		reservationServiceImp.deleteReservationsById(id);
		return new ResponseEntity<String>("Reservation Deleted", HttpStatus.NO_CONTENT);
	}

	@PostMapping("/book")
	public ResponseEntity<ReservResonse> createReservation(@RequestBody Reservations reservation) {
		System.out.println("Creating Reservation " + reservation);
		Reservations reserv = reservationServiceImp.createReservation(reservation);
		if (reserv != null) {
			return new ResponseEntity<>(new ReservResonse("Booking Created Successfully", HttpStatus.CREATED.value()),
					HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(new ReservResonse("Failed to Book", HttpStatus.NO_CONTENT.value()),
					HttpStatus.NO_CONTENT);
	}
}
