package net.etfbl.musicfever.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import net.etfbl.musicfever.dao.ConnectionPool;
import net.etfbl.musicfever.dto.Event;
import net.etfbl.musicfever.dto.Genre;
import net.etfbl.musicfever.dto.User;
import net.etfbl.musicfever.dao.DAOUtil;

public class EventDAO {
	private static final String SQL_ADD_EVENT = "INSERT INTO event(name, start_time, location, creation_time, approved, creatorId) values(?, ?, ?, NOW(), ?, ?)";
//	private static final String SQL_UPDATE_EVENT = "UPDATE event SET name=?, start_time=?, location=? WHERE id=?";
	private static final String SQL_DELETE_EVENT = "DELETE FROM event WHERE id=?";
	private static final String SQL_ALL_EVENTS = "SELECT * FROM event";
	private static final String SQL_APPROVE_EVENT = "UPDATE event SET approved = 1 WHERE id=?";
	
	public static boolean deleteEvent(Event event) {
		Connection connection = null;
		Object values[] = {event.getId()};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_EVENT, false, values);
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
	
	public static boolean approveEvent(Event event) {
		Connection connection = null;
		Object values[] = {event.getId()};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_APPROVE_EVENT, false, values);
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
	
	/*public static boolean updateEvent(Event event) {
		Connection connection = null;
		boolean retVal = false;
		Object[] values = {event.getName(), event.getStartTime(), event.getLocation(),  event.getId()};

		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, SQL_UPDATE_EVENT, false, values);
			int rez = ps.executeUpdate();
			if (rez == 0) {
				retVal = false;
				System.out.println("Couldn't update event");
			} else {
				retVal = true;
				System.out.println("Event updated");
			}
			ps.close();
			return retVal;
		} catch (SQLException e) {
			System.out.println("Event update exception " + e);
			return retVal;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
	}*/
	
	public static boolean addEvent(Event event, int creatorId) {
		Connection connection = null;
		boolean retVal = false;
		Object[] values = {event.getName(), event.getStartTime(), event.getLocation(), 0, creatorId};

		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, SQL_ADD_EVENT, false, values);
			int rez = ps.executeUpdate();
			if (rez == 0) {
				retVal = false;
				System.out.println("Couldnt add event");
			} else {
				retVal = true;
				System.out.println("Event added");
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Event registration exception " + e);
			return false;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
		return retVal;
	}
	
	public static ArrayList<Event> getAllEvents() {
		Connection connection = null;
		ArrayList<Event> rez = new ArrayList<Event>();
		Object values[] = {};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_ALL_EVENTS, false, values);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				rez.add(new Event(rs.getInt(1), rs.getInt(7), rs.getDate(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6) == 1));
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
