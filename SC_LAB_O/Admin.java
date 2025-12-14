import java.util.ArrayList;

public class Admin extends User {
    private String department;
    
    public Admin(String username, String password, String name, String email, String department) {
        super(username, password, name, email);
        this.department = department;
    }
    
    public Admin(String username, String password, String name, String email) {
        this(username, password, name, email, "General");
    }
    
    public String getRole() { return "Admin"; }
    
    public String getDepartment() { return department; }
    
    public String toString() {
        return super.toString() + " [" + department + "]";
    }
}
