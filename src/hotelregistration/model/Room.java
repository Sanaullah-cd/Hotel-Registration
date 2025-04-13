
package hotelregistration.model;

public class Room {
    
    String roomNo;
    String roomName;
    String category;
    String price;
    String createdAt;

    public Room(String roomNo, String roomName, String category, String price, String createdAt) {
        this.roomNo = roomNo;
        this.roomName = roomName;
        this.category = category;
        this.price = price;
        this.createdAt = createdAt;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    
    
}
