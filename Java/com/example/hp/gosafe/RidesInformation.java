package com.example.hp.gosafe;

public class RidesInformation {

    String pickup;
    String drop;
    String timing;
    String  categ;
    String user;
    String date;

    public RidesInformation() {
    }



    public RidesInformation(String user ,String pickup, String drop, String timing , String categ , String date) {
        this.user = user;
        this.pickup = pickup;
        this.drop = drop;
        this.timing = timing;
        this.categ = categ;
        this.date=date;

    }

    public String getUser() {
        return user;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDrop() {
        return drop;
    }

    public String getTiming() {
        return timing;
    }

    public String getCateg() {
        return categ;
    }

    public String getDate() {
        return date;
    }
}
