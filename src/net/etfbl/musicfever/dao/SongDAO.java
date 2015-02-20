package net.etfbl.musicfever.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.musicfever.dto.Song;

public class SongDAO {
	private static final String SQL_SONG_DETAILS = "SELECT * FROM song WHERE id=? limit 1";
	private static final String SQL_ADD_SONG = "INSERT INTO song(name, artist, release_date, duration, file_type, lyrics, location, active, registration_date) values(?, ?, ?, ?, ?, ?, ?, ?, NOW())";
	private static final String SQL_UPDATE_SONG = "UPDATE song SET name=?, artist=?, realease_date=?, duration=?, file_type=?, lyrics=?, location=? WHERE id=?";
	private static final String SQL_DELETE_SONG = "UPDATE song SET active=0 WHERE id=?";
	private static final String SQL_ALL_USERS = "SELECT * FROM user WHERE usergroup=0 AND active=1";
	
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
	
	public static Song addUser(Song song) {
		Connection connection = null;
		ResultSet generatedKeys = null;

		Object[] values = {song.getName(), song.getArtist(), song.getReleaseDate(), song.getDuration(), song.getFileType(), song.getLyrics(), song.getLocation(), 1};
		// Definitvno provjeriti upit u bazi
		// UBACITI GENRE I OSTALO
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
	
	// DA PRIHVATA VISE ZANROVA I VRATI NA OSNOVU PREGLEDA???
	public static ArrayList<Song> getSongsByGenre() {
		Connection connection = null;
		ArrayList<Song> rez = new ArrayList<Song>();
		Object values[] = {};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_ALL_USERS, false, values);
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
				/*int id, String artist, String name, boolean active, int fileType,
				int releaseDate, int duration, Date registrationDate,
				String location, String lyrics*/
				songRet = new Song(rs.getInt(1), rs.getString(7), rs.getString(10), rs.getInt(11) == 1, rs.getInt(12), rs.getInt(2),
									rs.getInt(3), rs.getDate(9), rs.getString(4), rs.getString(5));
			}
			
			// Get AllGenre 
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
}
