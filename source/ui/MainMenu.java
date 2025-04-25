//common for customer and admin

package source.ui;

import source.api.HotelResource;
import source.model.Customer;
import source.model.IRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to the Hotel Reservation System");
            System.out.println("======================================");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.print("Please select an option: ");

            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        findAndReserveRoom();
                        break;
                    case 2:
                        System.out.print("Enter your email: ");
                        String email = scanner.nextLine();
                        Collection<?> reservations = hotelResource.getCustomerReservations(email);
                        System.out.println(reservations != null && !reservations.isEmpty() ? reservations : "No reservations found.");
                        break;
                    case 3:
                        System.out.print("Enter your first name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Enter your last name: ");
                        String lastName = scanner.nextLine();
                        System.out.print("Enter your email: ");
                        email = scanner.nextLine();
                        hotelResource.createACustomer(firstName, lastName, email);
                        System.out.println("Account created successfully!");
                        break;
                    case 4:
                        AdminMenu.display();
                        break;
                    case 5:
                        System.out.println("Exiting");
                        System.exit(0);
                    default:
                        System.out.println("Invalid");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private static void findAndReserveRoom() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            Date currentDate = new Date();

            System.out.print("Enter check-in date (yyyy-MM-dd): ");
            Date checkIn = dateFormat.parse(scanner.nextLine());
            if (checkIn.before(currentDate)) {
                System.out.println("Check-in date cannot be in the past.");
                return;
            }

            System.out.print("Enter check-out date (yyyy-MM-dd): ");
            Date checkOut = dateFormat.parse(scanner.nextLine());
            if (checkOut.before(currentDate)) {
                System.out.println("Check-out date cannot be in the past.");
                return;
            }

            if (!checkIn.before(checkOut)) {
                System.out.println("Check-in date must be before check-out date.");
                return;
            }

            Collection<IRoom> availableRooms = hotelResource.findAvailableRooms(checkIn, checkOut);
            if (availableRooms.isEmpty()) {
                System.out.println("No available rooms for the selected dates.");
                Map<String, Collection<IRoom>> recommendedDates = hotelResource.findRecommendedDates(checkIn, checkOut);
                if (!recommendedDates.isEmpty()) {
                    System.out.println("Recommended alternative dates:");
                    for (Map.Entry<String, Collection<IRoom>> entry : recommendedDates.entrySet()) {
                        System.out.println("Dates: " + entry.getKey());
                        System.out.println("Available rooms: " + entry.getValue());
                    }
                 System.out.print("Would you like to book a room for these dates? (yes/no):");
                String response = scanner.nextLine().trim().toLowerCase();
                    if (response.equals("yes")) {
                                    String recommendedDateRange = recommendedDates.keySet().iterator().next();
                                     availableRooms = recommendedDates.get(recommendedDateRange);
                                      checkIn = dateFormat.parse(recommendedDateRange.split(" to ")[0]);
                                      checkOut = dateFormat.parse(recommendedDateRange.split(" to ")[1]);
                   } else {
                                    System.out.println("Cancelled.");
                                    return;
                    }
                    } else {
                            System.out.println("No rooms available ");
                return;
            }
        }


            System.out.println("Available rooms:");
            for (IRoom room : availableRooms) {
                System.out.println(room);
            }

            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            Customer customer = hotelResource.getCustomer(email);
            if (customer == null) {
                System.out.println("Customer not found. Please create an account first.");
                return;
            }

            System.out.print("Enter room number to reserve: ");
            String roomNumber = scanner.nextLine();
            IRoom room = hotelResource.getRoom(roomNumber);
            if (room == null || !availableRooms.contains(room)) {
                System.out.println("Invalid room number.");
                return;
            }

            hotelResource.bookRoom(customer, room, checkIn, checkOut);
            System.out.println("Room reserved successfully!");
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please use yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
