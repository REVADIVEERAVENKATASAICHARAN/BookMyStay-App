import java.util.*;

// Reservation class (basic structure)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Price: ₹" + price;
    }
}

// Booking History (stores confirmed reservations)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Reservation added to history: " + reservation.getReservationId());
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Booking Report Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("\n=== Booking History ===");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummaryReport(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getPrice();
        }

        System.out.println("\n=== Booking Summary Report ===");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        Reservation r1 = new Reservation("RES101", "Charan", "Deluxe", 3000);
        Reservation r2 = new Reservation("RES102", "Rahul", "Suite", 5000);
        Reservation r3 = new Reservation("RES103", "Anita", "Standard", 2000);

        // Add to history (CONFIRMED bookings)
        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);

        // Admin views booking history
        List<Reservation> allBookings = bookingHistory.getAllReservations();

        // Display all bookings
        reportService.displayAllBookings(allBookings);

        // Generate report
        reportService.generateSummaryReport(allBookings);
    }
}