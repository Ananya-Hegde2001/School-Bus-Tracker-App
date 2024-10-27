package com.example.bustracker1;

public class latlon {
    Double latitude,longitude;

    public latlon() {

    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {this.longitude = longitude;
    }

    public latlon(Double latitude, Double longitude){
       this.latitude = latitude;
       this.longitude = longitude;
   }
}
