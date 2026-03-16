
abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    // Constructor
    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Method to display room information
    public void showRoomDetails() {
        System.out.println("Beds : " + beds);
        System.out.println("Room Size : " + size + " sqft");
        System.out.println("Price per night : " + price);
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


public class BookMyStay{

    public static void main(String[] args) {

        System.out.println("======= Hotel Room Initialization =======\n");

        // Single Room
        SingleRoom single = new SingleRoom();
        int singleAvailable = 5;

        System.out.println("Single Room Details");
        single.showRoomDetails();
        System.out.println("Available Rooms : " + singleAvailable + "\n");

        // Double Room
        DoubleRoom doubleRoom = new DoubleRoom();
        int doubleAvailable = 3;

        System.out.println("Double Room Details");
        doubleRoom.showRoomDetails();
        System.out.println("Available Rooms : " + doubleAvailable + "\n");

        // Suite Room
        SuiteRoom suite = new SuiteRoom();
        int suiteAvailable = 2;

        System.out.println("Suite Room Details");
        suite.showRoomDetails();
        System.out.println("Available Rooms : " + suiteAvailable);
    }
}