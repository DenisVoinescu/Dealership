import javafx.event.ActionEvent;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class RegisterSceneControllerTest {
    RegisterSceneController scene = new RegisterSceneController();

    private ActionEvent event;

    @Test
    public void testUsernames() {
        // Valid usernames
        assertTrue("testtttt".matches(scene.usernameRegex));
        assertTrue("denisdenis".matches(scene.usernameRegex));
        assertTrue("denis123".matches(scene.usernameRegex));
        // Invalid usernames
        assertFalse("ab".matches(scene.usernameRegex));
        assertFalse("user@name".matches(scene.usernameRegex));
        assertFalse("user name".matches(scene.usernameRegex));
        assertFalse("john".matches(scene.usernameRegex));
    }

    @Test
    public void testEmails() {
        // Valid emails
        assertTrue("denisvoinescu@yahoo.com".matches(scene.emailRegex));
        assertTrue("asdasdasd@hotmail.co.uk".matches(scene.emailRegex));
        // Invalid emails
        assertFalse("denisdenisdenis.asd.com".matches(scene.emailRegex));
        assertFalse("denisdenisdenis.com".matches(scene.emailRegex));
        assertFalse("asdasdasd@hotmail.co.uk@".matches(scene.emailRegex));
    }
    @Test
    public void testPasswords() {
        // Valid passwords
        assertTrue("5char".matches(scene.passwordRegex));
        assertTrue("123123".matches(scene.passwordRegex));
        assertTrue("@@@@@".matches(scene.passwordRegex));
        // Invalid passwords
        assertFalse("4chr".matches(scene.passwordRegex));
        assertFalse("123".matches(scene.passwordRegex));
        assertFalse("$$$$".matches(scene.passwordRegex));

    }
    @Test
    public void passwordsMatch() {
        // Valid passwords
        assertTrue(scene.passwordsMatch("asd","asd"));
        assertTrue(scene.passwordsMatch("11111","11111"));
        // Invalid passwords
        assertFalse(scene.passwordsMatch("11111@","11111"));
        assertFalse(scene.passwordsMatch("asd1","asd"));
    }
}