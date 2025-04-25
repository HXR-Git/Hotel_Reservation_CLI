//it give information about room number,room price and type of room (single /double)s
package source.model;

public class Room implements IRoom {
    private final String roomNumber;
    private final Double price;
    private final RoomType type;

    public Room(String roomNumber, Double price, RoomType type) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Room Number");
        }

        if (price == null || price < 0) {
            throw new IllegalArgumentException("Invalid Room Price");
        }
        if (type == null) {
            throw new IllegalArgumentException("Room type cannot be  other than SINGLE and DOUBLE type");
        }
        this.roomNumber = roomNumber.trim();
        this.price = price;
        this.type = type;
    }

    public Double getRoomPrice() {
        return price;
    }


    public RoomType getRoomType() {
        return type;
    }

    public String getRoomNumber() {
        return roomNumber;
    }


    @Override
    public String toString() {
        return "Room Number: " + roomNumber + ", Price: " + price + ", Type: " + type;
    }
}
