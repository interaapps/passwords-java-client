package de.interaapps.passwords.apiclient.responses;


import de.interaapps.passwords.apiclient.PasswordsAPI;
import de.interaapps.passwords.apiclient.helper.EncryptionHelper;

import javax.crypto.BadPaddingException;
import java.sql.Timestamp;

public class Folder {
    public int id;

    public String name;

    public String color;

    public int parentId;

    Timestamp createdAt;

    Timestamp updatedAt;



    private transient String masterPassword;
    private transient PasswordsAPI passwordsAPI;

    public void decrypt(String password, PasswordsAPI passwordsAPI){
        masterPassword = password;
        this.passwordsAPI = passwordsAPI;
    }

}
