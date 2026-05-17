package find_and_flip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;                  // For GUI timer
import javax.swing.*;                      // For JFrame, JButton, JPanel, etc.
import java.awt.*;                         // For layout and color
import java.awt.event.*;                   // For event listeners
import java.util.ArrayList;                // For list of cards and scores
import java.util.Collections;              // For shuffling
import java.util.Scanner;                  // For reading leaderboard
import java.io.File;                       // For leaderboard file
import java.io.IOException;                // For file I/O exception
import java.io.PrintWriter;                // For writing scores


public class GameWindow extends JFrame {
    private int pairCount;
    private int cols;
    private int matchedPairs = 0;
    private String difficulty;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Card> flipped = new ArrayList<>();
    private long startTime;
    private Timer gameTimer;
    private JLabel timerLabel;

    public GameWindow(String difficulty) {
        this.difficulty = difficulty;
        setTitle("Flip and Find - " + difficulty);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        timerLabel.setForeground(Color.YELLOW);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(timerLabel, BorderLayout.NORTH);

        updateDifficulty();
        initGameBoard();
        startTimer();
    }

    private void updateDifficulty() {
        switch (difficulty) {
            case "Easy": pairCount = 4; cols = 4; break;
            case "Normal": pairCount = 8; cols = 4; break;
            case "Hard": pairCount = 16; cols = 8; break;
            case "Master": pairCount = 32; cols = 8; break;
            default: pairCount = 4; cols = 4; break;
        }
    }

    private void initGameBoard() {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(0, cols, 10, 10));
        cards.clear();

        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < pairCount; ++i) {
            ids.add(i);
            ids.add(i);
        }
        Collections.shuffle(ids);

        for (int id : ids) {
            Card card = new Card(id);
            card.button.addActionListener(e -> onCardClicked(card));
            cards.add(card);
            cardPanel.add(card.button);
        }

        add(cardPanel, BorderLayout.CENTER);
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        gameTimer = new Timer(1000, e -> {
            int time = (int) ((System.currentTimeMillis() - startTime) / 1000);
            timerLabel.setText("Time: " + time + "s");
        });
        gameTimer.start();
    }

    private void onCardClicked(Card card) {
        if (card.revealed || card.matched || flipped.size() == 2) return;

        card.reveal();
        flipped.add(card);

        if (flipped.size() == 2) {
            Card a = flipped.get(0), b = flipped.get(1);
            if (a.id == b.id) {
                a.match();
                b.match();
                matchedPairs++;
                flipped.clear();

                if (matchedPairs == pairCount) {
                    gameTimer.stop();
                    int finalTime = (int)((System.currentTimeMillis() - startTime) / 1000);
                    JOptionPane.showMessageDialog(this, "Congratulations! You completed the game in " + finalTime + " seconds.");
                    dispose();  // Close this window
                }
            } else {
                Timer t = new Timer(700, e -> {
                    a.hide();
                    b.hide();
                    flipped.clear();
                });
                t.setRepeats(false);
                t.start();
            }
        }
    }
}
