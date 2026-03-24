import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Class (remove if already exists separately)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int roomsBooked;

    public Reservation(String reservationId, String guestName, String roomType, int roomsBooked) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomsBooked = roomsBooked;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public int getRoomsBooked() { return roomsBooked; }

    public void setRoomType(String roomType) { this.roomType = roomType; }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Rooms Booked: " + roomsBooked;
    }
}

// Validator Class
class InvalidBookingValidator {

    private Map<String, Integer> inventory;

    public InvalidBookingValidator(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public void validate(Reservation reservation) throws InvalidBookingException {

        String inputType = reservation.getRoomType().toLowerCase();
        String matchedType = null;

        // Case-insensitive matching
        for (String key : inventory.keySet()) {
            if (key.toLowerCase().equals(inputType)) {
                matchedType = key;
                break;
            }
        }

        // Invalid room type
        if (matchedType == null) {
            throw new InvalidBookingException(
                    "Invalid room type. Allowed: " + inventory.keySet()
            );
        }

        // Normalize correct value
        reservation.setRoomType(matchedType);

        // Validate room count
        if (reservation.getRoomsBooked() <= 0) {
            throw new InvalidBookingException("Rooms booked must be greater than zero.");
        }

        // Validate availability
        int available = inventory.get(matchedType);
        if (reservation.getRoomsBooked() > available) {
            throw new InvalidBookingException("Not enough rooms available.");
        }
    }
}

// Booking Service
class BookingService {

    private Map<String, Integer> inventory;

    public BookingService(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation reservation) {
        int available = inventory.get(reservation.getRoomType());
        inventory.put(reservation.getRoomType(),
                available - reservation.getRoomsBooked());

        System.out.println("Booking confirmed: " + reservation);
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Inventory
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);

        InvalidBookingValidator validator = new InvalidBookingValidator(inventory);
        BookingService bookingService = new BookingService(inventory);

        System.out.println("=== Booking Validation ===");

        System.out.print("Enter guest name: ");
        String name = sc.nextLine();

        System.out.print("Enter room type (Standard/Deluxe/Suite): ");
        String roomType = sc.nextLine();

        System.out.print("Enter number of rooms: ");
        int rooms = sc.nextInt();

        Reservation reservation = new Reservation("RES301", name, roomType, rooms);

        try {
            validator.validate(reservation);   // Validate first
            bookingService.confirmBooking(reservation); // Then book

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        sc.close();
    }
}