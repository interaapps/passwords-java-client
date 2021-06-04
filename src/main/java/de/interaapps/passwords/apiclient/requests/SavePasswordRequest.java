package de.interaapps.passwords.apiclient.requests;

import de.interaapps.passwords.apiclient.helper.EncryptionHelper;
import de.interaapps.passwords.apiclient.responses.Password;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SavePasswordRequest {

    public int id;
    public String name;
    public String username;
    public String password;
    public String website;
    public  String description;
    public  String totp;
    public int folder;

    public SavePasswordRequest(Password password, String masterPassword){
        try {
            id = password.getId();
            name = EncryptionHelper.encrypt(masterPassword, password.getName());
            username = EncryptionHelper.encrypt(masterPassword, password.getUsername());
            this.password = EncryptionHelper.encrypt(masterPassword, password.getPassword());
            website = EncryptionHelper.encrypt(masterPassword, password.getWebsite());
            description = EncryptionHelper.encrypt(masterPassword, password.getDescription());
            totp = EncryptionHelper.encrypt(masterPassword, password.getTotp());
            folder = password.getFolder();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
