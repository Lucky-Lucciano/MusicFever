package net.etfbl.musicfever.dto;

public class Rating {
	private int songId;
	private int userId;
	private int score;
	
	public Rating() {
		
	}
	
	public Rating(int songId, int userId, int score) {
		super();
		this.songId = songId;
		this.userId = userId;
		this.score = score;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}	
}
