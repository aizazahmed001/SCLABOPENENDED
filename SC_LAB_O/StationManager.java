import java.util.ArrayList;
import java.util.Iterator;

public class StationManager {
    private ArrayList<Station> stations;
    private ArrayList<Ticket> allTickets;
    private double basePricePerUnit = 150.0;
    private static StationManager instance;
    
    private StationManager() {
        stations = new ArrayList<>();
        allTickets = new ArrayList<>();
        stations.add(new Station("LHR", "Lahore Junction", "Lahore", 0));
        stations.add(new Station("FSD", "Faisalabad Terminal", "Faisalabad", 1));
        stations.add(new Station("MUL", "Multan City", "Multan", 2));
        stations.add(new Station("RWP", "Rawalpindi Station", "Rawalpindi", 3));
        stations.add(new Station("ISB", "Islamabad Central", "Islamabad", 4));
        stations.add(new Station("PSH", "Peshawar Cantonment", "Peshawar", 5));
        stations.add(new Station("QTA", "Quetta Railway", "Quetta", 6));
        stations.add(new Station("KHI", "Karachi Cantt", "Karachi", 7));
    }
    
    public static StationManager getInstance() {
        if (instance == null) instance = new StationManager();
        return instance;
    }
    
    public ArrayList<Station> getStations() { return stations; }
    
    public Station getStationByIndex(int index) {
        if (index >= 0 && index < stations.size()) return stations.get(index);
        return null;
    }
    
    public String[] getStationNames() {
        String[] names = new String[stations.size()];
        for (int i = 0; i < stations.size(); i++) {
            names[i] = stations.get(i).getName();
        }
        return names;
    }
    
    public double calculatePrice(Station from, Station to, String ticketType, String passengerType, int quantity) {
        double basePrice = from.getDistanceTo(to) * basePricePerUnit;
        if (ticketType.equals("Return")) basePrice *= 2;
        if (passengerType.equals("Child")) basePrice *= 0.5;
        else if (passengerType.equals("Senior")) basePrice *= 0.7;
        return basePrice * quantity;
    }
    
    public Ticket createTicket(Station from, Station to, String ticketType, String passengerType, int quantity, String customerUsername) {
        double price = calculatePrice(from, to, ticketType, passengerType, quantity);
        Ticket ticket = new Ticket(from, to, ticketType, passengerType, quantity, price, customerUsername);
        allTickets.add(ticket);
        return ticket;
    }
    
    public ArrayList<Ticket> getAllTickets() { return allTickets; }
    public int getStationCount() { return stations.size(); }
    
    public double getTotalRevenue() {
        double total = 0;
        for (Ticket ticket : allTickets) total += ticket.getTotalPrice();
        return total;
    }
}
