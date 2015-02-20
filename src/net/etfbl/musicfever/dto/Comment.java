package net.etfbl.musicfever.dto;

import java.util.Date;

public class Comment {
	private int songId;
	private int userId;
	private String comment;
	private Date time;
	
	public Comment() {
		
	}
	
	public Comment(int songId, int userId, String comment, Date time) {
		super();
		this.songId = songId;
		this.userId = userId;
		this.comment = comment;
		this.time = time;
	}


	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
