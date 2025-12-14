import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class SignupPage extends JPanel {
    private MainFrame mainFrame;
    private JTextField nameField, emailField, usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JLabel messageLabel;
    
    public SignupPage(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(MainFrame.DARK_BG);
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MainFrame.GOLD);
        header.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JButton backBtn = new JButton("← Back");
        backBtn.setForeground(MainFrame.DARK_BG);
        backBtn.setBackground(MainFrame.GOLD);
        backBtn.setBorderPainted(false);
        backBtn.addActionListener(e -> mainFrame.showPage(MainFrame.WELCOME));
        
        JLabel title = new JLabel("CREATE ACCOUNT", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(MainFrame.DARK_BG);
        
        header.add(backBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(MainFrame.DARK_BG);
        form.setBorder(new EmptyBorder(30, 50, 40, 50));
        
        nameField = createField();
        emailField = createField();
        usernameField = createField();
        passwordField = createPassField();
        confirmPasswordField = createPassField();
        
        messageLabel = new JLabel(" ");
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        JButton signupBtn = new JButton("CREATE ACCOUNT");
        signupBtn.setFont(new Font("Arial", Font.BOLD, 15));
        signupBtn.setForeground(MainFrame.DARK_BG);
        signupBtn.setBackground(MainFrame.GOLD);
        signupBtn.setFocusPainted(false);
        signupBtn.setBorderPainted(false);
        signupBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        signupBtn.addActionListener(e -> handleSignup());
        
        form.add(createLabel("Full Name"));
        form.add(nameField);
        form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Email"));
        form.add(emailField);
        form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Username"));
        form.add(usernameField);
        form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Password"));
        form.add(passwordField);
        form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Confirm Password"));
        form.add(confirmPasswordField);
        form.add(Box.createVerticalStrut(10));
        form.add(messageLabel);
        form.add(Box.createVerticalStrut(20));
        form.add(signupBtn);
        
        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        
        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
    
    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(MainFrame.TEXT_SECONDARY);
        return l;
    }
    
    private JTextField createField() {
        JTextField f = new JTextField(20);
        f.setForeground(MainFrame.TEXT_PRIMARY);
        f.setBackground(MainFrame.DARK_ELEVATED);
        f.setCaretColor(MainFrame.GOLD);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        f.setBorder(new EmptyBorder(10, 15, 10, 15));
        return f;
    }
    
    private JPasswordField createPassField() {
        JPasswordField f = new JPasswordField(20);
        f.setForeground(MainFrame.TEXT_PRIMARY);
        f.setBackground(MainFrame.DARK_ELEVATED);
        f.setCaretColor(MainFrame.GOLD);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        f.setBorder(new EmptyBorder(10, 15, 10, 15));
        return f;
    }
    
    private void handleSignup() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmPasswordField.getPassword());
        
        if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields"); return;
        }
        if (username.length() < 3) { showError("Username must be at least 3 characters"); return; }
        if (password.length() < 4) { showError("Password must be at least 4 characters"); return; }
        if (!password.equals(confirm)) { showError("Passwords do not match"); return; }
        if (!email.contains("@")) { showError("Please enter a valid email"); return; }
        if (UserManager.getInstance().usernameExists(username)) { showError("Username already taken"); return; }
        
        if (UserManager.getInstance().registerCustomer(username, password, name, email)) {
            showSuccess("Account created successfully!");
            clearFields();
            Timer timer = new Timer(1500, e -> mainFrame.showPage(MainFrame.LOGIN));
            timer.setRepeats(false);
            timer.start();
        } else {
            showError("Registration failed");
        }
    }
    
    private void showError(String msg) { messageLabel.setForeground(MainFrame.ERROR); messageLabel.setText(msg); }
    private void showSuccess(String msg) { messageLabel.setForeground(MainFrame.SUCCESS); messageLabel.setText(msg); }
    private void clearFields() { nameField.setText(""); emailField.setText(""); usernameField.setText(""); passwordField.setText(""); confirmPasswordField.setText(""); }
}
