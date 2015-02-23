package net.etfbl.musicfever.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.musicfever.dto.Genre;
import net.etfbl.musicfever.dto.Song;

public class SongDAO {
	private static final String SQL_SONG_DETAILS = "SELECT * FROM song WHERE id=? limit 1";
	private static final String SQL_ADD_SONG = "INSERT INTO song(user_id, name, artist, release_date, duration, file_type, lyrics, location, active, date_added) values(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
	private static final String SQL_UPDATE_HISTORY = "INSERT INTO history(user_id, song_id, time) values(?, ?, NOW())";
	private static final String SQL_UPDATE_SONG = "UPDATE song SET name=?, artist=?, realease_date=?, duration=?, file_type=?, lyrics=?, location=? WHERE id=?";
	private static final String SQL_DELETE_SONG = "UPDATE song SET active=0 WHERE id=?";
	private static final String SQL_ALL_USER_SONGS = "SELECT * FROM song WHERE user_id=? AND active=1";
	private static final String SQL_LATEST_SONGS = "SELECT * FROM song WHERE active=1 LIMIT 10";
	private static final String SQL_GET_MATCHING_SONGS = "SELECT * FROM song WHERE song.name LIKE ?";
	
	private static final String SQL_ALL_SONGS_BY_GENRE = "SELECT * FROM song WHERE user_id=? AND active=1";
	
	
	public static Song addSong(Song song) {
		Connection connection = null;
		ResultSet generatedKeys = null;

		Object[] values = {song.getUser_id(), song.getName(), song.getArtist(), song.getReleaseDate(), song.getDuration(), song.getFileType(), song.getLyrics(), song.getLocation(), 1};

		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, SQL_ADD_SONG, true, values);
			int rez = ps.executeUpdate();
			if (rez == 0) {
				song = null;
				System.out.println("Couldn't add song");
			} else {
				generatedKeys = ps.getGeneratedKeys();
				System.out.println("Song added");
				//Povratni info za ID
				if (generatedKeys.next()) {
					song.setId(generatedKeys.getInt(1));
					System.out.println("Adding genres...");
					
					for (int i = 0; i < song.getGenres().size(); i++) {
			            GenreDAO.setGenreOnSong(song.getId(), song.getGenres().get(i).getId(), song.getGenres().get(i).isPrimary());
			        }
				}
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Add song exception " + e);
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
		return song;
	}
	
	public static void updateHistory(int userid, Song song) {
		Connection connection = null;

		Object[] values = {userid, song.getId()};

		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, SQL_UPDATE_HISTORY, false, values);
			int rez = ps.executeUpdate();
			if (rez == 0) {
				System.out.println("Failed to add song to history");
			} else {
				System.out.println("Added song to history");
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Add song exception " + e);
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
	}
	
	public static boolean deleteSong(Song song) {
		Connection connection = null;
		Object values[] = {song.getId()};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_SONG, false, values);
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		} catch(Exception ex) {
			System.out.println("Song delete exception");
			ex.printStackTrace(System.err);
			return false;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
	}
	
	public static boolean updateSong(Song song) {
		Connection connection = null;
		boolean retVal = false;
		Object[] values = {song.getName(), song.getArtist(), song.getReleaseDate(), song.getDuration(), song.getFileType(), song.getLyrics(), song.getLocation(), song.getId()};
		// DODATI I IZMJENE NA GENRE!!!!!!!
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, SQL_UPDATE_SONG, false, values);
			int rez = ps.executeUpdate();
			if (rez == 0) {
				retVal = false;
				System.out.println("Couldn't update song");
			} else {
				retVal = true;
				System.out.println("Song updated");
			}
			ps.close();
			return retVal;
		} catch (SQLException e) {
			System.out.println("Update song exception " + e);
			return retVal;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
	}

	
	// DA PRIHVATA VISE ZANROVA I VRATI NA OSNOVU PREGLEDA???
	public static ArrayList<Song> getSongsByGenre() {
		Connection connection = null;
		ArrayList<Song> rez = new ArrayList<Song>();
		Object values[] = {};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_ALL_SONGS_BY_GENRE, false, values);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				/*rez.add(new User(rs.getInt(1), rs.getInt(7), rs.getInt(10) == 1, rs.getInt(11) == 1, rs.getInt(12) == 1, rs.getString(2),
									rs.getString(3), rs.getDate(9), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(12), rs.getString(13)));*/
			}			
			rs.close();
			pstmt.close();
			return rez;
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			return rez;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);			
		}
	}
	
	public static Song getSongDetails(Song song) {		
		Connection connection = null;
		Song songRet = null;
		Object values[] = {song.getId()};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SONG_DETAILS, false, values);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()){
				songRet = new Song(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5) == 1, rs.getInt(10), rs.getDate(6),
										rs.getInt(8), rs.getDate(7), rs.getString(9), rs.getString(11));
				songRet.setGenres(GenreDAO.getGenresOnSong(songRet.getId()));
			}
			
			rs.close();
			pstmt.close();
			
			return songRet;
		} catch (SQLException e) {
			System.out.println("Song details exception " + e);
			e.printStackTrace();
			return songRet;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
	}
	
	public static ArrayList<Song> getAllSongs(int userId) {
		Connection connection = null;
		ArrayList<Song> rez = new ArrayList<Song>();
		Object values[] = {userId};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_ALL_USER_SONGS, false, values);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Song temp = new Song(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5) == 1, rs.getInt(10), rs.getDate(6),
										rs.getInt(8), rs.getDate(7), rs.getString(9), rs.getString(11));
				temp.setGenres(GenreDAO.getGenresOnSong(temp.getId()));
				
				rez.add(temp);
			}			
			rs.close();
			pstmt.close();
			return rez;
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			return rez;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);			
		}
	}
	
	public static ArrayList<Song> getLatestAddedSongs() {
		Connection connection = null;
		ArrayList<Song> rez = new ArrayList<Song>();
		Object values[] = {};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_LATEST_SONGS, false, values);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Song temp = new Song(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5) == 1, rs.getInt(10), rs.getDate(6),
										rs.getInt(8), rs.getDate(7), rs.getString(9), rs.getString(11));
				temp.setGenres(GenreDAO.getGenresOnSong(temp.getId()));
				
				rez.add(temp);
			}			
			rs.close();
			pstmt.close();
			return rez;
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			return rez;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);			
		}
	}
	
	public static ArrayList<Song> getMatchingSongs(String query) {
		Connection connection = null;
		ArrayList<Song> rez = new ArrayList<Song>();
		Object values[] = {"%" + query + "%"};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_GET_MATCHING_SONGS, false, values);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Song temp = new Song(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5) == 1, rs.getInt(10), rs.getDate(6),
										rs.getInt(8), rs.getDate(7), rs.getString(9), rs.getString(11));
				temp.setGenres(GenreDAO.getGenresOnSong(temp.getId()));
				
				rez.add(temp);
			}			
			rs.close();
			pstmt.close();
			return rez;
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			return rez;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);			
		}
	}
}
