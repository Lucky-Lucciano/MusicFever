/**
 * ArtistsService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.etfbl.artistdb;

public interface ArtistsService extends javax.xml.rpc.Service {
    public java.lang.String getArtistsAddress();

    public net.etfbl.artistdb.Artists getArtists() throws javax.xml.rpc.ServiceException;

    public net.etfbl.artistdb.Artists getArtists(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
