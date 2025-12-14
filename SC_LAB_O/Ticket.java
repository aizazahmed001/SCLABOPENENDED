import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
    private static int ticketCounter = 1000;
    
    private String ticketId;
    private Station fromStation;
    private Station toStation;
    private String ticketType;
    private String passengerType;
    private int quantity;
    private double totalPrice;
    private Date bookingDate;
    private String customerUsername;
    
    public Ticket(Station from, Station to, String ticketType, String passengerType, 
                  int quantity, double totalPrice, String customerUsername) {
        this.ticketId = "TKT" + (++ticketCounter);
        this.fromStation = from;
        this.toStation = to;
        this.ticketType = ticketType;
        this.passengerType = passengerType;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.bookingDate = new Date();
        this.customerUsername = customerUsername;
    }
    
    public String getTicketId() { return ticketId; }
    public Station getFromStation() { return fromStation; }
    public Station getToStation() { return toStation; }
    public String getTicketType() { return ticketType; }
    public String getPassengerType() { return passengerType; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
    public Date getBookingDate() { return bookingDate; }
    public String getCustomerUsername() { return customerUsername; }
    
    public String getFormattedDate() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(bookingDate);
    }
    
    public String toString() {
        return "Ticket #" + ticketId + "\n" +
               "Route: " + fromStation.getName() + " -> " + toStation.getName() + "\n" +
               "Type: " + ticketType + " | Passenger: " + passengerType + "\n" +
               "Qty: " + quantity + " | Total: Rs. " + String.format("%.2f", totalPrice) + "\n" +
               "Booked: " + getFormattedDate();
    }
}
