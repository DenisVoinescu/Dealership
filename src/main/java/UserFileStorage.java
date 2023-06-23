import java.io.*;
import java.nio.file.*;

public class UserFileStorage {
    private static final String FILE_PATH = "user_info.txt";

    public static void storeLoggedInUsername(String username) {
        try {
            Files.write(Paths.get(FILE_PATH), username.getBytes());
            System.out.println("Username stored successfully.");
        } catch (IOException e) {
            System.err.println("Error storing username: " + e.getMessage());
        }
    }

    public static String retrieveLoggedInUsername() {
        try {
            byte[] usernameBytes = Files.readAllBytes(Paths.get(FILE_PATH));
            return new String(usernameBytes);
        } catch (IOException e) {
            System.err.println("Error retrieving username: " + e.getMessage());
        }
        return null;
    }
}
