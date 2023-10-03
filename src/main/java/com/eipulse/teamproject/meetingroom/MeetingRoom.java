package com.eipulse.teamproject.meetingroom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MeetingRoom")
public class MeetingRoom extends AuditEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "capacity")
	private Integer capacity;

	@Column(name = "location")
	private String location;

	public MeetingRoom() {
	}

	public MeetingRoom(Integer id, String name, Integer capacity, String location) {
		super();
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.location = location;
	}

	public MeetingRoom(String name, Integer capacity, String location) {
		super();
		this.name = name;
		this.capacity = capacity;
		this.location = location;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
