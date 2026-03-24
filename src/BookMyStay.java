import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking Processor (Thread-Safe)
class ConcurrentBookingProcessor {

    private Map<String, Integer> inventory;
    private Map<String, Integer> roomCounter;

    public ConcurrentBookingProcessor(Map<String, Integer> inventory) {
        this.inventory = inventory;
        this.roomCounter = new HashMap<>();

        // Initialize counters
        for (String key : inventory.keySet()) {
            roomCounter.put(key, 1);
        }
    }

    // Critical Section (Thread-Safe)
    public synchronized void processBooking(BookingRequest request) {

        String type = request.roomType;

        if (!inventory.containsKey(type)) {
            System.out.println("Invalid room type for " + request.guestName);
            return;
        }

        int available = inventory.get(type);

        if (available > 0) {

            // Allocate room ID
            int roomNum = roomCounter.get(type);
            String roomId = type + "-" + roomNum;

            roomCounter.put(type, roomNum + 1);

            // Update inventory safely
            inventory.put(type, available - 1);

            System.out.println("Booking confirmed for Guest: "
                    + request.guestName + ", Room ID: " + roomId);

        } else {
            System.out.println("No rooms available for " + request.guestName);
        }
    }
}

// Thread class
class BookingThread extends Thread {

    private ConcurrentBookingProcessor processor;
    private BookingRequest request;

    public BookingThread(ConcurrentBookingProcessor processor, BookingRequest request) {
        this.processor = processor;
        this.request = request;
    }

    @Override
    public void run() {
        processor.processBooking(request);
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Concurrent Booking Simulation ===");

        // Shared Inventory
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);

        ConcurrentBookingProcessor processor =
                new ConcurrentBookingProcessor(inventory);

        // Booking Requests (Simultaneous)
        List<BookingThread> threads = new ArrayList<>();

        threads.add(new BookingThread(processor, new BookingRequest("Abhi", "Single")));
        threads.add(new BookingThread(processor, new BookingRequest("Vamathi", "Double")));
        threads.add(new BookingThread(processor, new BookingRequest("Kural", "Suite")));
        threads.add(new BookingThread(processor, new BookingRequest("Subha", "Single")));

        // Start threads
        for (Thread t : threads) {
            t.start();
        }

        // Wait for all to finish
        for (Thread t : threads) {
            t.join();
        }

        // Final Inventory
        System.out.println("\nRemaining Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }
    }
}