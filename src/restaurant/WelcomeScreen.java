package restaurant;

import java.awt.*;
import javax.swing.*;

public class WelcomeScreen {
    private JFrame welcomeFrame;

    public WelcomeScreen() {
        welcomeFrame = new JFrame("Welcome to Restaurant Ordering System");
        welcomeFrame.setSize(500, 300);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLocationRelativeTo(null); // center the frame

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Our Restaurant!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));

        JButton getStartedButton = new JButton("Get Started");
        getStartedButton.setFont(new Font("Arial", Font.BOLD, 18));
        getStartedButton.setBackground(Color.BLACK);
        getStartedButton.setForeground(Color.WHITE);
        getStartedButton.setFocusPainted(false);
        getStartedButton.setPreferredSize(new Dimension(200, 50));

        // Center the button using a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(getStartedButton);

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        welcomeFrame.add(panel);
        welcomeFrame.setVisible(true);

        // Action listener to launch RestaurantApp
        getStartedButton.addActionListener(e -> {
            welcomeFrame.dispose(); // close welcome screen
            new RestaurantApp();    // launch the main app
        });
    }

    public static void main(String[] args) {
        new WelcomeScreen();
    }
}
