package net.etfbl.musicfever.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import net.etfbl.musicfever.dao.ConnectionPool;
import net.etfbl.musicfever.dto.User;
import net.etfbl.musicfever.dao.DAOUtil;

public class UserDAO {
	private static final String SQL_LOGIN = "SELECT * FROM user WHERE username = ? and password = md5(?) limit 1";
	private static final String SQL_ADD_USER = "INSERT INTO user(username, password, name, surname, email, usergroup, JMBG, registration_date, active, approved, superuser, image) values(?, md5(?), ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?)";
	private static final String SQL_UPDATE_USER = "UPDATE user SET name=?, surname=?, email=?, JMBG=?, image=? WHERE id=?";
	private static final String SQL_APPROVE_USER = "UPDATE user SET approved = 1 WHERE id=?";
	private static final String SQL_DELETE_USER = "UPDATE user SET active=0 WHERE id=?";
	private static final String SQL_UPGRADE_TO_SUPERUSER = "UPDATE user SET superuser=1 WHERE id=?";
	private static final String SQL_ALL_USERS = "SELECT * FROM user WHERE usergroup=0 AND active=1";
	private static final String SQL_USERNAME_AVAILABLE = "SELECT count(*) FROM user WHERE username=?";
	
	// Ajaxom se moze provjeriti je l dostupno - ako ne bude vremena izbaciti
	public static boolean usernameAvailable(String name) {
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
	
	
	public static boolean approveUser(int userID) {
		Connection connection = null;
		Object values[] = {userID};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_APPROVE_USER, false, values);
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
	
	public static boolean deleteUser(User user) {
		Connection connection = null;
		Object values[] = {user.getId()};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_USER, false, values);
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
	
	public static boolean upgradeToSuperuser(int userID) {
		Connection connection = null;
		Object values[] = {userID};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_UPGRADE_TO_SUPERUSER, false, values);
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
	
	public static boolean updateUser(User user) {
		Connection connection = null;
		boolean retVal = false;
		String profileImage = user.getProfileImage().matches("") ? "http://3.bp.blogspot.com/-2uL3QmmToWI/Uia-0cvD7YI/AAAAAAAABBQ/ICJLdNIXHMQ/s1600/facebook-default--profile-pic2.jpg" : user.getProfileImage();
		Object[] values = {user.getName(), user.getSurname(), user.getEmail(), user.getJMBG(), profileImage, user.getId()};

		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, SQL_UPDATE_USER, false, values);
			int rez = ps.executeUpdate();
			if (rez == 0) {
				retVal = false;
				System.out.println("Couldn't update user");
			} else {
				retVal = true;
				System.out.println("User updated");
			}
			ps.close();
			return retVal;
		} catch (SQLException e) {
			System.out.println("Registration exception " + e);
			return retVal;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
	}
	
	public static User addUser(User user) {
		Connection connection = null;
		ResultSet generatedKeys = null;
		String profileImage = user.getProfileImage().matches("") ? "http://3.bp.blogspot.com/-2uL3QmmToWI/Uia-0cvD7YI/AAAAAAAABBQ/ICJLdNIXHMQ/s1600/facebook-default--profile-pic2.jpg" : user.getProfileImage();
		Object[] values = {user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getEmail(), 0, user.getJMBG(), 1, 0, 0, profileImage};

		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(connection, SQL_ADD_USER, true, values);
			int rez = ps.executeUpdate();
			if (rez == 0) {
				user = null;
				System.out.println("Couldnt add user");
			} else {
				generatedKeys = ps.getGeneratedKeys();
				System.out.println("User added");
				//Povratni info za ID
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getInt(1));
				}
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Registration exception " + e);
			return null;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
		return user;
	}
	
	// vraca sve registrovane korsinike
	public static ArrayList<User> getAllUsers() {
		Connection connection = null;
		ArrayList<User> rez = new ArrayList<User>();
		Object values[] = {};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_ALL_USERS, false, values);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				rez.add(new User(rs.getInt(1), rs.getInt(7), rs.getInt(10) == 1, rs.getInt(11) == 1, rs.getInt(12) == 1, rs.getString(2),
									rs.getString(3), rs.getDate(9), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(8), rs.getString(13)));
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
	
	public static User login(User user) {		
		Connection connection = null;
		User userRet = null;
		Object values[] = {user.getUsername(), user.getPassword()};
		
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_LOGIN, false, values);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()){
				userRet = new User(rs.getInt(1), rs.getInt(7), rs.getInt(10) == 1, rs.getInt(11) == 1, rs.getInt(12) == 1, rs.getString(2),
									rs.getString(3), rs.getDate(9), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(12), rs.getString(13));
			}
			rs.close();
			pstmt.close();
			
			return userRet;
		} catch (SQLException exp) {
			exp.printStackTrace();
			return userRet;
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
	}
}
