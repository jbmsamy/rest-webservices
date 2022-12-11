package com.in28minutes.rest.webservices.exception;

import java.time.LocalDateTime;

public class ErrorDetails {

	private LocalDateTime date;
	String messsage, description;
	public ErrorDetails(LocalDateTime date, String messsage, String description) {
		super();
		this.date = date;
		this.messsage = messsage;
		this.description = description;
	}
	
	
	@Override
	public String toString() {
		return "ErrorDetails [date=" + date + ", messsage=" + messsage + ", description=" + description + "]";
	}


	public LocalDateTime getDate() {
		return date;
	}


	public void setDate(LocalDateTime date) {
		this.date = date;
	}


	public String getMesssage() {
		return messsage;
	}


	public void setMesssage(String messsage) {
		this.messsage = messsage;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
