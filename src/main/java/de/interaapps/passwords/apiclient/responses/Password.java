package de.interaapps.passwords.apiclient.responses;

import de.interaapps.passwords.apiclient.PasswordsAPI;
import de.interaapps.passwords.apiclient.helper.EncryptionHelper;

import javax.crypto.BadPaddingException;
import java.sql.Timestamp;

public class Password {
    public int id;
    public int userId;
    public String name;
    public String username;
    public String password;
    public String website;
    public String description;
    public String totp;
    public int folder;
    Timestamp createdAt;
    Timestamp updatedAt;


    private transient String masterPassword;
    private transient PasswordsAPI passwordsAPI;

    public void decrypt(String masterPassword, PasswordsAPI passwordsAPI){
        System.out.println(masterPassword);
        try {
            name = EncryptionHelper.decrypt(masterPassword, name);
            username = EncryptionHelper.decrypt(masterPassword, username);
            password = EncryptionHelper.decrypt(masterPassword, password);
            website = EncryptionHelper.decrypt(masterPassword, website);
            description = EncryptionHelper.decrypt(masterPassword, description);

            if (totp != null)
                totp = EncryptionHelper.decrypt(masterPassword, totp);
            System.out.println(id+" | "+name+" | "+password);
            this.masterPassword = masterPassword;
            this.passwordsAPI = passwordsAPI;
        } catch (BadPaddingException e) {
            System.out.println(id+" | err");
        }
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotp() {
        return totp;
    }

    public void setTotp(String totp) {
        this.totp = totp;
    }

    public int getFolder() {
        return folder;
    }

    public void setFolder(int folder) {
        this.folder = folder;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
