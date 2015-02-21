package net.etfbl.musicfever.dto;

public class Genre {
	private int id;
	private String name;
	private boolean primary;
	private boolean active;
	
	
	public Genre() {
		
	}
	
	public Genre(int id, String name, boolean primary, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.primary = primary;
		this.active = active;
	}
	
	public Genre(int id, String name, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isPrimary() {
		return primary;
	}
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
