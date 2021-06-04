package de.interaapps.passwords.apiclient.responses;

import java.sql.Timestamp;

public class Key {

    public int id;
    public String name;
    public String key;
    public int userId;
    public KeyType type; // Using String because ORM is broken
    public Timestamp createdAt;
    public Timestamp updatedAt;


    public Key(String name, String key, KeyType type) {
        this.name = name;
        this.key = key;
        this.type = type;
    }

    public enum KeyType {
        RECOVERY,
        MASTER_PASSWORD,
        FOLDER;
    }
}
