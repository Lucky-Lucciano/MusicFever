package net.etfbl.musicfever.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.etfbl.musicfever.dto.Song;
import net.etfbl.musicfever.dto.User; 

@ManagedBean
@SessionScoped
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private User user = new User();
	private User userAdd = new User();
	private User userDelete = new User();
	private boolean loggedIn = false;
	
	
	// Setuje se samo active na false i prikaze BUBBLE meesage ako je uspjesno
	public void deleteUser() {
		
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUserAdd() {
		return userAdd;
	}

	public void setUserAdd(User userAdd) {
		this.userAdd = userAdd;
	}

	public User getUserDelete() {
		return userDelete;
	}

	public void setUserDelete(User userDelete) {
		this.userDelete = userDelete;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
