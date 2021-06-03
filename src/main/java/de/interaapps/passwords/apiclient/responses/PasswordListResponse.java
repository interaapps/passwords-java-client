package de.interaapps.passwords.apiclient.responses;

import java.util.ArrayList;
import java.util.List;

public class PasswordListResponse {
    private List<Password> passwords;
    private List<PasswordListResponse> folders;
    private Folder folder;
    private String key = null;
    private int parent;

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
