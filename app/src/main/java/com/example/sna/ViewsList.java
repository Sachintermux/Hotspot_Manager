package com.example.sna;

public class ViewsList {

    private String macAddress;
    private String ipAddress;

    public ViewsList( String macAddress, String ipAddress ) {
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress( String macAddress ) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress( String ipAddress ) {
        this.ipAddress = ipAddress;
    }
}
