package de.interaapps.passwords.apiclient.responses;


import java.util.List;

public class FetchResponse {
    public User user;
    public PasswordListResponse passwords;
    public List<Key> keys;
    public List<Note> notes;
}
