package net.etfbl.musicfever.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private int usergroup;
	private boolean active;
	private boolean approved;
	private boolean superuser;
	private String username;
	private String password;
	private Date registration;
	private String name;
	private String surname;
	private String email;
	private String JMBG;
	private String profileImage;
	private ArrayList<Playlist> playlists = new ArrayList<Playlist>(); // nije u constr.

	public User () {
		
	}
	

	public User(int id, int usergroup, boolean active, boolean approved, boolean superuser,
			String username, String password, Date registration, String name,
			String surname, String email, String jMBG, String profileImage) {
		super();
		this.id = id;
		this.usergroup = usergroup;
		this.superuser = superuser;
		this.active = active;
		this.approved = approved;
		this.username = username;
		this.password = password;
		this.registration = registration;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.JMBG = jMBG;
		this.profileImage = profileImage;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(int usergroup) {
		this.usergroup = usergroup;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegistration() {
		SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");

		return sdf.format(registration);
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJMBG() {
		return JMBG;
	}

	public void setJMBG(String jMBG) {
		this.JMBG = jMBG;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}


	public ArrayList<Playlist> getPlaylists() {
		return playlists;
	}


	public void setPlaylists(ArrayList<Playlist> playlists) {
		this.playlists = playlists;
	}


	public boolean isSuperuser() {
		return superuser;
	}


	public void setSuperuser(boolean superuser) {
		this.superuser = superuser;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", usergroup=" + usergroup + ", active="
				+ active + ", approved=" + approved + ", superuser="
				+ superuser + ", username=" + username + ", password="
				+ password + ", registration=" + registration + ", name="
				+ name + ", surname=" + surname + ", email=" + email
				+ ", JMBG=" + JMBG + ", profileImage=" + profileImage
				+ ", playlists=" + playlists + "]";
	}
	
	
}
