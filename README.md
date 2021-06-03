## Passwords Java APIClient

```java
import de.interaapps.passwords.apiclient.PasswordsAPI;

class Test {
    public static void main(String[] args) {
        PasswordsAPI passwordsAPI = new PasswordsAPI("API-KEY");
        passwordsAPI.fetch("master-password").passwords.getPasswords().forEach(password -> {
            System.out.println(password.getName());
        });
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
