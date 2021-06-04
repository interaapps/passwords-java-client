package de.interaapps.passwords.apiclient.responses;


import de.interaapps.passwords.apiclient.PasswordsAPI;
import de.interaapps.passwords.apiclient.helper.EncryptionHelper;

import javax.crypto.BadPaddingException;
import java.sql.Timestamp;

public class Folder {
    private int id;
    private String name = "";
    private String color = "#FF4343";
    private int parentId;
    private Timestamp createdAt;
    private Timestamp updatedAt;



    private transient String masterPassword;
    private transient PasswordsAPI passwordsAPI;

    public void decrypt(String password, PasswordsAPI passwordsAPI){
        masterPassword = password;
        this.passwordsAPI = passwordsAPI;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public int getParentId() {
        return parentId;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }
}
