
package hotelregistration.model;

public class Customers {
    
    String name;
    String fname;
    String address;
    String phone;
    String createdAt;

    public Customers(String name, String fname, String address, String phone, String createdAt) {
        this.name = name;
        this.fname = fname;
        this.address = address;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
}
