package net.etfbl.musicfever.dto;

public class Favourite {
	private int songId;
	private int userId;
	
	public Favourite() {
		
	}
	
	public Favourite(int songId, int userId) {
		super();
		this.songId = songId;
		this.userId = userId;
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
}
