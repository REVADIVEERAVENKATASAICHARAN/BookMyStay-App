import java.util.*;


class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}


class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}


class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {

        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();

        assignedRoomsByType.put("Single", new HashSet<>());
        assignedRoomsByType.put("Double", new HashSet<>());
        assignedRoomsByType.put("Suite", new HashSet<>());
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        String inventoryKey = type + " Room";

        if (availability.getOrDefault(inventoryKey, 0) > 0) {

            String roomId = generateRoomId(type);

            allocatedRoomIds.add(roomId);
            assignedRoomsByType.get(type).add(roomId);

            int newCount = availability.get(inventoryKey) - 1;
            inventory.updateAvailability(inventoryKey, newCount);

            System.out.println("Booking confirmed for Guest: "
                    + reservation.getGuestName()
                    + ", Room ID: "
                    + roomId);
        } else {

            System.out.println("Booking failed for Guest: "
                    + reservation.getGuestName()
                    + ". No "
                    + type
                    + " rooms available.");
        }
    }

    private String generateRoomId(String roomType) {
        int nextId = assignedRoomsByType.get(roomType).size() + 1;
        return roomType + "-" + nextId;
    }
}


public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Simulate booking requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Process queue
        while (queue.hasPendingRequests()) {

            Reservation request = queue.getNextRequest();
            allocationService.allocateRoom(request, inventory);
        }
    }
}