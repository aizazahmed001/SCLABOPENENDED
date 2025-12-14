import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class WelcomePage extends JPanel {
    private MainFrame mainFrame;
    
    public WelcomePage(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(MainFrame.DARK_BG);
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(MainFrame.DARK_BG);
        content.setBorder(new EmptyBorder(60, 40, 60, 40));
        
        JLabel icon = new JLabel("🚄");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        icon.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("METRO TICKET");
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setForeground(MainFrame.GOLD);
        title.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("BOOKING SYSTEM");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setForeground(MainFrame.TEXT_SECONDARY);
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        
        JPanel line = new JPanel();
        line.setBackground(MainFrame.GOLD);
        line.setMaximumSize(new Dimension(100, 3));
        
        JButton loginBtn = createBtn("SIGN IN", MainFrame.GOLD, MainFrame.DARK_BG);
        loginBtn.addActionListener(e -> mainFrame.showPage(MainFrame.LOGIN));
        
        JButton signupBtn = createBtn("CREATE ACCOUNT", MainFrame.DARK_ELEVATED, MainFrame.WHITE);
        signupBtn.setBorder(BorderFactory.createLineBorder(MainFrame.GOLD, 2));
        signupBtn.addActionListener(e -> mainFrame.showPage(MainFrame.SIGNUP));
        
        content.add(icon);
        content.add(Box.createVerticalStrut(20));
        content.add(title);
        content.add(subtitle);
        content.add(Box.createVerticalStrut(15));
        content.add(line);
        content.add(Box.createVerticalStrut(60));
        content.add(loginBtn);
        content.add(Box.createVerticalStrut(15));
        content.add(signupBtn);
        
        add(content, BorderLayout.CENTER);
    }
    
    private JButton createBtn(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setMaximumSize(new Dimension(280, 50));
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
