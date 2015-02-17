package net.etfbl.musicfever.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SongDAO {
	private static final String SQL_LOGIN = "SELECT * FROM user WHERE username = ? and password = md5(?) limit 1";
	private static final String SQL_ADD_USER = "INSERT INTO users(username, password, name, surname, email, usergroup, JMBG, registration_date, active, approved, superuser, image) values(?, md5(?), ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?)";
	private static final String SQL_APPROVE_USER = "UPDATE user SET approved = 1 WHERE id=?";
	private static final String SQL_ALL_USERS = "SELECT * FROM user";
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
}
