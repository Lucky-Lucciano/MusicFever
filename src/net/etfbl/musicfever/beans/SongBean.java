package net.etfbl.musicfever.beans;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import net.etfbl.artistdb.Artists;
import net.etfbl.artistdb.ArtistsServiceLocator;
import net.etfbl.musicfever.dao.GenreDAO;
import net.etfbl.musicfever.dao.SongDAO;
import net.etfbl.musicfever.dao.UserDAO;
import net.etfbl.musicfever.dto.Genre;
import net.etfbl.musicfever.dto.Song;
import net.etfbl.musicfever.dto.User;

@ManagedBean
@SessionScoped
public class SongBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Song song = new Song();
	private Song songDelete = new Song();
	private Song songAdd = new Song();
	private Song songSelected = new Song();

	private ArrayList<Genre> allGenres = GenreDAO.getAllGenres();
	private ArrayList<String> secondaryGenres = new ArrayList<String>();;
	private ArrayList<Genre> detailsSecGenres = new ArrayList<Genre>();
	private ArrayList<Song> latestAddedSongs = new ArrayList<Song>();
	private ArrayList<Song> searchResults = new ArrayList<Song>();
	private ArrayList<Song> userSongs = new ArrayList<Song>();
	private StreamedContent file;
	private String searchQuery = "";
	private String primaryGenre = "";
	private String detailsPrimaryGenre = "";
	private Date durationTime;
	private int userId = -1;
	private String artist = "";
	
	public String addSong() {
		System.out.println(songAdd);
		System.out.println("Other values: primaryGenreA - " + primaryGenre);
		songAdd.setGenres(convertGenres(primaryGenre, secondaryGenres));
		System.out.println(songAdd);
		System.out.println("Other values: primaryGenre - " + primaryGenre);
		
		if((song = SongDAO.addSong(songAdd)) != null) {
			songAdd = new Song();
			String messageSuccess = "Succesfully added!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			
			// Odvesti na my songs
			return "index.html?faces-redirect=true";
		} else {
			songAdd = new Song();
			String messageFailure = "Song couldn't be added!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}
	
	public StreamedContent getFile() {
		try {
		System.out.println("Unutar fff: " +  songSelected.getId());
		System.out.println("Unutar det: " +  songSelected.getArtist() + " - " + songSelected.getName() + ".mp3");
		InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/music/" + songSelected.getId() + ".mp3");
        file = new DefaultStreamedContent(stream, "audio/mpeg", songSelected.getArtist() + " - " + songSelected.getName() + ".mp3");
		
//		InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/img/optimus.jpg");
//        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.jpg");
        return file;
		} catch(Exception e) {
			System.out.println("Prso " + e.getMessage());
        	e.printStackTrace();
        	return null;
        }
    }
	
	
	public ArrayList<Song> getLatestAddedSongs() {
		songSelected = new Song();
		return SongDAO.getLatestAddedSongs();
	}
	
	public void addSongToFavourite() {
		
	}
	
	public ArrayList<Song> getSearchResults() {
		ArrayList<Song> res = SongDAO.getMatchingSongs(searchQuery);
		searchQuery = "";
		return res;
	}
	
	public String addNewArtist() {
		try {
			ArtistsServiceLocator loc = new ArtistsServiceLocator();
			Artists art = loc.getArtists();
			
			if(art.addArtist(artist)) {
				artist = "";
				String messageSuccess = "Artist succesfully added!";
				System.out.println(messageSuccess);
				addMessage(messageSuccess);
				return "";
			} else {
				artist = "";
				songDelete = new Song();
				String messageFailure = "Failed to add artist!";
				System.out.println(messageFailure);
				addMessage(messageFailure);
				return null;
			}
			
		} catch (Exception exp) {
			exp.printStackTrace();
			return null;
		}
	}
	
	public String updateSong() {
		if(SongDAO.updateSong(songSelected)){
			String messageSuccess = "Song info updated!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			return "";
		} else {
			songDelete = new Song();
			String messageFailure = "Failed to update song info!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}
	
	public String deleteSong() {
		if(SongDAO.deleteSong(songDelete)){
			songDelete = new Song();
			String messageSuccess = "Song deleted succesfully!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			return "";
		} else {
			songDelete = new Song();
			String messageFailure = "Song couldn't be deleted!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}
	
	public List<String> completeText(String query) {
		String[] artistResults = queryMatchingArtist(query);
//		System.out.println("comp: " + artistResults[0]);
		List<String> results = new ArrayList<String>();
		
		if(artistResults != null) {
	        for(int i = 0; i < artistResults.length; i++) {
	            results.add(artistResults[i]);
	        }
        }
         
        return results;
    }
	
	public List<String> completeTextGenre(String query) {
		List<String> results = new ArrayList<String>();
        for(int i = 0; i < allGenres.size(); i++) {
        	String currentGenName = allGenres.get(i).getName();
        	
        	if(currentGenName.toLowerCase().contains(query.toLowerCase())) {
                results.add(currentGenName);
        	}
        }
         
        return results;
    }
	
	public List<String> completeSecGenres(String query) {
        List<String> filteredGenres = new ArrayList<String>();
         
        for (int i = 0; i < allGenres.size(); i++) {
            Genre gen = allGenres.get(i);
            if(gen.getName().toLowerCase().contains(query.toLowerCase())) {
            	filteredGenres.add(gen.getName());
            }
        }
         
        return filteredGenres;
    }
	
	public ArrayList<Genre> convertGenres(String prim, ArrayList<String> secs) {
		ArrayList<Genre> retGenres = new ArrayList<Genre>();
         
        for (int i = 0; i < allGenres.size(); i++) {
            Genre gen = allGenres.get(i);
            
            if(secs != null) {
	            for (int j = 0; j < secs.size(); j++) {
	            	System.out.println(secs.get(j));
		            if(gen.getName().toLowerCase().matches(secs.get(j))) {
		            	retGenres.add(gen);
		            }
	            }
	            
	            if(gen.getName().toLowerCase().matches(prim.toLowerCase())) {
	            	gen.setPrimary(true);
	            	retGenres.add(gen);
	            }
            }
        }
         
        return retGenres;
    }

	public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public String[] queryMatchingArtist(String query) {
		try {
			ArtistsServiceLocator loc = new ArtistsServiceLocator();
			Artists art = loc.getArtists();
			
			return art.getMatchingArtists(query);
		} catch (Exception exp) {
			exp.printStackTrace();
			return null;
		}
	}
	
	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public Song getSongDelete() {
		return songDelete;
	}

	public void setSongDelete(Song songDelete) {
		this.songDelete = songDelete;
	}

	public Song getSongAdd() {
		return songAdd;
	}

	public void setSongAdd(Song songAdd) {
		this.songAdd = songAdd;
	}

	public Song getSongSelected() {
		return songSelected;
	}

	public void setSongSelected(Song songSelected) {
		this.songSelected = songSelected;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getPrimaryGenre() {
		return primaryGenre;
	}

	public void setPrimaryGenre(String primaryGenre) {
		this.primaryGenre = primaryGenre;
	}

	public Date getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(Date durationTime) {
		this.durationTime = durationTime;
	}

	public List<String> getSecondaryGenres() {
		return secondaryGenres;
	}

	public void setSecondaryGenres(ArrayList<String> secondaryGenres) {
		this.secondaryGenres = secondaryGenres;
	}


	public String getArtist() {
		return artist;
	}


	public void setArtist(String artist) {
		this.artist = artist;
	}



	public void setSearchResults(ArrayList<Song> searchResults) {
		this.searchResults = searchResults;
	}


	public String getSearchQuery() {
		return searchQuery;
	}


	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}


	public String getDetailsPrimaryGenre() {
		String rez = "";
        
        for (int i = 0; i < songSelected.getGenres().size(); i++) {
            Genre gen = songSelected.getGenres().get(i);
            
            if(gen.isPrimary()) {
            	rez = gen.getName();
            }
        }
		return rez;
	}


	public void setDetailsPrimaryGenre(String detailsPrimaryGenre) {
		this.detailsPrimaryGenre = detailsPrimaryGenre;
	}


	public ArrayList<Genre> getDetailsSecGenres() {
		detailsSecGenres = new ArrayList<Genre>();
 
        for (int i = 0; i < songSelected.getGenres().size(); i++) {
            Genre gen = songSelected.getGenres().get(i);
            
            if(!gen.isPrimary()) {
            	detailsSecGenres.add(gen);
            }
        }
		return detailsSecGenres;
	}


	public void setDetailsSecGenres(ArrayList<Genre> detailsSecGenres) {
		this.detailsSecGenres = detailsSecGenres;
	}

	public ArrayList<Song> getUserSongs() {
		System.out.println("Adding song to history...: ");
		UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();
		
		SongDAO.updateHistory(uid, songSelected);
		return SongDAO.getAllSongs(uid);
	}

	public void setUserSongs(ArrayList<Song> userSongs) {
		this.userSongs = userSongs;
	}
}
