import java.util.ArrayList;
import java.util.Iterator;

public class Customer extends User {
    private ArrayList<Ticket> bookingHistory;
    
    public Customer(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.bookingHistory = new ArrayList<>();
    }
    
    public String getRole() { return "Customer"; }
    
    public void addBooking(Ticket ticket) { bookingHistory.add(ticket); }
    
    public ArrayList<Ticket> getBookingHistory() { return bookingHistory; }
    
    public int getTotalBookings() { return bookingHistory.size(); }
    
    public double getTotalSpent() {
        double total = 0;
        Iterator<Ticket> iterator = bookingHistory.iterator();
        while (iterator.hasNext()) {
            total += iterator.next().getTotalPrice();
        }
        return total;
    }
}
