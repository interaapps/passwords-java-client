package de.interaapps.passwords.apiclient.responses;

import java.sql.Timestamp;


public class Note {

    public int id;
    public String title;

    public String content;

    public int userId;

    Timestamp createdAt;

    Timestamp updatedAt;

}
