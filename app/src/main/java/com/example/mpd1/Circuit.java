package com.example.mpd1;
//HAYLEY_BRIDGES_S2333704
public class Circuit {

    private String circuitId;
    private String circuitName;
    private String latitude;
    private  String longitude;

    public Circuit(){}

    public String getCircuitId() { return circuitId;}
    public void setCircuitId(String circuitId) {this.circuitId = circuitId;}

    public String getCircuitName() { return circuitName;}
    public void setCircuitName(String circuitName) {this.circuitName = circuitName;}

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
