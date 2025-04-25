//class provides the admin menu for managing customers, rooms, and reservations, as well as adding new rooms
package source.ui;

import source.api.AdminResource;
import source.model.IRoom;
import source.model.Room;
import source.model.RoomType;
import java.util.Scanner;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void display() {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.print("Please select an option: ");

            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> System.out.println(adminResource.getAllCustomers());
                    case 2 -> System.out.println(adminResource.getAllRooms());
                    case 3 -> adminResource.displayAllReservations();
                    case 4 -> addRoom();
                    case 5 -> { return; }
                    default -> System.out.println("Invalid");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    private static void addRoom() {
        try {
            System.out.print("Enter room number: ");
            String roomNumber = scanner.nextLine();
            System.out.print("Enter price");
            double price = scanner.nextDouble();
            System.out.print("Enter room type (1 for SINGLE, 2 for DOUBLE): ");
            int type = scanner.nextInt();
            scanner.nextLine();

            RoomType roomType;
        if (type== 1) {
            roomType = RoomType.SINGLE;
        } else if (type == 2) {
            roomType = RoomType.DOUBLE;
        } else {
            throw new IllegalArgumentException("Invalid room type");
        }
        IRoom room = new Room(roomNumber, price, roomType);

            adminResource.addRoom(room);
            System.out.println("Room added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding room: " + e.getMessage());
        }catch (Exception e) {
            System.out.println("Invalid input");
        }
    }
}
