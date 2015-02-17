package net.etfbl.musicfever.dto;

import java.util.ArrayList;
import java.util.Date;

public class Song {
	private int id;
	private String artist;
	private String name;
	private boolean active;
	private int releaseDate;
	private int duration;
	private int fileType;
	private Date registrationDate;
	private String location;
	private String lyrics;
	private ArrayList<Genre> genres = new ArrayList<Genre>(); // nije u constr.
	
	public Song() {
		
	}
	
	
	public Song(int id, String artist, String name, boolean active, int fileType,
			int releaseDate, int duration, Date registrationDate,
			String location, String lyrics) {
		super();
		this.id = id;
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
	public int getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(int releaseDate) {
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
	
}
