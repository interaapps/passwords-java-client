package de.interaapps.passwords.apiclient.responses;

import java.sql.Timestamp;

public class UserSession {
    public int id;

    public String key;

    public int userId;

    public String userKey;

    Timestamp createdAt;

    Timestamp updatedAt;
}
