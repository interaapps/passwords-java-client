package de.interaapps.passwords.apiclient.requests;

import de.interaapps.passwords.apiclient.helper.EncryptionHelper;
import de.interaapps.passwords.apiclient.responses.Note;
import de.interaapps.passwords.apiclient.responses.Password;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SaveNoteRequest {

    public int id;
    public String title;
    public String content;

    public SaveNoteRequest(Note note, String masterPassword){
        try {
            id = note.getId();
            title = EncryptionHelper.encrypt(masterPassword, note.getTitle());
            content = EncryptionHelper.encrypt(masterPassword, note.getContent());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
