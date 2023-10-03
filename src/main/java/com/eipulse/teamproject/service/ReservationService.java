package com.eipulse.teamproject.service;

import java.util.LinkedList;
import java.util.List;

import com.eipulse.teamproject.meetingroom.Reservations;

public interface ReservationService {

	LinkedList<Reservations> getFutureBookings();

	List<Reservations> getReservations();

	Reservations findById(Integer id);

	Boolean deleteReservationsById(Integer id);

	List<Reservations> findByName(String name);

	Reservations createReservation(Reservations reservation);

}
