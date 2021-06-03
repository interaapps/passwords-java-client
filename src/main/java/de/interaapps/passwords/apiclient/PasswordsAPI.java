package de.interaapps.passwords.apiclient;

import de.interaapps.passwords.apiclient.helper.EncryptionHelper;
import de.interaapps.passwords.apiclient.responses.FetchResponse;
import de.interaapps.passwords.apiclient.responses.PasswordListResponse;
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

        Map<String, String> keys = new HashMap<>();
        AtomicReference<String> masterKey = new AtomicReference<>("");
        fetchResponse.keys.forEach(key -> {
            if (key.type.equals("MASTER_PASSWORD")) {
                try {
                    masterKey.set(EncryptionHelper.decrypt(password, key.key));
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
            }
        });
        keys.put("APP.MASTER", masterKey.get());
        fetchResponse.keys.forEach(key -> {
            if (!key.type.equals("MASTER_PASSWORD")) {
                try {
                    keys.put(key.name, EncryptionHelper.decrypt(masterKey.get(), key.key));
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
            }
        });

        decryptPasswordList(fetchResponse.passwords, keys);

        return fetchResponse;
    }

    private void decryptPasswordList(PasswordListResponse passwordListResponse, Map<String, String> keys){
        passwordListResponse.getPasswords().forEach(pw -> {
            boolean isNotRoot = passwordListResponse.getFolder() != null && passwordListResponse.getFolder().parentId != -1;
            String key = keys.get(isNotRoot ? "FOLDER:"+passwordListResponse.getFolder().id : "APP.MASTER");
            pw.decrypt(key, this);
        });
        passwordListResponse.getFolders().forEach(folder -> {
            decryptPasswordList(folder, keys);
        });
    }


    public String login(String iaAuthCode){
        return get("/api/authentication/login").query("userkey", iaAuthCode).execute().header("Location").split("key=")[1];
    }
}
