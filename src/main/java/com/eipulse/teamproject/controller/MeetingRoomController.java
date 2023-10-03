package com.eipulse.teamproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.eipulse.teamproject.meetingroom.MeetingRoom;
import com.eipulse.teamproject.meetingroom.ReservationDate;
import com.eipulse.teamproject.service.MeetingRoomService;

@RestController
@RequestMapping("/meetingroom")
public class MeetingRoomController {
	@Autowired
	private MeetingRoomService meetingRoomServiceImp;

	@GetMapping("/get")
	public List<MeetingRoom> getAllMeetingRoom() {
		List<MeetingRoom> rooms = meetingRoomServiceImp.getMeetingRoom();
		return rooms;
	}

	@GetMapping("/{id}")
	public ResponseEntity<MeetingRoom> getUserById(@PathVariable("id") Integer id) {
		System.out.println("Fetching Meeting Room with id " + id);
		MeetingRoom meetingroom = meetingRoomServiceImp.findById(id);
		if (meetingroom == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(meetingroom, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<String> createUser(@RequestBody MeetingRoom meetingroom, UriComponentsBuilder ucBuilder) {
		System.out.println("Creating Meeting Room " + meetingroom.getName());
		meetingRoomServiceImp.createMeetingRoom(meetingroom);
		return new ResponseEntity<>("Meeting Room Created Successfully", HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
		MeetingRoom room = meetingRoomServiceImp.findById(id);
		if (room == null) {
			return new ResponseEntity<>("No such meeting room", HttpStatus.NOT_FOUND);
		}
		meetingRoomServiceImp.deleteMeetingRoomById(id);
		return new ResponseEntity<>("Meeting Room Deleted", HttpStatus.NO_CONTENT);
	}

	@PutMapping("/update")
	public ResponseEntity<MeetingRoom> updateUserPartially(@RequestBody MeetingRoom room) {
		MeetingRoom meetingroom = meetingRoomServiceImp.findById(room.getId());
		if (meetingroom == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		MeetingRoom r1 = meetingRoomServiceImp.update(room, room.getId());
		return new ResponseEntity<>(r1, HttpStatus.OK);
	}

	@PostMapping("/date")
	public List<MeetingRoom> getAllReservationsByDate(@RequestBody ReservationDate dates) {
		System.out.println("Dates to enquire from: " + dates.getDateFrom() + " to: " + dates.getDateTo());
		List<MeetingRoom> rooms = meetingRoomServiceImp.findMeetingRoomAvailable(dates.getDateFrom(),
				dates.getDateTo());
		return rooms;
	}
}
