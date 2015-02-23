package net.etfbl.musicfever.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Song implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String artist;
	private String name;
	private boolean active;
	private Date releaseDate;
	private int duration;
	private int fileType;
	private Date registrationDate;
	private String location;
	private String lyrics;
	private int user_id;
	private ArrayList<Genre> genres = new ArrayList<Genre>();
	
	public Song() {
		
	}
	
	
	public Song(int id, int uid, String artist, String name, boolean active, int fileType,
			Date releaseDate, int duration, Date registrationDate,
			String location, String lyrics) {
		
		super();
		this.id = id;
		this.user_id = uid;
		this.fileType = fileType;
		this.artist = artist;
		this.name = name;
		this.active = active;
		this.releaseDate = releaseDate;
		this.duration = duration;
		this.registrationDate = registrationDate;
		this.location = location;
		this.lyrics = lyrics;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public int getFileType() {
		return fileType;
	}


	public void setFileType(int fileType) {
		this.fileType = fileType;
	}


	public ArrayList<Genre> getGenres() {
		return genres;
	}


	public void setGenres(ArrayList<Genre> genres) {
		this.genres = genres;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	@Override
	public String toString() {
		return "Song [id=" + id + ", artist=" + artist + ", name=" + name
				+ ", active=" + active + ", releaseDate=" + releaseDate
				+ ", duration=" + duration + ", fileType=" + fileType
				+ ", registrationDate=" + registrationDate + ", location="
				+ location + ", lyrics=" + lyrics + ", user_id=" + user_id
				+ ", genres=" + genres + "]";
	}
}
