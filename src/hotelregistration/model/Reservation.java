
package hotelregistration.model;

public class Reservation {
    
    String customer;
    String room_no;
    String reserved_date;
    int day_duration;
    double price;
    double total;
    double payment;
    double remine;
    String created;

    public Reservation(String customer, String room_no, String reserved_date, int day_duration, double price, double total, double payment, double remine, String created) {
        this.customer = customer;
        this.room_no = room_no;
        this.reserved_date = reserved_date;
        this.day_duration = day_duration;
        this.price = price;
        this.total = total;
        this.payment = payment;
        this.remine = remine;
        this.created = created;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getReserved_date() {
        return reserved_date;
    }

    public void setReserved_date(String reserved_date) {
        this.reserved_date = reserved_date;
    }

    public int getDay_duration() {
        return day_duration;
    }

    public void setDay_duration(int day_duration) {
        this.day_duration = day_duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getRemine() {
        return remine;
    }

    public void setRemine(double remine) {
        this.remine = remine;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
    
    
}
