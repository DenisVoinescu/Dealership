public class MyException {
    public static void userDoesNotExist() throws Exception {
        try {
            throw new Exception("User doesn't exist! ");
        } catch (Exception e) {
            System.out.println("Encountered an exception: " + e.getMessage());
        }
    }

    public static void usernameInvalid() {
        try {
            throw new Exception("Username is not valid! ");
        } catch (Exception e) {
            System.out.println("Encountered an exception: " + e.getMessage());
        }
    }

    public static void emailInvalid() {
        try {
            throw new Exception("E-Mail is not valid! ");
        } catch (Exception e) {
            System.out.println("Encountered an exception: " + e.getMessage());
        }
    }

    public static void passwordInvalid() {
        try {
            throw new Exception("Password is not valid! ");
        } catch (Exception e) {
            System.out.println("Encountered an exception: " + e.getMessage());
        }
    }

    public static void cardDetailsInvalid() {
        try {
            throw new Exception("Card details are not valid! ");
        } catch (Exception e) {
            System.out.println("Encountered an exception: " + e.getMessage());
        }
    }

    public static void passwordsDontMatch() {
        try {
            throw new Exception("The passwords don't match! ");
        } catch (Exception e) {
            System.out.println("Encountered an exception: " + e.getMessage());
        }
    }
}

