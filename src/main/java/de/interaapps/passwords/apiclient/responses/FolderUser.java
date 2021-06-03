package de.interaapps.passwords.apiclient.responses;


import java.sql.Timestamp;

public class FolderUser {
    public int id;

    public int folderId;

    public int userId;


    public Type type;

    Timestamp createdAt;

    Timestamp updatedAt;

    public enum Type {
        USER,
        OWNER
    }
}
