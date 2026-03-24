import java.io.*;
import java.util.*;

// Reservation (must be Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getRoomType() { return roomType; }
}

// Wrapper class for persistence
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> history;

    public SystemState(Map<String, Integer> inventory, List<Reservation> history) {
        this.inventory = inventory;
        this.history = history;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // Load state
    public SystemState load() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            return (SystemState) ois.readObject();

        } catch (Exception e) {
            System.out.println("Corrupted data. Starting fresh.");
            return null;
        }
    }
}

// Main Class
public class    BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== System Recovery ===");

        PersistenceService service = new PersistenceService();

        Map<String, Integer> inventory;
        List<Reservation> history;

        // Load previous state
        SystemState state = service.load();

        if (state != null) {
            inventory = state.inventory;
            history = state.history;
            System.out.println("System state restored successfully.");
        } else {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Single", 5);
            inventory.put("Double", 3);
            inventory.put("Suite", 2);

            history = new ArrayList<>();
        }

        // Display inventory
        System.out.println("\nCurrent Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }

        // Simulate adding booking
        history.add(new Reservation("R101", "Single"));

        // Save state before exit
        service.save(new SystemState(inventory, history));
    }
}