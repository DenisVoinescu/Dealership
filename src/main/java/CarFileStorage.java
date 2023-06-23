import java.io.*;

public class CarFileStorage {
    private static final String FILE_PATH = "car_info.txt";

    public static void storeCarId(int carId) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.println(carId);
            System.out.println("Car ID stored successfully.");
        } catch (IOException e) {
            System.err.println("Error storing Car ID: " + e.getMessage());
        }
    }

    public static int retrieveCarId() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String carIdString = reader.readLine();
            System.out.println("Retrieved Car ID: " + carIdString);
            return Integer.parseInt(carIdString);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error retrieving Car ID: " + e.getMessage());
        }
        return -1; // Return a default value if retrieval
    }
}