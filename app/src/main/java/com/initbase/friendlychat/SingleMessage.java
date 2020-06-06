package com.initbase.friendlychat;

import android.net.Uri;

public class SingleMessage {
    private String usersName;
    private String time;
    private String message;
//    private Uri image =null;

    public SingleMessage(){

    }
    public SingleMessage(String usersName, String message, String time){
        this.usersName = usersName;
        this.message = message;
        this.time = time;
    }

    public SingleMessage(String usersName, String message, Uri image, String time){
        this.usersName = usersName;
        this.message = message;
//        this.image = image;
        this.time =time;
    }

    public SingleMessage(String usersName, Uri image, String time){
        this.usersName = usersName;
//        this.image = image;
        this.time =time;
    }

    public String getUsersName() {
        return usersName;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

//    public Uri getImage() {
//        return image;
//    }
}