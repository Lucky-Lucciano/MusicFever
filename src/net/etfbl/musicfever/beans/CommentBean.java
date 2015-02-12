package net.etfbl.musicfever.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.etfbl.musicfever.dto.Comment;
import net.etfbl.musicfever.dto.Song;

@ManagedBean
@SessionScoped
public class CommentBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ArrayList<Comment> getComments(Song song) {
		ArrayList<Comment> rez = new ArrayList<Comment>();
		
		return rez;
	}
}
