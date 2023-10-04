package com.eipulse.teamproject.repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eipulse.teamproject.meetingroom.Reservations;

public interface ReservationsRepository extends JpaRepository<Reservations, Integer> {

	@Query(value = "SELECT * FROM reservations WHERE room_id = (select id from meeting_room WHERE name = ?1)", nativeQuery = true)
	List<Reservations> findByName(String name);

	@Query(value = "SELECT reserv FROM reservations as reserv WHERE reserv.dateEnd >=:date ORDER BY createdAt desc", nativeQuery = true)
	LinkedList<Reservations> findAllByDate(Date date);
}
