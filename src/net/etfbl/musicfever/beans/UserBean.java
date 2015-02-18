package net.etfbl.musicfever.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.etfbl.musicfever.dto.Song;
import net.etfbl.musicfever.dto.User; 
import net.etfbl.musicfever.dao.UserDAO;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private User user = new User();
	private User userAdd = new User();
	private User userDelete = new User();
	private User userSelected = new User();
	private boolean loggedIn = false;

	// Setuje se samo active na false i prikaze BUBBLE meesage ako je uspjesno
	public void deleteUser() {
		
	}
	
	public String updateUser() {
		// U sluacju da ne uspije, kroz growl ispsiati gresku, inace refresh
		UserDAO.approveUser(userSelected);
		return "";
	}
	
	public ArrayList<User> getAllUsers() {
		userSelected = new User();
		return UserDAO.getAllUsers();
	}
	
	// Podesiti return da vratina poseban page ako cu ici bez modal window.
	public String login() {
		if((user = UserDAO.login(user)) != null) {
			loggedIn = true;
			System.out.println("Ulogovan user " + user.getUsername());
			String site = "";
			// Sada na osnvu tiupa oderditi koju stranicu da vidi - po potrebi iz baze podatke dodatne popuniti
			switch (user.getUsergroup()) {
				case 0:
					System.out.println("Grupa: 0 - User");
					site = "user?faces-redirect=true";
					break;
				case 1:
					System.out.println("Grupa: 1 - Admin");
					site = "admin?faces-redirect=true";
					break;
				default:
					site = "index?faces-redirect=true";
					break;
			}
			
			return site;
		}
		System.out.println("Nije ulogovan");
		user = new User();
		loggedIn = false;
		return "index?faces-redirect=true";
	}
	
	public String logout() {
		System.out.println("Logged out");
		loggedIn = false;
		return "index?faces-redirect=true";
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

	public User getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(User userSelected) {
		this.userSelected = userSelected;
	}
}
