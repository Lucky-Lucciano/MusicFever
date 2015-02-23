package net.etfbl.musicfever.utils;

import javax.xml.rpc.ServiceException;

import net.etfbl.artistdb.Artists;
import net.etfbl.artistdb.ArtistsServiceLocator;

public class MainTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArtistsServiceLocator loc = new ArtistsServiceLocator();
		try {
			Artists art = loc.getArtists();
			
			art.getMatchingArtists("ni");
			
			
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		
		
	}

}
