package com.cloud.models;

public class Position {

    private double lat;
    private double lon;

    public Position(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Position() {
        lat = 0;
        lon = 0;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"lat\":")
                .append(lat)
                .append(",\"lon\":")
                .append(lon)
                .append("}");
        return builder.toString();
    }
}
