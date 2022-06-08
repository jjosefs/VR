package com.mobileapplication.vrmain;

public class GetDirections {
    private String yourlocation;
    private String yourdestination;
    private static final String mAPI = "AIzaSyA8rDRqEGBrZEAvtTyUjNuE9wAgDoR6nm0";


    GetDirections(String yourlocation, String yourdestination){
        this.yourlocation = yourlocation;
        this.yourdestination = yourdestination;
    }

    public String mhttpBuild(){
        return "https://maps.googleapis.com/maps/api/directions/json?origin=" + yourlocation + "&destination=" +yourdestination + "&key=" + mAPI;
        //return "https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood&key=AIzaSyA8rDRqEGBrZEAvtTyUjNuE9wAgDoR6nm0";
    }

    /*public JSONObject mJSONBuild(String link){

    }*/
}
