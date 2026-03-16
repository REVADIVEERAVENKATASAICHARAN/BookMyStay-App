import java.util.HashMap;
import java.util.Map;


abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int beds, int size, double price) {
        numberOfBeds = beds;
        squareFeet = size;
        pricePerNight = price;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}


class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 250, 1500);
    }
}


class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 400, 2500);
    }
}


class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 750, 5000);
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


public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Hotel Room Inventory Status\n");

        RoomInventory inventory = new RoomInventory();
        Map<String, Integer> currentStatus = inventory.getRoomAvailability();

        // Single Room
        Room singleRoom = new SingleRoom();
        System.out.println("Single Room:");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + currentStatus.get("Single Room") + "\n");

        // Double Room
        Room doubleRoom = new DoubleRoom();
        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + currentStatus.get("Double Room") + "\n");

        // Suite Room
        Room suiteRoom = new SuiteRoom();
        System.out.println("Suite Room:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + currentStatus.get("Suite Room"));
    }
}