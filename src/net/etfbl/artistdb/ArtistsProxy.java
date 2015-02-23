package net.etfbl.artistdb;

public class ArtistsProxy implements net.etfbl.artistdb.Artists {
  private String _endpoint = null;
  private net.etfbl.artistdb.Artists artists = null;
  
  public ArtistsProxy() {
    _initArtistsProxy();
  }
  
  public ArtistsProxy(String endpoint) {
    _endpoint = endpoint;
    _initArtistsProxy();
  }
  
  private void _initArtistsProxy() {
    try {
      artists = (new net.etfbl.artistdb.ArtistsServiceLocator()).getArtists();
      if (artists != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)artists)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)artists)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (artists != null)
      ((javax.xml.rpc.Stub)artists)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public net.etfbl.artistdb.Artists getArtists() {
    if (artists == null)
      _initArtistsProxy();
    return artists;
  }
  
  public java.lang.String[] getMatchingArtists(java.lang.String art) throws java.rmi.RemoteException{
    if (artists == null)
      _initArtistsProxy();
    return artists.getMatchingArtists(art);
  }
  
  public boolean addArtist(java.lang.String art) throws java.rmi.RemoteException{
    if (artists == null)
      _initArtistsProxy();
    return artists.addArtist(art);
  }
  
  
}