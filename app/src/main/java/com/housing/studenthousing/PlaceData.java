package com.housing.studenthousing;

/**
 * Created by Ishan on 7/27/2016.
 */
public class PlaceData {
    CharSequence placeName;
    double lat, lng;
    String id;

    public CharSequence getPlaceName() {
        return placeName;
    }

    public void setPlaceName(CharSequence placeName) {
        this.placeName = placeName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
