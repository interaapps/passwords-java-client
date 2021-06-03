package de.interaapps.passwords.apiclient.responses;

import java.sql.Timestamp;

public class Key {

    public int id;

    public String name;

    public String key;

    public int userId;

    public String type; // Using String because ORM is broken

    public Timestamp createdAt;

    public Timestamp updatedAt;

    public enum KeyType {
        RECOVERY,
        MASTER_PASSWORD,
        FOLDER;
    }
}
