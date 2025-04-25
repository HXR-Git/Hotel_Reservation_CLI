//interface defines the essential methods for a room, including getting the room number, price, and type
package source.model;

public interface IRoom {
    String getRoomNumber();
    RoomType getRoomType();
    Double getRoomPrice();

}
