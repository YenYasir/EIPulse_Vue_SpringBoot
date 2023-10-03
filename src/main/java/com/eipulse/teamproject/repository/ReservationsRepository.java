package com.eipulse.teamproject.repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eipulse.teamproject.meetingroom.Reservations;

public interface ReservationsRepository extends JpaRepository<Reservations, Integer> {

	@Query(value = "SELECT * FROM reservation WHERE room_id = (select id from meeting_room where name = ?1)", nativeQuery = true)
	List<Reservations> findByName(String name);

	@Query("select reserv from reservation as reserv where reserv.dateEnd >=:date order by createdAt desc")
	LinkedList<Reservations> findAllByDate(Date date);
}
