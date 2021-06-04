package de.interaapps.passwords.apiclient.responses;


import de.interaapps.passwords.apiclient.PasswordsAPI;
import de.interaapps.passwords.apiclient.helper.EncryptionHelper;
import de.interaapps.passwords.apiclient.requests.SaveNoteRequest;
import de.interaapps.passwords.apiclient.requests.SavePasswordRequest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FetchResponse {
    private User user;
    private PasswordListResponse passwords;
    private List<Key> keys;
    private List<Note> notes;
    private Map<String, String> decryptedKeys;

    private transient PasswordsAPI passwordsAPI;

    public void setPasswordsAPI(PasswordsAPI passwordsAPI) {
        this.passwordsAPI = passwordsAPI;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public PasswordListResponse getPasswords() {
        return passwords;
    }

    public User getUser() {
        return user;
    }

    public void setDecryptedKeys(Map<String, String> decryptedKeys) {
        this.decryptedKeys = decryptedKeys;
    }

    public boolean savePassword(Password password){
        String key = decryptedKeys.get("APP.MASTER");
        if (password.getFolder() != 0)
            key = decryptedKeys.get("FOLDER:" + password.getFolder());
        SuccessResponse response = passwordsAPI.put("/password").jsonBody(new SavePasswordRequest(password, key)).object(SuccessResponse.class);
        if (password.getId() == 0)
            addPasswordRec(passwords, password);
        password.setId((int) (double) response.extra.get("id"));
        return response.success;
    }

    public boolean deletePassword(int id){
        return passwordsAPI.deletePassword(id);
    }

    public boolean saveFolder(Folder folder){
        SuccessResponse response = passwordsAPI.put("/folder", folder).object(SuccessResponse.class);
        boolean keyCreated = false;
        try {
            String key = randomString(50);
            int id = (int) (double) response.extra.get("id");
            keyCreated = passwordsAPI.createKey(EncryptionHelper.encrypt(decryptedKeys.get("APP.MASTER"), key), "FOLDER:"+id, Key.KeyType.FOLDER);

            decryptedKeys.put("FOLDER:"+id, key);
            if (folder.getId() == 0)
                addFolderRec(passwords, folder);
            folder.setId(id);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return response.success && keyCreated;
    }

    public boolean deleteFolder(int id){
        return passwordsAPI.deleteFolder(id);
    }

    public boolean deleteFolder(Folder folder){
        return passwordsAPI.deleteFolder(folder.getId());
    }

    public boolean saveNote(Note note){
        SuccessResponse response = passwordsAPI.put("/note").jsonBody(new SaveNoteRequest(note, decryptedKeys.get("APP.MASTER"))).object(SuccessResponse.class);
        if (note.getId() == 0)
            notes.add(0, note);
        note.setId((int) (double) response.extra.get("id"));
        return response.success;
    }

    public boolean deleteNote(Note note){
        return passwordsAPI.deleteNote(note.getId());
    }

    private void addFolderRec(PasswordListResponse passwords, Folder folder){
        if (passwords.getParent() == -1) {
            PasswordListResponse passwordListResponse1 = new PasswordListResponse(folder, 0);
            passwords.getFolders().add(passwordListResponse1);
        }
        passwords.getFolders().forEach(passwordListResponse -> {
            if (passwordListResponse.getFolder() != null && folder.getParentId() == passwordListResponse.getFolder().getId()) {
                PasswordListResponse passwordListResponse1 = new PasswordListResponse(folder, passwordListResponse.getFolder().getId());
                passwordListResponse.getFolders().add(passwordListResponse1);
            }
        });
    }

    private void addPasswordRec(PasswordListResponse passwords, Password password){
        if (passwords.getParent() == -1) {
            passwords.getPasswords().add(password);
        }
        passwords.getFolders().forEach(passwordListResponse -> {
            if (passwordListResponse.getFolder() != null && password.getFolder() == passwordListResponse.getFolder().getId()) {
                passwordListResponse.getPasswords().add(password);
            }
        });
    }

    private String randomString(int count){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYTabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; count > i; i++) {
            stringBuilder.append(chars[random.nextInt(chars.length-1)]);
        }

        return stringBuilder.toString();
    }

}
