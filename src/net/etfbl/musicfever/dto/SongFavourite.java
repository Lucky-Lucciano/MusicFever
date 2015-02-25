package net.etfbl.musicfever.dto;

import java.io.Serializable;

public class SongFavourite implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Song song;
	private int favouriteCount;
	
	
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
	public int getFavouriteCount() {
		return favouriteCount;
	}
	public void setFavouriteCount(int favouriteCount) {
		this.favouriteCount = favouriteCount;
	}
	
	
}
