package de.interaapps.passwords.apiclient;

import de.interaapps.passwords.apiclient.exceptions.PasswordIncorrectException;
import de.interaapps.passwords.apiclient.helper.EncryptionHelper;
import de.interaapps.passwords.apiclient.responses.*;
import org.javawebstack.httpclient.HTTPClient;

import javax.crypto.BadPaddingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PasswordsAPI extends HTTPClient {

    public PasswordsAPI(String apiKey, String server){
        setBaseUrl(server);
        header("x-key", apiKey);
    }

    public PasswordsAPI(String apiKey){
        this(apiKey, "https://api.passwords.interaapps.de");
    }

    public PasswordsAPI(){
        this(null);
    }

    public FetchResponse fetch(String password){
        FetchResponse fetchResponse = get("/fetch").object(FetchResponse.class);
        fetchResponse.setPasswordsAPI(this);

        Map<String, String> keys = new HashMap<>();
        AtomicReference<String> masterKey = new AtomicReference<>("");
        fetchResponse.getKeys().forEach(key -> {
            if (key.type.equals(Key.KeyType.MASTER_PASSWORD)) {
                try {
                    masterKey.set(EncryptionHelper.decrypt(password, key.key));
                } catch (BadPaddingException e) {}
            }
        });
        keys.put("APP.MASTER", masterKey.get());
        fetchResponse.getKeys().forEach(key -> {
            if (!key.type.equals(Key.KeyType.MASTER_PASSWORD)) {
                try {
                    System.out.println("SETTING: "+key.name);
                    keys.put(key.name, EncryptionHelper.decrypt(masterKey.get(), key.key));
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
            }
        });
        fetchResponse.setDecryptedKeys(keys);

        if (masterKey.get().equals(""))
            throw new PasswordIncorrectException();

        decryptPasswordList(fetchResponse.getPasswords(), keys);

        fetchResponse.getNotes().forEach(note -> {
            note.decrypt(masterKey.get(), this);
        });

        return fetchResponse;
    }

    private void decryptPasswordList(PasswordListResponse passwordListResponse, Map<String, String> keys){
        passwordListResponse.getPasswords().forEach(pw -> {
            boolean isNotRoot = passwordListResponse.getFolder() != null && passwordListResponse.getFolder().getParentId() != -1;
            String key = keys.get(isNotRoot ? "FOLDER:"+passwordListResponse.getFolder().getId() : "APP.MASTER");
            pw.decrypt(key, this);
        });
        passwordListResponse.getFolders().forEach(folder -> {
            decryptPasswordList(folder, keys);
        });
    }

    public boolean deleteFolder(int id) {
        return delete("/folder/"+id).object(SuccessResponse.class).success;
    }

    public boolean deletePassword(int id) {
        return delete("/password/"+id).object(SuccessResponse.class).success;
    }

    public boolean deleteNote(int id) {
        return delete("/note/"+id).object(SuccessResponse.class).success;
    }

    public boolean createKey(String key, String name, Key.KeyType type) {
        return put("/key", new Key(name, key, type)).object(SuccessResponse.class).success;
    }


    public String login(String iaAuthCode){
        return get("/auth/ia/login").query("userkey", iaAuthCode).execute().header("Location").split("authentication=")[1];
    }
}
