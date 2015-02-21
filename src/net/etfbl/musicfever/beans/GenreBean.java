package net.etfbl.musicfever.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import net.etfbl.musicfever.dao.GenreDAO;
import net.etfbl.musicfever.dao.UserDAO;
import net.etfbl.musicfever.dto.Genre;
import net.etfbl.musicfever.dto.User;

@ManagedBean
@SessionScoped
public class GenreBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Genre genre = new Genre();
	private Genre genreAdd = new Genre();
	private Genre genreDelete = new Genre();
	
	//Samo admin ima pristup upravljanja ovim podacima
	public ArrayList<Genre> getAllGenres() {
//		userSelected = new User();
		return GenreDAO.getAllGenres();
	}
	
	public String removeGenre() {
		System.out.println("usao u remove");
		if(GenreDAO.deleteGenre(genreDelete)){
			genreDelete = new Genre();
			String messageSuccess = "Genre deleted succesfully!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			return "";
		} else {
			genreDelete = new Genre();
			String messageFailure = "Genre couldn't be deleted!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}
	
	public String editGenre() {
		if(GenreDAO.updateGenre(genre)){
			genreDelete = new Genre();
			String messageSuccess = "Genre updated succesfully!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			return "";
		} else {
			genreDelete = new Genre();
			String messageFailure = "Genre couldn't be updated!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}
	
	public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Genre getGenreAdd() {
		return genreAdd;
	}

	public void setGenreAdd(Genre genreAdd) {
		this.genreAdd = genreAdd;
	}

	public Genre getGenreDelete() {
		return genreDelete;
	}

	public void setGenreDelete(Genre genreDelete) {
		this.genreDelete = genreDelete;
	}
}
