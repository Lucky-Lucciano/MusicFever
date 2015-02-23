/**
 * ArtistsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.etfbl.artistdb;

public class ArtistsServiceLocator extends org.apache.axis.client.Service implements net.etfbl.artistdb.ArtistsService {

    public ArtistsServiceLocator() {
    }


    public ArtistsServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ArtistsServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Artists
    private java.lang.String Artists_address = "http://localhost:8080/ArtistDB/services/Artists";

    public java.lang.String getArtistsAddress() {
        return Artists_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ArtistsWSDDServiceName = "Artists";

    public java.lang.String getArtistsWSDDServiceName() {
        return ArtistsWSDDServiceName;
    }

    public void setArtistsWSDDServiceName(java.lang.String name) {
        ArtistsWSDDServiceName = name;
    }

    public net.etfbl.artistdb.Artists getArtists() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Artists_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getArtists(endpoint);
    }

    public net.etfbl.artistdb.Artists getArtists(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            net.etfbl.artistdb.ArtistsSoapBindingStub _stub = new net.etfbl.artistdb.ArtistsSoapBindingStub(portAddress, this);
            _stub.setPortName(getArtistsWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setArtistsEndpointAddress(java.lang.String address) {
        Artists_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (net.etfbl.artistdb.Artists.class.isAssignableFrom(serviceEndpointInterface)) {
                net.etfbl.artistdb.ArtistsSoapBindingStub _stub = new net.etfbl.artistdb.ArtistsSoapBindingStub(new java.net.URL(Artists_address), this);
                _stub.setPortName(getArtistsWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Artists".equals(inputPortName)) {
            return getArtists();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://artistdb.etfbl.net", "ArtistsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://artistdb.etfbl.net", "Artists"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Artists".equals(portName)) {
            setArtistsEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
