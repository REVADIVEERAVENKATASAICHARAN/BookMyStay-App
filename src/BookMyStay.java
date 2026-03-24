import java.util.*;

// Reservation Class
class Reservation {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getRoomType() { return roomType; }
}

// Booking History (only confirmed bookings)
class BookingHistory {
    private Map<String, Reservation> confirmedBookings = new HashMap<>();

    public void addReservation(Reservation r) {
        confirmedBookings.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return confirmedBookings.get(id);
    }

    public void removeReservation(String id) {
        confirmedBookings.remove(id);
    }

    public boolean exists(String id) {
        return confirmedBookings.containsKey(id);
    }
}

// Cancellation Service
class CancellationService {

    private Map<String, Integer> inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack;

    public CancellationService(Map<String, Integer> inventory,
                               BookingHistory history,
                               Stack<String> rollbackStack) {
        this.inventory = inventory;
        this.history = history;
        this.rollbackStack = rollbackStack;
    }

    public void cancelBooking(String reservationId) {

        // Validate existence
        if (!history.exists(reservationId)) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        // Get reservation
        Reservation r = history.getReservation(reservationId);

        // Push to rollback stack (LIFO)
        rollbackStack.push(reservationId);

        // Restore inventory
        String roomType = r.getRoomType();
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);

        // Remove from history
        history.removeReservation(reservationId);

        System.out.println("\nBooking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        for (int i = rollbackStack.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + rollbackStack.get(i));
        }
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("=== Booking Cancellation ===");

        // Inventory setup
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);

        // Booking history
        BookingHistory history = new BookingHistory();

        // Stack for rollback
        Stack<String> rollbackStack = new Stack<>();

        // Add sample confirmed booking
        Reservation r1 = new Reservation("Single-1", "Single");
        history.addReservation(r1);

        // Cancellation service
        CancellationService service =
                new CancellationService(inventory, history, rollbackStack);

        // Cancel booking
        service.cancelBooking("Single-1");

        // Show rollback history
        service.showRollbackHistory();

        // Show updated inventory
        System.out.println("\nUpdated Single Room Availability: " + inventory.get("Single"));
    }
}