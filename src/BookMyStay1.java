import java.util.*;

// Add-On Service class
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<Service>> reservationServicesMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, Service service) {
        reservationServicesMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service added to reservation " + reservationId);
    }

    // Get all services for a reservation
    public List<Service> getServices(String reservationId) {
        return reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {
        List<Service> services = reservationServicesMap.get(reservationId);
        double total = 0;

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services for this reservation.");
            return;
        }

        System.out.println("Add-On Services for Reservation " + reservationId + ":");
        for (Service s : services) {
            System.out.println("- " + s);
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

// Main Class
public class  BookMyStay1{

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES123";

        // Creating services
        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa Access", 1500);
        Service airportPickup = new Service("Airport Pickup", 800);

        // Guest selects services
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);
        manager.addService(reservationId, airportPickup);

        // Display services
        manager.displayServices(reservationId);
    }
}
