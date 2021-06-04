package de.interaapps.passwords.apiclient.responses;

import de.interaapps.passwords.apiclient.PasswordsAPI;

import java.util.ArrayList;
import java.util.List;

public class PasswordListResponse {
    private List<Password> passwords = new ArrayList<>();
    private List<PasswordListResponse> folders = new ArrayList<>();
    private Folder folder;
    private String key = null;
    private int parent;

    private transient PasswordsAPI passwordsAPI;

    public void setPasswordsAPI(PasswordsAPI passwordsAPI) {
        this.passwordsAPI = passwordsAPI;
    }

    public PasswordListResponse(){

    }

    public PasswordListResponse(Folder folder, int parent){
        this.folder = folder;
        this.parent = parent;
    }

    public boolean isFolder(){
        return folder != null;
    }

    public int getParent() {
        return parent;
    }

    public String getKey() {
        return key;
    }

    public List<Password> getPasswords() {
        return passwords;
    }

    public Folder getFolder() {
        return folder;
    }

    public List<PasswordListResponse> getFolders() {
        return folders;
    }
}
