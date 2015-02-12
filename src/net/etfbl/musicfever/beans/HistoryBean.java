package net.etfbl.musicfever.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import net.etfbl.musicfever.dto.Song;
import net.etfbl.musicfever.dto.User;

@ManagedBean
@RequestScoped
public class HistoryBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// RequestScoped je correct? 
	// Trea li ovo u UsersBean - za sda mislim ne da ga ne preopteretim
	
	// Komplikovaniji query - Iz hisotry izvuci pjesme -> na osnovu genres tih pjesama izvuci 5 koji imajue genre
	public ArrayList<Song> getSuggestions(User user) {
		ArrayList<Song> rez = new ArrayList<Song>();
		
		
		
		return rez;
	}
	
	// U UserDAO update history tabele sa VALUES(user.id, song.id, NOW()) a pregaziti najstariji history;
	// njastariji hsitory = SELECT *; provjeriti je li preko 20; ako je poredati po vremenu i zadni izbrsiati
	public void updateHistory(User user, Song song) {
		
	}
}
