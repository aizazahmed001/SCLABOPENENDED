import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.ArrayList;

public class AdminDashboard extends JPanel {
    private MainFrame mainFrame;
    private JLabel welcomeLabel, totalUsersLabel, totalBookingsLabel, totalRevenueLabel, totalStationsLabel;
    private DefaultTableModel bookingsModel, customersModel;
    
    public AdminDashboard(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(MainFrame.DARK_BG);
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(40, 20, 20));
        header.setBorder(new EmptyBorder(18, 25, 18, 25));
        
        welcomeLabel = new JLabel("ADMIN PANEL");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(MainFrame.CYAN);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setForeground(MainFrame.TEXT_PRIMARY);
        logoutBtn.setBackground(MainFrame.DARK_ELEVATED);
        logoutBtn.setBorderPainted(false);
        logoutBtn.addActionListener(e -> mainFrame.logout());
        
        header.add(welcomeLabel, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(MainFrame.DARK_BG);
        tabs.setForeground(MainFrame.TEXT_PRIMARY);
        tabs.addTab("Dashboard", createDashboardTab());
        tabs.addTab("Bookings", createBookingsTab());
        tabs.addTab("Customers", createCustomersTab());
        tabs.addTab("Stations", createStationsTab());
        
        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }
    
    private JPanel createDashboardTab() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setBackground(MainFrame.DARK_BG);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        totalUsersLabel = new JLabel("0");
        totalBookingsLabel = new JLabel("0");
        totalRevenueLabel = new JLabel("Rs. 0");
        totalStationsLabel = new JLabel("0");
        
        panel.add(createStatCard("Users", totalUsersLabel, MainFrame.CYAN));
        panel.add(createStatCard("Bookings", totalBookingsLabel, MainFrame.GOLD));
        panel.add(createStatCard("Revenue", totalRevenueLabel, MainFrame.SUCCESS));
        panel.add(createStatCard("Stations", totalStationsLabel, new Color(186, 104, 200)));
        
        return panel;
    }
    
    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(MainFrame.DARK_CARD);
        card.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        JLabel titleL = new JLabel(title);
        titleL.setForeground(MainFrame.TEXT_SECONDARY);
        
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        valueLabel.setForeground(color);
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(MainFrame.DARK_CARD);
        content.add(titleL);
        content.add(valueLabel);
        
        card.add(content, BorderLayout.CENTER);
        return card;
    }
    
    private JPanel createBookingsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MainFrame.DARK_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        String[] cols = {"ID", "Customer", "From", "To", "Type", "Price", "Date"};
        bookingsModel = new DefaultTableModel(cols, 0);
        JTable table = createTable(bookingsModel);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createCustomersTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MainFrame.DARK_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        String[] cols = {"Username", "Name", "Email", "Bookings", "Spent"};
        customersModel = new DefaultTableModel(cols, 0);
        JTable table = createTable(customersModel);
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
    
    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setForeground(MainFrame.TEXT_PRIMARY);
        table.setBackground(MainFrame.DARK_CARD);
        table.setGridColor(MainFrame.DARK_ELEVATED);
        table.setRowHeight(32);
        table.getTableHeader().setBackground(MainFrame.DARK_ELEVATED);
        table.getTableHeader().setForeground(MainFrame.GOLD);
        return table;
    }
    
    private JPanel createStationsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MainFrame.DARK_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBackground(MainFrame.DARK_BG);
        
        for (Station s : StationManager.getInstance().getStations()) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(MainFrame.DARK_CARD);
            card.setBorder(new EmptyBorder(12, 20, 12, 20));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            
            JLabel code = new JLabel(s.getCode());
            code.setFont(new Font("Arial", Font.BOLD, 16));
            code.setForeground(MainFrame.GOLD);
            
            JLabel name = new JLabel(s.getName() + " - " + s.getCity());
            name.setForeground(MainFrame.TEXT_PRIMARY);
            
            card.add(code, BorderLayout.WEST);
            card.add(name, BorderLayout.CENTER);
            list.add(card);
            list.add(Box.createVerticalStrut(8));
        }
        
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        return panel;
    }
    
    public void refreshData() {
        User u = mainFrame.getCurrentUser();
        if (u != null) welcomeLabel.setText("Admin: " + u.getName());
        
        totalUsersLabel.setText(String.valueOf(UserManager.getInstance().getTotalUsers()));
        totalBookingsLabel.setText(String.valueOf(StationManager.getInstance().getAllTickets().size()));
        totalRevenueLabel.setText(String.format("Rs. %.0f", StationManager.getInstance().getTotalRevenue()));
        totalStationsLabel.setText(String.valueOf(StationManager.getInstance().getStationCount()));
        
        bookingsModel.setRowCount(0);
        for (Ticket t : StationManager.getInstance().getAllTickets()) {
            bookingsModel.addRow(new Object[]{t.getTicketId(), t.getCustomerUsername(), 
                t.getFromStation().getCode(), t.getToStation().getCode(), t.getTicketType(),
                String.format("Rs. %.2f", t.getTotalPrice()), t.getFormattedDate()});
        }
        
        customersModel.setRowCount(0);
        for (Customer c : UserManager.getInstance().getAllCustomers()) {
            customersModel.addRow(new Object[]{c.getUsername(), c.getName(), c.getEmail(),
                c.getTotalBookings(), String.format("Rs. %.2f", c.getTotalSpent())});
        }
    }
}
