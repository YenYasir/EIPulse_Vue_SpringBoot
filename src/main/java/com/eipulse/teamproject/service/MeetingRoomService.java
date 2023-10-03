package com.eipulse.teamproject.service;

import java.util.Date;
import java.util.List;

import com.eipulse.teamproject.meetingroom.MeetingRoom;

public interface MeetingRoomService {

	void createMeetingRoom(MeetingRoom meetingroom);

	List<MeetingRoom> getMeetingRoom();

	MeetingRoom findById(Integer id);

	MeetingRoom update(MeetingRoom meetingroom, Integer id);

	Boolean deleteMeetingRoomById(Integer id);

	List<MeetingRoom> findMeetingRoomAvailable(Date db, Date de);

}
