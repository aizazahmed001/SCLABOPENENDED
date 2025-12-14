import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;

public class CustomerDashboard extends JPanel {
    private MainFrame mainFrame;
    private JComboBox<String> fromStation, toStation;
    private JRadioButton singleTicket, returnTicket, adultPassenger, childPassenger, seniorPassenger;
    private JSpinner quantitySpinner;
    private JLabel priceLabel, welcomeLabel;
    private JPanel historyPanel;
    
    public CustomerDashboard(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(MainFrame.DARK_BG);
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MainFrame.DARK_CARD);
        header.setBorder(new EmptyBorder(18, 25, 18, 25));
        
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(MainFrame.GOLD);
        
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
        tabs.addTab("Book Ticket", createBookingTab());
        tabs.addTab("My Tickets", createHistoryTab());
        
        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }
    
    private JPanel createBookingTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(MainFrame.DARK_BG);
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        String[] stations = StationManager.getInstance().getStationNames();
        
        JPanel routeCard = createCard("SELECT ROUTE");
        routeCard.setLayout(new GridLayout(2, 2, 15, 10));
        JLabel fromL = new JLabel("FROM"); fromL.setForeground(MainFrame.TEXT_SECONDARY);
        JLabel toL = new JLabel("TO"); toL.setForeground(MainFrame.TEXT_SECONDARY);
        fromStation = new JComboBox<>(stations);
        toStation = new JComboBox<>(stations); toStation.setSelectedIndex(1);
        fromStation.addItemListener(e -> updatePrice());
        toStation.addItemListener(e -> updatePrice());
        routeCard.add(fromL); routeCard.add(toL);
        routeCard.add(fromStation); routeCard.add(toStation);
        
        JPanel typeCard = createCard("TICKET TYPE");
        typeCard.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5));
        singleTicket = createRadio("Single", true);
        returnTicket = createRadio("Return (x2)", false);
        ButtonGroup tg = new ButtonGroup(); tg.add(singleTicket); tg.add(returnTicket);
        singleTicket.addItemListener(e -> updatePrice());
        returnTicket.addItemListener(e -> updatePrice());
        typeCard.add(singleTicket); typeCard.add(returnTicket);
        
        JPanel passCard = createCard("PASSENGER");
        passCard.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        adultPassenger = createRadio("Adult", true);
        childPassenger = createRadio("Child -50%", false);
        seniorPassenger = createRadio("Senior -30%", false);
        ButtonGroup pg = new ButtonGroup(); pg.add(adultPassenger); pg.add(childPassenger); pg.add(seniorPassenger);
        adultPassenger.addItemListener(e -> updatePrice());
        childPassenger.addItemListener(e -> updatePrice());
        seniorPassenger.addItemListener(e -> updatePrice());
        passCard.add(adultPassenger); passCard.add(childPassenger); passCard.add(seniorPassenger);
        
        JPanel qtyCard = createCard("QUANTITY");
        qtyCard.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel qtyL = new JLabel("Tickets:"); qtyL.setForeground(MainFrame.TEXT_PRIMARY);
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        quantitySpinner.addChangeListener(e -> updatePrice());
        qtyCard.add(qtyL); qtyCard.add(quantitySpinner);
        
        JPanel priceCard = new JPanel();
        priceCard.setBackground(new Color(30, 50, 30));
        priceCard.setBorder(BorderFactory.createLineBorder(MainFrame.SUCCESS, 2));
        priceLabel = new JLabel("TOTAL: Rs. 0.00");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        priceLabel.setForeground(MainFrame.SUCCESS);
        priceCard.add(priceLabel);
        
        JButton bookBtn = new JButton("BOOK NOW");
        bookBtn.setFont(new Font("Arial", Font.BOLD, 16));
        bookBtn.setForeground(MainFrame.DARK_BG);
        bookBtn.setBackground(MainFrame.GOLD);
        bookBtn.setBorderPainted(false);
        bookBtn.setMaximumSize(new Dimension(220, 50));
        bookBtn.setAlignmentX(CENTER_ALIGNMENT);
        bookBtn.addActionListener(e -> handleBooking());
        
        panel.add(routeCard); panel.add(Box.createVerticalStrut(12));
        panel.add(typeCard); panel.add(Box.createVerticalStrut(12));
        panel.add(passCard); panel.add(Box.createVerticalStrut(12));
        panel.add(qtyCard); panel.add(Box.createVerticalStrut(15));
        panel.add(priceCard); panel.add(Box.createVerticalStrut(20));
        panel.add(bookBtn);
        
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }
    
    private JPanel createCard(String title) {
        JPanel card = new JPanel();
        card.setBackground(MainFrame.DARK_CARD);
        card.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(MainFrame.DARK_ELEVATED), 
            title, TitledBorder.LEFT, TitledBorder.TOP, null, MainFrame.GOLD));
        return card;
    }
    
    private JRadioButton createRadio(String text, boolean selected) {
        JRadioButton rb = new JRadioButton(text, selected);
        rb.setForeground(MainFrame.TEXT_PRIMARY);
        rb.setBackground(MainFrame.DARK_CARD);
        return rb;
    }
    
    private JPanel createHistoryTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MainFrame.DARK_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("YOUR BOOKING HISTORY");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(MainFrame.GOLD);
        
        historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(MainFrame.DARK_BG);
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(historyPanel), BorderLayout.CENTER);
        return panel;
    }
    
    private void updatePrice() {
        if (fromStation == null) return;
        int fi = fromStation.getSelectedIndex(), ti = toStation.getSelectedIndex();
        if (fi == ti) { priceLabel.setText("Select different stations"); return; }
        
        StationManager sm = StationManager.getInstance();
        String type = singleTicket.isSelected() ? "Single" : "Return";
        String pass = adultPassenger.isSelected() ? "Adult" : (childPassenger.isSelected() ? "Child" : "Senior");
        double price = sm.calculatePrice(sm.getStationByIndex(fi), sm.getStationByIndex(ti), type, pass, (Integer)quantitySpinner.getValue());
        priceLabel.setText(String.format("TOTAL: Rs. %.2f", price));
    }
    
    private void handleBooking() {
        int fi = fromStation.getSelectedIndex(), ti = toStation.getSelectedIndex();
        if (fi == ti) { JOptionPane.showMessageDialog(this, "Select different stations!"); return; }
        
        StationManager sm = StationManager.getInstance();
        String type = singleTicket.isSelected() ? "Single" : "Return";
        String pass = adultPassenger.isSelected() ? "Adult" : (childPassenger.isSelected() ? "Child" : "Senior");
        
        Customer c = (Customer) mainFrame.getCurrentUser();
        Ticket t = sm.createTicket(sm.getStationByIndex(fi), sm.getStationByIndex(ti), type, pass, (Integer)quantitySpinner.getValue(), c.getUsername());
        c.addBooking(t);
        
        JOptionPane.showMessageDialog(this, "Booking Confirmed!\n\n" + t.toString());
        fromStation.setSelectedIndex(0); toStation.setSelectedIndex(1);
        singleTicket.setSelected(true); adultPassenger.setSelected(true);
        quantitySpinner.setValue(1);
        updatePrice(); refreshHistory();
    }
    
    private void refreshHistory() {
        historyPanel.removeAll();
        User u = mainFrame.getCurrentUser();
        if (u instanceof Customer) {
            for (Ticket t : ((Customer)u).getBookingHistory()) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(MainFrame.DARK_CARD);
                card.setBorder(new EmptyBorder(12, 15, 12, 15));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
                JTextArea text = new JTextArea(t.toString());
                text.setForeground(MainFrame.TEXT_PRIMARY);
                text.setBackground(MainFrame.DARK_CARD);
                text.setEditable(false);
                card.add(text);
                historyPanel.add(card);
                historyPanel.add(Box.createVerticalStrut(10));
            }
        }
        historyPanel.revalidate(); historyPanel.repaint();
    }
    
    public void refreshData() {
        User u = mainFrame.getCurrentUser();
        if (u != null) welcomeLabel.setText("Welcome, " + u.getName() + "!");
        updatePrice(); refreshHistory();
    }
}
