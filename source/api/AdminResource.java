//admin related class provide methods for managing customers,rooms and reservations
package source.api;

import source.model.Customer;
import source.model.IRoom;
import source.service.CustomerService;
import source.service.ReservationService;
import java.util.Collection;

public class AdminResource {
    private static final AdminResource INSTANCE = new AdminResource();
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    public static AdminResource getInstance() {

        return INSTANCE;
    }

    public Collection<Customer> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    public void addRoom(IRoom room) {

        reservationService.addRoom(room);
    }

    public Customer getCustomer(String email) {

        return customerService.getCustomer(email);
    }

    public Collection<IRoom> getAllRooms() {

        return reservationService.getAllRooms();
    }

@Override
    public String toString() {
         return getAllCustomers().size() + "" + getAllRooms().size() + "" + reservationService.getAllReservations().size();
   }
    private AdminResource() {}

    public void displayAllReservations() {

        for (var res : reservationService.getAllReservations()) {
            System.out.println(res);
        }
    }
}
