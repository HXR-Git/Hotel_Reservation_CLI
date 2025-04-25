package source.service;

import source.model.Customer;
import source.model.IRoom;
import source.model.Reservation;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    private static final ReservationService INSTANCE = new ReservationService();
    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Set<Reservation> reservations = new HashSet<>();

    private ReservationService() {}

    public static ReservationService getInstance() {
        return INSTANCE;
    }

    public void addRoom(IRoom room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("Room number " + room.getRoomNumber() + " already exists");
        }
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        List<IRoom> bookedRooms = reservations.stream()
                .filter(res -> !(checkOut.before(res.getCheckInDate()) || checkIn.after(res.getCheckOutDate())))
                .map(Reservation::getRoom)
                .collect(Collectors.toList());
        return rooms.values().stream()
                .filter(room -> !bookedRooms.contains(room))
                .collect(Collectors.toList());
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.stream()
                .filter(res -> res.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    public Collection<Reservation> getAllReservations() {
        return reservations;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        Date currentDate = new Date(); // Today’s date (March 22, 2025)
        if (checkInDate.before(currentDate) || checkOutDate.before(currentDate)) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be in the past");
        }
        if (!checkInDate.before(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        if (!findRooms(checkInDate, checkOutDate).contains(room)) {
            throw new IllegalArgumentException("Room " + room.getRoomNumber() + " is not available for these dates");
        }
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public Map<String, Collection<IRoom>> findRecommendedDates(Date originalCheckIn, Date originalCheckOut) {
        Map<String, Collection<IRoom>> recommendations = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originalCheckIn);
        calendar.add(Calendar.DATE, 7);
        Date newCheckIn = calendar.getTime();
        calendar.setTime(originalCheckOut);
        calendar.add(Calendar.DATE, 7);
        Date newCheckOut = calendar.getTime();
        Collection<IRoom> availableRooms = findRooms(newCheckIn, newCheckOut);
        if (!availableRooms.isEmpty()) {
            String dateRange = new SimpleDateFormat("yyyy-MM-dd").format(newCheckIn) + " to " +
            new SimpleDateFormat("yyyy-MM-dd").format(newCheckOut);
            recommendations.put(dateRange, availableRooms);
        }
    return recommendations;
    }

    @Override
    public String toString() {
        return "ReservationService [Rooms: " + rooms.size() + ", Reservations: " + reservations.size() + "]";
    }
}
