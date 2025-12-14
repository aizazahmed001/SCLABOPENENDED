import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class LoginPage extends JPanel {
    private MainFrame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    
    public LoginPage(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(MainFrame.DARK_BG);
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MainFrame.DARK_CARD);
        header.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JButton backBtn = new JButton("← Back");
        backBtn.setForeground(MainFrame.TEXT_SECONDARY);
        backBtn.setBackground(MainFrame.DARK_CARD);
        backBtn.setBorderPainted(false);
        backBtn.addActionListener(e -> mainFrame.showPage(MainFrame.WELCOME));
        
        JLabel title = new JLabel("SIGN IN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(MainFrame.GOLD);
        
        header.add(backBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(MainFrame.DARK_BG);
        form.setBorder(new EmptyBorder(50, 50, 50, 50));
        
        JLabel icon = new JLabel("🔐");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        icon.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel welcome = new JLabel("Welcome Back");
        welcome.setFont(new Font("Arial", Font.BOLD, 24));
        welcome.setForeground(MainFrame.TEXT_PRIMARY);
        welcome.setAlignmentX(CENTER_ALIGNMENT);
        
        usernameField = createField();
        passwordField = new JPasswordField(20);
        styleField(passwordField);
        
        messageLabel = new JLabel(" ");
        messageLabel.setForeground(MainFrame.ERROR);
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        JButton loginBtn = new JButton("SIGN IN");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 15));
        loginBtn.setForeground(MainFrame.DARK_BG);
        loginBtn.setBackground(MainFrame.GOLD);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        loginBtn.addActionListener(e -> handleLogin());
        
        passwordField.addActionListener(e -> handleLogin());
        
        form.add(icon);
        form.add(Box.createVerticalStrut(20));
        form.add(welcome);
        form.add(Box.createVerticalStrut(40));
        form.add(usernameField);
        form.add(Box.createVerticalStrut(15));
        form.add(passwordField);
        form.add(Box.createVerticalStrut(10));
        form.add(messageLabel);
        form.add(Box.createVerticalStrut(25));
        form.add(loginBtn);
        
        add(header, BorderLayout.NORTH);
        add(new JScrollPane(form), BorderLayout.CENTER);
    }
    
    private JTextField createField() {
        JTextField f = new JTextField(20);
        styleField(f);
        return f;
    }
    
    private void styleField(JTextField f) {
        f.setFont(new Font("Arial", Font.PLAIN, 14));
        f.setForeground(MainFrame.TEXT_PRIMARY);
        f.setBackground(MainFrame.DARK_ELEVATED);
        f.setCaretColor(MainFrame.GOLD);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        f.setBorder(new EmptyBorder(10, 15, 10, 15));
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in all fields");
            return;
        }
        
        User user = UserManager.getInstance().authenticate(username, password);
        if (user != null) {
            mainFrame.setCurrentUser(user);
            usernameField.setText("");
            passwordField.setText("");
            messageLabel.setText(" ");
            if (user.getRole().equals("Admin")) {
                mainFrame.refreshAdminDashboard();
                mainFrame.showPage(MainFrame.ADMIN_DASHBOARD);
            } else {
                mainFrame.refreshCustomerDashboard();
                mainFrame.showPage(MainFrame.CUSTOMER_DASHBOARD);
            }
        } else {
            messageLabel.setText("Invalid username or password");
        }
    }
}
