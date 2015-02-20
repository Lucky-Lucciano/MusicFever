package net.etfbl.musicfever.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import net.etfbl.musicfever.dto.Genre;

@ManagedBean
@RequestScoped
public class GenreBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Genre genre = new Genre();
	private Genre genreAdd = new Genre();
	private Genre genreDelete = new Genre();
	
	//Samo admin ima pristup upravljanja ovim podacima
	
}
