package source.api;

import source.model.Customer;
import source.model.IRoom;
import source.model.Reservation;
import source.service.CustomerService;
import source.service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class HotelResource {
    private static final HotelResource INSTANCE = new HotelResource();
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    public static HotelResource getInstance() {
        return INSTANCE;
    }

    private HotelResource() {}

    public void createACustomer(String firstName, String lastName, String email) {
        customerService.addCustomer(firstName, lastName, email);
    }

    public Reservation bookRoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        return reservationService.reserveARoom(customer, room, checkIn, checkOut);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public Collection<IRoom> findAvailableRooms(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail);
        if (customer != null) {
            return reservationService.getCustomersReservation(customer);
        }
        return null;
    }

    // Added for Step 6: Recommended dates
    public Map<String, Collection<IRoom>> findRecommendedDates(Date checkIn, Date checkOut) {
        return reservationService.findRecommendedDates(checkIn, checkOut);
    }

    @Override
    public String toString() {
        return "HotelResource [Customers: " + customerService.getAllCustomers().size() +
               ", Available Rooms: " + findAvailableRooms(new Date(), new Date()).size() + "]";
    }
}
