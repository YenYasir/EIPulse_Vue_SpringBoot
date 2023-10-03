package com.eipulse.teamproject.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.teamproject.meetingroom.MeetingRoom;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {
	@Query(value = "select * from meeting_room as room  where room.id not in "
			+ "( select reserv.room_id from reservation as reserv where (date_begin >= ?1 and date_begin < ?2) "
			+ "or (date_end >= ?1 and date_end < ?2) " + ")", nativeQuery = true)
	List<MeetingRoom> findMeetingRoomAvailable(Date date_begin, Date date_end);

	@Query(value = "select * from (select * from meeting_room as room where room.id not in "
			+ "(select reserv.room_id from reservation as reserv where (date_begin >= ?1 and date_begin < ?2) "
			+ "or (date_end >= ?1 and date_end < ?2))) as room where room.id = ?3", nativeQuery = true)
	MeetingRoom checkAvailability(Date date_begin, Date date_end, Integer id);
}
