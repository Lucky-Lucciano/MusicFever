package net.etfbl.musicfever.beans;

import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.primefaces.event.RateEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import net.etfbl.artistdb.Artists;
import net.etfbl.artistdb.ArtistsServiceLocator;
import net.etfbl.musicfever.dao.GenreDAO;
import net.etfbl.musicfever.dao.SongDAO;
import net.etfbl.musicfever.dao.UserDAO;
import net.etfbl.musicfever.dto.Comment;
import net.etfbl.musicfever.dto.Genre;
import net.etfbl.musicfever.dto.Playlist;
import net.etfbl.musicfever.dto.Song;
import net.etfbl.musicfever.dto.SongFavourite;
import net.etfbl.musicfever.dto.User;
import net.etfbl.rss.model.Feed;
import net.etfbl.rss.model.FeedMessage;
import net.etfbl.rss.reader.RSSFeedParser;
import net.etfbl.rss.writer.RSSFeedWriter;

@ManagedBean
@SessionScoped
public class SongBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Song song = new Song();
	private Song songDelete = new Song();
	private Song songAdd = new Song();
	private Song songSelected = new Song();
	private Playlist plAdd = new Playlist();
	private Playlist plSelected = new Playlist();
	private Playlist plDelete = new Playlist();
	private Part uploadFile;
	
	private ArrayList<Genre> allGenres = GenreDAO.getAllGenres();
	private ArrayList<String> secondaryGenres = new ArrayList<String>();;
	private ArrayList<Genre> detailsSecGenres = new ArrayList<Genre>();
	private ArrayList<Song> latestAddedSongs = new ArrayList<Song>();
	private ArrayList<Song> searchResults = new ArrayList<Song>();
	private ArrayList<Song> userSongs = new ArrayList<Song>();
	private ArrayList<Song> playlistSongs = new ArrayList<Song>();
	private ArrayList<Playlist> userPlaylists = new ArrayList<Playlist>();
	private ArrayList<FeedMessage> feedCont = new ArrayList<FeedMessage>();
	private ArrayList<SongFavourite> reportFav = new ArrayList<SongFavourite>();
	private StreamedContent file;
	private boolean favourited;
	private String searchQuery = "";
	private String primaryGenre = "";
	private String detailsPrimaryGenre = "";
	private Date durationTime;
	private int userId = -1;
	private String artist = "";
	private Integer rating;
	private ArrayList<Song> songComments = new ArrayList<Song>();
	private String profilePic = "";
	private int deletCommentUser;
	private String addComment;
	private String plAddFromSong;
	private Map<String,String> plAddFromSongs = new HashMap<String, String>();
	
	{
		generateRss();
	}
	
	
