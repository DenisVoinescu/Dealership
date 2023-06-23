import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentSceneControllerTest {
    PaymentSceneController scene = new PaymentSceneController();

    @Test
    public void testCardName() {
        // Valid card name
        assertTrue("denis".matches(scene.cardNameRegex));
        // Invalid card name
        assertFalse("de".matches(scene.cardNameRegex));
        assertFalse("denis123".matches(scene.cardNameRegex));
    }
    @Test
    public void testCardNumber() {
        // Valid card number
        assertTrue("1234123412341234".matches(scene.cardNumberRegex));
        // Invalid card number
        assertFalse("123456789123456".matches(scene.cardNumberRegex));
    }
    @Test
    public void testCardExpiryDate() {
        // Valid expiry date
        assertTrue("03/24".matches(scene.cardDateRegex));
        // Invalid expiry date
        assertFalse("0324".matches(scene.cardDateRegex));
    }
    @Test
    public void testCardCVV() {
        // Valid card CVV
        assertTrue("332".matches(scene.CVVRegex));
        // Invalid card CVV
        assertFalse("21".matches(scene.CVVRegex));
        assertFalse("3444".matches(scene.CVVRegex));
    }


}