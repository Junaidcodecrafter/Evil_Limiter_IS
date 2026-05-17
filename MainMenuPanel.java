package find_and_flip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    JButton newGameBtn = new JButton("🎮 New Game");
    JButton resumeBtn = new JButton("🔄 Resume Game");
    JButton leaderboardBtn = new JButton("🏆 Leaderboard");
    JButton difficultyBtn = new JButton("⚙️ Difficulty: Easy");
    JButton exitBtn = new JButton("❌ Exit");

    JLabel titleLabel = new JLabel("Flip and Find");

    public MainMenuPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 60));

        // Title Styling
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 10, 30, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBackground(new Color(30, 30, 60));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));

        Font btnFont = new Font("Arial", Font.PLAIN, 20);
        Color btnColor = new Color(70, 130, 180);

        for (JButton btn : new JButton[]{newGameBtn, resumeBtn, leaderboardBtn, difficultyBtn, exitBtn}) {
            btn.setFont(btnFont);
            btn.setBackground(btnColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    // Utility to set current difficulty text
    public void setDifficulty(String diff) {
        difficultyBtn.setText("⚙️ Difficulty: " + diff);
    }

    public void setResumeEnabled(boolean enabled) {
        resumeBtn.setEnabled(enabled);
    }

    // Methods to hook up event listeners from main class
    public void setNewGameListener(ActionListener l) {
        newGameBtn.addActionListener(l);
    }

    public void setResumeListener(ActionListener l) {
        resumeBtn.addActionListener(l);
    }

    public void setLeaderboardListener(ActionListener l) {
        leaderboardBtn.addActionListener(l);
    }

    public void setDifficultyListener(ActionListener l) {
        difficultyBtn.addActionListener(l);
    }

    public void setExitListener(ActionListener l) {
        exitBtn.addActionListener(l);
    }
}