//	public String getProfilePic() {
//		return  UserDAO.getCommentsOnSong(songSelected);
//	}
	
	
	
	public ArrayList<FeedMessage> getFeedCont() {
		RSSFeedParser parser = new RSSFeedParser("http://www.rollingstone.com/music.rss");
		Feed feed = parser.readFeed();
		ArrayList<FeedMessage> tmp = new ArrayList<FeedMessage>();
		
		System.out.println(feed);
		for (FeedMessage message : feed.getMessages()) {
			if(tmp.size() > 5)
				break;
			tmp.add(message);
		}		
//		
//		List<FeedMessage> source = feed.getMessages();
//		
//		for (int i = 0; i < 5; i++) {
//			tmp.add(source.get(i));
//		}

		return tmp;
	}
	
	public String getPlAddFromSong() {
		return plAddFromSong;
	}

	public void setPlAddFromSong(String plAddFromSong) {
		this.plAddFromSong = plAddFromSong;
	}
	
	

	public ArrayList<SongFavourite> getReportFav() {
		return SongDAO.reportFavourites();
	}

	public void setReportFav(ArrayList<SongFavourite> reportFav) {
		this.reportFav = reportFav;
	}

	public Map<String, String> getPlAddFromSongs() {
		UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();
		
		ArrayList<Playlist> arr = SongDAO.getPlaylists(uid);
		for (Playlist product : arr) {
		   plAddFromSongs.put(product.getTitle(), String.valueOf(product.getId()));
		}
		
		return plAddFromSongs;
	}

	public void setPlAddFromSongs(Map<String, String> plAddFromSongs) {
		this.plAddFromSongs = plAddFromSongs;
	}

	public String addToPlaylist() {
		if(SongDAO.addSongToPlaylist(Integer.parseInt(plAddFromSong), songSelected)) {
			String messageSuccess = "Succesfully added to playlist!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			
			return "";
		} else {
			String messageFailure = "Failed to add!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}
	
	public void generateRss(){ 
		String copyright = "Copyright hold by Luka Trifunovic";
	    String title = "Best rated songs";
	    String description = "Latest best rated songs";
	    String language = "en";
	    String link = "";
	    Calendar cal = new GregorianCalendar();
	    Date creationDate = cal.getTime();
	    SimpleDateFormat date_format = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);
	    String pubdate = date_format.format(creationDate);
	    Feed rssFeeder = new Feed(title, link, description, language, copyright, pubdate);
	    
	    // Now add one example entry
		ArrayList<Song> songs = SongDAO.selectBest5Songs();
		for (Song d : songs){
		    FeedMessage feed = new FeedMessage();
		    feed.setTitle(d.getName());
		    feed.setDescription(d.getArtist());
		    feed.setAuthor("Luka Trifunovic");
		    feed.setLink("http://localhost:8080/MusicFever/faces/index.xhtml");
		    rssFeeder.getMessages().add(feed);
		}
	    // Now write the file
	    String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
	    RSSFeedWriter writer = new RSSFeedWriter(rssFeeder, realPath + "top5.rss");
	    try {
	      writer.write();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}

	public void setFeedCont(ArrayList<FeedMessage> feedCont) {
		this.feedCont = feedCont;
	}
	
	public Playlist getPlDelete() {
		return plDelete;
	}

	public void setPlDelete(Playlist plDelete) {
		this.plDelete = plDelete;
	}

	public String addNewComment() {
		UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();
		if(SongDAO.addComment(uid, songSelected, addComment)) {
			return "";
		} else {
			return null;
		}
		
	}
	
	public String addNewPlaylist() {
		UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();
		
		if(SongDAO.addPlaylist(uid, plAdd)) {
			plAdd = new Playlist();
			String messageSuccess = "Succesfully added playlist!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			
			return "";
		} else {
			plAdd = new Playlist();
			String messageFailure = "Playlist couldn't be added!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}
	
	
	public ArrayList<Song> getPlaylistSongs() {
		return playlistSongs;
	}



	public void setPlaylistSongs(ArrayList<Song> playlistSongs) {
		this.playlistSongs = playlistSongs;
	}



	public ArrayList<Playlist> getUserPlaylists() {
		UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();
		
		return SongDAO.getPlaylists(uid);
	}



	public void setUserPlaylists(ArrayList<Playlist> userPlaylists) {
		this.userPlaylists = userPlaylists;
	}

	public String removePlaylist() {		
		if(SongDAO.deletePlaylist(plDelete)) {
			String messageSuccess = "Playlist removed!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			
			return "";
		} else {
			String messageFailure = "Playlist couldn't be removed!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}
	
	public String showPlaylistSongs() {
		playlistSongs = SongDAO.getSongsOnPlaylist(plSelected);
		
		return "";
	}
	
	public Playlist getPlAdd() {
		return plAdd;
	}

	public void setPlAdd(Playlist plAdd) {
		this.plAdd = plAdd;
	}

	public Playlist getPlSelected() {
		return plSelected;
	}

	public void setPlSelected(Playlist plSelected) {
		this.plSelected = plSelected;
	}

	public String removeComment() {		
		if(SongDAO.deleteComment(deletCommentUser, songSelected)) {
			String messageSuccess = "Comment added!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			
			// Odvesti na my songs
			return "";
		} else {

			String messageFailure = "Comment couldn't be added!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}
	
	public String getAddComment() {
		return addComment;
	}

	public void setAddComment(String addComment) {
		this.addComment = addComment;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public int getDeletCommentUser() {
		return deletCommentUser;
	}

	public void setDeletCommentUser(int deletCommentUser) {
		this.deletCommentUser = deletCommentUser;
	}

	public ArrayList<Comment> getSongComments() {
		return SongDAO.getCommentsOnSong(songSelected);
	}
	
	/*public String deleteComment() {
		return SongDAO.getCommentsOnSong(songSelected);
	}*/


	public void setSongComments(ArrayList<Song> songComments) {
		this.songComments = songComments;
	}


	public Integer getRating() {
		UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();
		System.out.println("Checking rat: " + SongDAO.getRating(uid, songSelected));
		return SongDAO.getRating(uid, songSelected);
	}


	public void setRating(Integer rating) {
		this.rating = rating;
	}


	public void setFavourited(boolean favourited) {
		this.favourited = favourited;
	}
	
	public void onrate(RateEvent rateEvent) {
		UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();
		
		System.out.println("Checking rat: " + uid + " rating: " + ((Integer) rateEvent.getRating()).intValue());
		
		SongDAO.addRating(uid, songSelected, ((Integer) rateEvent.getRating()).intValue());
		
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rate Event", "You rated:" + ((Integer) rateEvent.getRating()).intValue());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
     
    public void oncancel() {
    	UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();
		
    	SongDAO.addRating(uid, songSelected, 0);
    	
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


	public boolean getFavourited() {
		UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();
		boolean temp = SongDAO.isFav(uid, songSelected);
		
		return temp;
	}
	
	
	public String addSong() {
		songAdd.setGenres(convertGenres(primaryGenre, secondaryGenres));
		
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
	
	public String updateSong() {
		songSelected.setGenres(convertGenres(primaryGenre, secondaryGenres));
		
		if(SongDAO.updateSong(songSelected)) {
			songSelected = new Song();
			String messageSuccess = "Succesfully updated!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			
			return "user.html?faces-redirect=true";
		} else {
			songSelected = new Song();
			String messageFailure = "Song couldn't be updated!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}
	
	public StreamedContent getFile() {
		try {
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
	
	public String addToFavourites() {
		UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();
		
		if(SongDAO.addSongToFavourites(uid, songSelected)) {
			String messageSuccess = "Song added to favourites!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			return "";
		} else {
			String messageFailure = "Failed add to favourites!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}
	
	public String getDetailsPrimaryGenre() {
		String rez = "";

		UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();
		// History tracker
		SongDAO.updateHistory(uid, songSelected);

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
		UserBean curentUser = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		int uid = curentUser.getUser().getId();

		return SongDAO.getAllSongs(uid);
	}

	public void setUserSongs(ArrayList<Song> userSongs) {
		this.userSongs = userSongs;
	}
	
	
	public Part getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(Part uploadFile) {
		this.uploadFile = uploadFile;
	}

	public void callUploadServlet() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.setAttribute("uploadFile", getUploadFile());
			//request.setAttribute("songId", String.valueOf(songSelected.getId()));
			request.setAttribute("songId", "1");
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Uploader");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}
}
