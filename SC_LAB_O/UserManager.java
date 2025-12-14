import java.util.HashMap;
import java.util.ArrayList;

public class UserManager {
    private HashMap<String, User> users;
    private ArrayList<Customer> customers;
    private ArrayList<Admin> admins;
    private static UserManager instance;
    
    private UserManager() {
        users = new HashMap<>();
        customers = new ArrayList<>();
        admins = new ArrayList<>();
        Admin defaultAdmin = new Admin("admin", "admin123", "System Admin", "admin@metro.pk", "IT");
        users.put(defaultAdmin.getUsername(), defaultAdmin);
        admins.add(defaultAdmin);
    }
    
    public static UserManager getInstance() {
        if (instance == null) instance = new UserManager();
        return instance;
    }
    
    public boolean registerCustomer(String username, String password, String name, String email) {
        if (users.containsKey(username)) return false;
        Customer customer = new Customer(username, password, name, email);
        users.put(username, customer);
        customers.add(customer);
        return true;
    }
    
    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) return user;
        return null;
    }
    
    public boolean usernameExists(String username) { return users.containsKey(username); }
    public User getUser(String username) { return users.get(username); }
    public ArrayList<Customer> getAllCustomers() { return customers; }
    public int getTotalUsers() { return users.size(); }
}
