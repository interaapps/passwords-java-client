## Passwords Java APIClient

```java
import de.interaapps.passwords.apiclient.PasswordsAPI;

class Test {
    public static void main(String[] args) {
        PasswordsAPI passwordsAPI = new PasswordsAPI("S-NB01-API-KEY");

        FetchResponse instance = passwordsAPI.fetch("PASSWORD");

        Folder folder = new Folder();
        folder.setName("Hey");
        folder.setColor("#547687");
        instance.saveFolder(folder);

        Password password = new Password();
        password.setFolder(folder.getId());
        password.setPassword("123456");
        password.setDescription("hey");
        password.setName("My Password");
        password.setUsername("My Name");
        password.setWebsite("https://accounts.interaapps.de");
        instance.savePassword(password);
        
        
        instance.deletePassword(password.getId());
        
        // Notes
        
        Note note = new Note();
        note.setTitle("Nice Title");
        note.setContent("Hello");
        instance.saveNote(note);
        
        instance.getNotes().forEach(notes -> {
            System.out.println(notes.getTitle());
        });
        
        instance.deleteNote(note.getId());
        
    }
}
```

## Installation
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

	<dependency>
	    <groupId>com.github.interaapps</groupId>
	    <artifactId>passwords-java-client</artifactId>
	    <version>1.0 <!-- TAG --></version>
	</dependency>
```
