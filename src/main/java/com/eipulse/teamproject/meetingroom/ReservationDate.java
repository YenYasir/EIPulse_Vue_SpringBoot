package com.eipulse.teamproject.meetingroom;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ReservationDate {

	private Date dateFrom;
	private Date dateTo;

	public ReservationDate() {

	}

	public ReservationDate(Date dateFrom, Date dateTo) {

		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

}
