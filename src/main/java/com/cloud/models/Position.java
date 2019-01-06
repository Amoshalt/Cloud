package com.cloud.models;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

public class Position {

    @GeoSpatialIndexed
    private double lat;
    @GeoSpatialIndexed
    private double lon;

    /**
     * Construct position from data
     * @param lat latitude
     * @param lon longitude
     */
    public Position(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Default constructor
     */
    Position() {
        lat = 0;
        lon = 0;
    }

    /**
     * Getter
     * @return the latitude
     */
    public double getLat() {
        return lat;
    }

    /**
     * Setter
     * @param lat latitude to set
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Getter
     * @return the longitude
     */
    public double getLon() {
        return lon;
    }

    /**
     * Setter
     * @param lon longitude to set
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Convert the position into a string
     * @return JSON representation of the position
     */
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
