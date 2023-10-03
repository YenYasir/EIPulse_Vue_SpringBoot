package com.eipulse.teamproject.meetingroom;

import java.util.Date;

import com.eipulse.teamproject.entitys.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "reservations")
public class Reservations extends AuditEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservation_id")
	private Integer reservationId;

	@Column(name = "room_id")
	private Integer roomId;

	@Column(name = "purpose")
	private String purpose;

	@Column(name = "date_begin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateBegin;

	@Column(name = "date_end")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEnd;

	@Transient
	private String name;

	@Transient
	private Integer capacity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
	private Employee employee;

	public Reservations() {
	}

	public Reservations(Integer id, Integer roomId, String purpose, Date dateBegin, Date dateEnd) {
		super();
		this.reservationId = id;
		this.roomId = roomId;
		this.purpose = purpose;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
	}

	public Reservations(Integer roomId, String purpose, Date dateBegin, Date dateEnd) {
		super();
		this.roomId = roomId;
		this.purpose = purpose;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
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

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer id) {
		this.reservationId = id;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Override
	public String toString() {
		return "Reservations [id=" + reservationId + ", roomId=" + roomId + ", purpose=" + purpose + ", dateBegin="
				+ dateBegin + ", dateEnd=" + dateEnd + "]";
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
