package net.etfbl.musicfever.dto;

public class Playlist {
	private int id;
	private int userId;
	private String title;
	private String description;
	
	public Playlist() {
		
	}

	public Playlist(int id, int userId, String title, String description) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
