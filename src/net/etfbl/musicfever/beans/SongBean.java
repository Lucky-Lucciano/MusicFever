package net.etfbl.musicfever.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.etfbl.musicfever.dto.Song;

@ManagedBean
@SessionScoped
public class SongBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Song song = new Song();
	private Song songDelete = new Song();
	private Song songAdd = new Song();
	
	// Korsiti postojeci song koji korz formu ima updatovana polja(ID ostaje isti) i porsljeduje se dalje
	public void updateSong() {
		
	}
	
	// Samo se setuje active na false, zbog statistike
	public void deleteSong() {
		
	}
	
	public void addSong() {
		
	}
	
	public void addSongToFavourite() {
		
	}

}
