package net.etfbl.musicfever.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import net.etfbl.musicfever.dao.ConnectionPool;
import net.etfbl.musicfever.dto.Genre;
import net.etfbl.musicfever.dto.User;
import net.etfbl.musicfever.dao.DAOUtil;

public class GenreDAO {
	private static final String SQL_ADD_GENRE = "INSERT INTO genre(name, active) values(?, ?)";
	private static final String SQL_UPDATE_GENRE = "UPDATE genre SET name=? WHERE id=?";
	private static final String SQL_DELETE_GENRE = "UPDATE genre SET active=0 WHERE id=?";
	private static final String SQL_ALL_GENRES = "SELECT * FROM genre WHERE active=1";
	private static final String SQL_USERNAME_AVAILABLE = "SELECT count(*) FROM user WHERE username=?";
	
	// Ajaxom se moze provjeriti je l dostupno - ako ne bude vremena izbaciti
	public static boolean genreAvailable(String name) {
		Connection connection = null;
		boolean rezultat = false;
		Object values[] = {name};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_USERNAME_AVAILABLE, false, values);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				rezultat = (rs.getInt(1) == 0);
			else
				rezultat = false;
			rs.close();
			pstmt.close();
			return rezultat;
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			return false;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
	}
	
	public static boolean deleteGenre(Genre genre) {
		Connection connection = null;
		Object values[] = {genre.getId()};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_GENRE, false, values);
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			return false;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
	}
	
	public static boolean updateGenre(Genre genre) {
		Connection connection = null;
		boolean retVal = false;
		Object[] values = {genre.getName(), genre.getId()};

		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, SQL_UPDATE_GENRE, false, values);
			int rez = ps.executeUpdate();
			if (rez == 0) {
				retVal = false;
				System.out.println("Couldn't update genre");
			} else {
				retVal = true;
				System.out.println("Genre updated");
			}
			ps.close();
			return retVal;
		} catch (SQLException e) {
			System.out.println("Genre update exception " + e);
			return retVal;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
	}
	
	public static boolean addGenre(Genre genre) {
		Connection connection = null;
		boolean retVal = false;
		Object[] values = {genre.getName(), 1};

		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, SQL_ADD_GENRE, false, values);
			int rez = ps.executeUpdate();
			if (rez == 0) {
				retVal = false;
				System.out.println("Couldnt add genre");
			} else {
				retVal = true;
				System.out.println("Genre added");
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Genre registration exception " + e);
			return false;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
		return retVal;
	}
	
	public static ArrayList<Genre> getAllGenres() {
		Connection connection = null;
		ArrayList<Genre> rez = new ArrayList<Genre>();
		Object values[] = {};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_ALL_GENRES, false, values);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				rez.add(new Genre(rs.getInt(1), rs.getString(2), rs.getInt(3) == 1));
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
	
	// vraca sve registrovane korsinike koji su na pjesmi
	//
	public static ArrayList<Genre> getAllGenresOnSong() {
		Connection connection = null;
		ArrayList<Genre> rez = new ArrayList<Genre>();
		Object values[] = {};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_ALL_GENRES, false, values);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				rez.add(new Genre(rs.getInt(1), rs.getString(2), rs.getInt(3) == 1));
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
	
	// vraca sve registrovane korsinike koji su na pjesmi
	// prvo izbrisati sve genre na toj pjesmi pa zamjeniti s novima
	public static boolean setGenresOnSong(ArrayList<Genre> genres, int primaryGenreId) {
		Connection connection = null;
		boolean rez = false;
		Object values[] = {};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_ALL_GENRES, false, values);
			ResultSet rs = pstmt.executeQuery();
//			while(rs.next()) {
//				rez.add(new Genre(rs.getInt(1), rs.getString(2), rs.getInt(3) == 1));
//			}			
			rs.close();
			pstmt.close();
			return rez;
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
			return false;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);			
		}
	}	
}
