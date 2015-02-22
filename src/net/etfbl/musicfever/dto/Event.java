package net.etfbl.musicfever.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event {
	private int id;
	private int creatorId;
	private Date startTime;
	private String name;
	private String location;
	private Date creationTime;
	private boolean approved;
	
	public Event() {
		
	}
	
	public Event(int id, int cid, Date startTime, String name, String location,
			Date creationTime, boolean approved) {
		super();
		this.id = id;
		this.creatorId = cid;
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
		/*DateFormat df = new SimpleDateFormat("d MMM yyyy HH:mm:ss", Locale.ENGLISH);
		String reportDate = df.format(startTime);
		DateFormat format = new SimpleDateFormat("d MMM yyyy HH:mm:ss", Locale.ENGLISH);
		try {
			Date date = format.parse(reportDate);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
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
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	
	
}
