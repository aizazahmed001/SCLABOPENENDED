import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private User currentUser;
    
    public static final String WELCOME = "welcome";
    public static final String LOGIN = "login";
    public static final String SIGNUP = "signup";
    public static final String CUSTOMER_DASHBOARD = "customer_dashboard";
    public static final String ADMIN_DASHBOARD = "admin_dashboard";
    
    public static final Color DARK_BG = new Color(18, 18, 18);
    public static final Color DARK_CARD = new Color(30, 30, 30);
    public static final Color DARK_ELEVATED = new Color(45, 45, 45);
    public static final Color GOLD = new Color(255, 193, 7);
    public static final Color CYAN = new Color(0, 229, 255);
    public static final Color SUCCESS = new Color(0, 230, 118);
    public static final Color ERROR = new Color(255, 82, 82);
    public static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    public static final Color TEXT_SECONDARY = new Color(158, 158, 158);
    public static final Color WHITE = Color.WHITE;
    
    public MainFrame() {
        setTitle("Metro Ticket System");
        setSize(520, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(DARK_BG);
        
        cardPanel.add(new WelcomePage(this), WELCOME);
        cardPanel.add(new LoginPage(this), LOGIN);
        cardPanel.add(new SignupPage(this), SIGNUP);
        cardPanel.add(new CustomerDashboard(this), CUSTOMER_DASHBOARD);
        cardPanel.add(new AdminDashboard(this), ADMIN_DASHBOARD);
        
        add(cardPanel);
        showPage(WELCOME);
    }
    
    public void showPage(String pageName) { cardLayout.show(cardPanel, pageName); }
    public void setCurrentUser(User user) { this.currentUser = user; }
    public User getCurrentUser() { return currentUser; }
    public void logout() { currentUser = null; showPage(WELCOME); }
    
    public void refreshCustomerDashboard() {
        for (Component c : cardPanel.getComponents())
            if (c instanceof CustomerDashboard) ((CustomerDashboard) c).refreshData();
    }
    
    public void refreshAdminDashboard() {
        for (Component c : cardPanel.getComponents())
            if (c instanceof AdminDashboard) ((AdminDashboard) c).refreshData();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
