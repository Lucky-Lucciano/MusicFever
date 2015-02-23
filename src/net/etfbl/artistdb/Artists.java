/**
 * Artists.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.etfbl.artistdb;

public interface Artists extends java.rmi.Remote {
    public java.lang.String[] getMatchingArtists(java.lang.String art) throws java.rmi.RemoteException;
    public boolean addArtist(java.lang.String art) throws java.rmi.RemoteException;
}
