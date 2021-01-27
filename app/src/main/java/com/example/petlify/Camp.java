package com.example.petlify;

public class Camp {

    String place,date,time,lat,lng,name,image;

    public Camp(String place, String date, String time, String lat, String lng,String name,String image) {
        this.place = place;
        this.date = date;
        this.time = time;
        this.lat = lat;
        this.lng = lng;
        this.image=image;
        this.name=name;
    }

    public Camp() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}