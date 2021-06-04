package de.interaapps.passwords.apiclient.responses;

import de.interaapps.passwords.apiclient.PasswordsAPI;
import de.interaapps.passwords.apiclient.helper.EncryptionHelper;

import javax.crypto.BadPaddingException;
import java.sql.Timestamp;


public class Note {

    private int id;
    private String title;
    private String content;
    private int userId;
    Timestamp createdAt;

    Timestamp updatedAt;

    private transient String masterPassword;
    private transient PasswordsAPI passwordsAPI;

    public void decrypt(String masterPassword, PasswordsAPI passwordsAPI){
        try {
            title = EncryptionHelper.decrypt(masterPassword, title);
            content = EncryptionHelper.decrypt(masterPassword, content);

            this.masterPassword = masterPassword;
            this.passwordsAPI = passwordsAPI;
        } catch (BadPaddingException ignored) { }
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
