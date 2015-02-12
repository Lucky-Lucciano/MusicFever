package net.etfbl.musicfever.dto;

import java.util.Date;

public class Event {
	private int id;
	private Date startTime;
	private String name;
	private String location;
	private String creationTime;
	private boolean approved;
	
	public Event() {
		
	}
	
	public Event(int id, Date startTime, String name, String location,
			String creationTime, boolean approved) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.name = name;
		this.location = location;
		this.creationTime = creationTime;
		this.approved = approved;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
}
