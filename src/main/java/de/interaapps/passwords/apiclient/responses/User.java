package de.interaapps.passwords.apiclient.responses;

import com.google.gson.annotations.SerializedName;

public class User {
    public boolean valid;
    public int id;
    @SerializedName("username")
    public String username;
    @SerializedName("profilepic")
    public String profilePicture;
    public String color;

}
