import java.util.ArrayList;

public abstract class User {
    protected String username;
    protected String password;
    protected String name;
    protected String email;
    
    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }
    
    public abstract String getRole();
    
    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String newPassword) { this.password = newPassword; }
    
    public String toString() {
        return name + " (" + username + ") - " + getRole();
    }
}
