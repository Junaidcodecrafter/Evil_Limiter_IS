package find_and_flip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
//
//
//public class FlipAndFindGame extends JFrame {
//    private CardLayout cardLayout;
//    private JPanel mainPanel, menuPanel, gamePanel, leaderboardPanel;
//    private JPanel cardGrid;
//    private JLabel timerLabel;
//    private JComboBox<String> difficultyBox;
//
//    private ArrayList<Card> cards = new ArrayList<>();
//    private ArrayList<ScoreEntry> leaderboard = new ArrayList<>();
//    private ArrayList<Card> flipped = new ArrayList<>();
//    private Timer gameTimer;
//    private long startTime;
//
//    private int pairCount = 4;
//    private int cols = 4;
//    private int matchedPairs = 0;
//    private String difficulty = "Easy";
//    private boolean hasSavedGame = false;
//
//    public FlipAndFindGame() {
//        setTitle("Flip and Find");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(800, 700);
//        setLocationRelativeTo(null);
//
//        cardLayout = new CardLayout();
//        mainPanel = new JPanel(cardLayout);
//        add(mainPanel);
//
//        buildMenuPanel();
//        buildGamePanel();
//        buildLeaderboardPanel();
//
//        mainPanel.add(menuPanel, "Menu");
//        mainPanel.add(gamePanel, "Game");
//        mainPanel.add(leaderboardPanel, "Leaderboard");
//
//        loadScores();
//        cardLayout.show(mainPanel, "Menu");
//    }
//
//    private void buildMenuPanel() {
//        menuPanel = new JPanel();
//        menuPanel.setLayout(null);
//        menuPanel.setBackground(new Color(40, 40, 100));
//
//        JLabel title = new JLabel("Flip and Find");
//        title.setFont(new Font("Verdana", Font.BOLD, 36));
//        title.setForeground(Color.YELLOW);
//        title.setBounds(250, 50, 400, 50);
//        menuPanel.add(title);
//
//        JButton newGameBtn = createMenuButton("New Game", 150);
//        JButton resumeBtn = createMenuButton("Resume Game", 210);
//        JButton diffBtn = createMenuButton("Change Difficulty", 270);
//        JButton leaderBtn = createMenuButton("Leaderboard", 330);
//        JButton exitBtn = createMenuButton("Exit", 390);
//
//        newGameBtn.addActionListener(e -> {
//            hasSavedGame = false;
//            startNewGame();
//        });
//
//        resumeBtn.addActionListener(e -> {
//            if (hasSavedGame) {
//                cardLayout.show(mainPanel, "Game");
//                gameTimer.start();
//            } else {
//                JOptionPane.showMessageDialog(this, "No saved game available.");
//            }
//        });
//
//        diffBtn.addActionListener(e -> {
//            String[] options = {"Easy", "Normal", "Hard", "Master"};
//            String selected = (String) JOptionPane.showInputDialog(this, "Select Difficulty:", "Difficulty",
//                    JOptionPane.PLAIN_MESSAGE, null, options, difficulty);
//            if (selected != null) difficulty = selected;
//        });
//
//        leaderBtn.addActionListener(e -> {
//            showLeaderboardText();
//            cardLayout.show(mainPanel, "Leaderboard");
//        });
//
//        exitBtn.addActionListener(e -> System.exit(0));
//
//        menuPanel.add(newGameBtn);
//        menuPanel.add(resumeBtn);
//        menuPanel.add(diffBtn);
//        menuPanel.add(leaderBtn);
//        menuPanel.add(exitBtn);
//    }
//
//    private JButton createMenuButton(String text, int y) {
//        JButton btn = new JButton(text);
//        btn.setBounds(300, y, 200, 40);
//        btn.setFont(new Font("Arial", Font.BOLD, 16));
//        btn.setBackground(new Color(70, 130, 180));
//        btn.setForeground(Color.WHITE);
//        return btn;
//    }
//
//    private void buildGamePanel() {
//        gamePanel = new JPanel(new BorderLayout());
//
//        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        timerLabel = new JLabel("Time: 0s");
//        topPanel.add(timerLabel);
//
//        JButton backBtn = new JButton("Back to Menu");
//        backBtn.addActionListener(e -> {
//            cardLayout.show(mainPanel, "Menu");
//            if (gameTimer != null) gameTimer.stop();
//        });
//        topPanel.add(backBtn);
//        gamePanel.add(topPanel, BorderLayout.NORTH);
//
//        cardGrid = new JPanel();
//        gamePanel.add(cardGrid, BorderLayout.CENTER);
//    }
//
//    private void buildLeaderboardPanel() {
//        leaderboardPanel = new JPanel(new BorderLayout());
//        JTextArea lbText = new JTextArea();
//        lbText.setEditable(false);
//        lbText.setFont(new Font("Monospaced", Font.PLAIN, 16));
//        leaderboardPanel.add(new JScrollPane(lbText), BorderLayout.CENTER);
//
//        JButton backBtn = new JButton("Back to Menu");
//        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
//        leaderboardPanel.add(backBtn, BorderLayout.SOUTH);
//
//        leaderboardPanel.putClientProperty("textArea", lbText);
//    }
//
//    private void showLeaderboardText() {
//        leaderboard.sort(Comparator.naturalOrder());
//        JTextArea lbText = (JTextArea) leaderboardPanel.getClientProperty("textArea");
//        StringBuilder sb = new StringBuilder("Top 15 Scores:\n\n");
//        for (int i = 0; i < Math.min(15, leaderboard.size()); ++i)
//            sb.append((i + 1)).append(". ").append(leaderboard.get(i)).append("\n");
//        lbText.setText(sb.toString());
//    }
//
//    private void startNewGame() {
//        if (difficulty.equals("Easy")) pairCount = 4;
//        else if (difficulty.equals("Normal")) pairCount = 8;
//        else if (difficulty.equals("Hard")) pairCount = 16;
//        else pairCount = 32;
//
//        cols = (pairCount <= 8) ? 4 : 8;
//        cardGrid.removeAll();
//        cardGrid.setLayout(new GridLayout(0, cols, 10, 10));
//
//        cards.clear();
//        flipped.clear();
//        matchedPairs = 0;
//
//        ArrayList<Integer> ids = new ArrayList<>();
//        for (int i = 0; i < pairCount; ++i) {
//            ids.add(i);
//            ids.add(i);
//        }
//        Collections.shuffle(ids);
//
//        for (int id : ids) {
//            Card card = new Card(id);
//            card.button.addActionListener(e -> onCardClicked(card));
//            cards.add(card);
//            cardGrid.add(card.button);
//        }
//
//        startTime = System.currentTimeMillis();
//        if (gameTimer != null) gameTimer.stop();
//        gameTimer = new Timer(1000, e -> updateTimer());
//        gameTimer.start();
//
//        hasSavedGame = true;
//        revalidate();
//        repaint();
//        cardLayout.show(mainPanel, "Game");
//    }
//
//    private void updateTimer() {
//        long now = System.currentTimeMillis();
//        int seconds = (int)((now - startTime) / 1000);
//        timerLabel.setText("Time: " + seconds + "s");
//    }
//
//    private void onCardClicked(Card card) {
//        if (card.revealed || card.matched || flipped.size() == 2) return;
//
//        card.reveal();
//        flipped.add(card);
//
//        if (flipped.size() == 2) {
//            Card a = flipped.get(0), b = flipped.get(1);
//            if (a.id == b.id) {
//                a.match();
//                b.match();
//                matchedPairs++;
//                flipped.clear();
//                if (matchedPairs == pairCount) {
//                    gameTimer.stop();
//                    float time = (System.currentTimeMillis() - startTime) / 1000f;
//                    leaderboard.add(new ScoreEntry(time, difficulty));
//                    saveScores();
//                    JOptionPane.showMessageDialog(this, "You Won! Time: " + time + "s");
//                    cardLayout.show(mainPanel, "Menu");
//                }
//            } else {
//                Timer t = new Timer(800, e -> {
//                    a.hide();
//                    b.hide();
//                    flipped.clear();
//                });
//                t.setRepeats(false);
//                t.start();
//            }
//        }
//    }
//
//    private void saveScores() {
//        try (PrintWriter writer = new PrintWriter("leaderboard.txt")) {
//            for (ScoreEntry entry : leaderboard)
//                writer.println(entry.timeTaken + " " + entry.difficulty);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void loadScores() {
//        leaderboard.clear();
//        File file = new File("leaderboard.txt");
//        if (!file.exists()) return;
//
//        try (Scanner sc = new Scanner(file)) {
//            while (sc.hasNextFloat()) {
//                float time = sc.nextFloat();
//                String diff = sc.next();
//                leaderboard.add(new ScoreEntry(time, diff));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new FlipAndFindGame().setVisible(true));
//    }
//}

//public class FlipAndFindGame extends JFrame {
//    private CardLayout cardLayout = new CardLayout();
//    private JPanel mainPanel = new JPanel(cardLayout);
//
//    private JPanel menuPanel = new JPanel();
//    private JPanel gamePanel = new JPanel();
//    private JPanel boardPanel = new JPanel();
//
//    private ArrayList<Card> cards = new ArrayList<>();
//    private ArrayList<Card> flipped = new ArrayList<>();
//    private ArrayList<ScoreEntry> leaderboard = new ArrayList<>();
//
//    private Timer gameTimer;
//    private long startTime;
//    private int pairCount = 4;
//    private int matchedPairs = 0;
//    private int cols = 4;
//    private String difficulty = "Easy";
//    private JLabel timerLabel = new JLabel("Time: 0s");
//
//    private String fontPath = "assets/customFont.ttf";
//    private Font customFont;
//
//    private boolean hasSavedGame = false;
//
//    public FlipAndFindGame() {
//        setTitle("Flip and Find");
//        setSize(1200, 800);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        loadFont();
//        loadScores();
//        setupMenu();
//        setupGamePanel();
//
//        add(mainPanel);
//        setVisible(true);
//    }
//
//    private void loadFont() {
//        try {
//            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(24f);
//        } catch (Exception e) {
//            customFont = new JLabel().getFont(); // fallback
//        }
//    }
//
//    private JButton styledButton(String text) {
//        JButton btn = new JButton(text);
//        btn.setFont(customFont);
//        btn.setFocusPainted(false);
//        btn.setForeground(Color.WHITE);
//        btn.setBackground(new Color(60, 60, 120));
//        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        btn.setPreferredSize(new Dimension(300, 50));
//        btn.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                btn.setBackground(new Color(90, 90, 180));
//            }
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                btn.setBackground(new Color(60, 60, 120));
//            }
//        });
//        return btn;
//    }
//
//    private void setupMenu() {
//        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
//        menuPanel.setBackground(new Color(20, 20, 40));
//        JLabel title = new JLabel("Flip and Find", SwingConstants.CENTER);
//        title.setFont(customFont.deriveFont(48f));
//        title.setForeground(Color.YELLOW);
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        JButton newBtn = styledButton("New Game");
//        JButton resumeBtn = styledButton("Resume Game");
//        JButton diffBtn = styledButton("Difficulty: Easy");
//        JButton leadBtn = styledButton("Leaderboard");
//        JButton exitBtn = styledButton("Exit");
//
//        newBtn.addActionListener(e -> {
//            startGame();
//            cardLayout.show(mainPanel, "game");
//        });
//
//        resumeBtn.addActionListener(e -> {
//            if (hasSavedGame) {
//                cardLayout.show(mainPanel, "game");
//                gameTimer.start();
//            } else {
//                JOptionPane.showMessageDialog(this, "No saved game.");
//            }
//        });
//
//        diffBtn.addActionListener(e -> {
//            String[] levels = {"Easy", "Normal", "Hard", "Master"};
//            String selected = (String) JOptionPane.showInputDialog(this, "Select Difficulty", "Difficulty",
//                    JOptionPane.PLAIN_MESSAGE, null, levels, difficulty);
//            if (selected != null) {
//                difficulty = selected;
//                diffBtn.setText("Difficulty: " + difficulty);
//            }
//        });
//
//        leadBtn.addActionListener(e -> showLeaderboard());
//
//        exitBtn.addActionListener(e -> System.exit(0));
//
//        menuPanel.add(Box.createVerticalStrut(60));
//        menuPanel.add(title);
//        menuPanel.add(Box.createVerticalStrut(40));
//        menuPanel.add(newBtn);
//        menuPanel.add(Box.createVerticalStrut(20));
//        menuPanel.add(resumeBtn);
//        menuPanel.add(Box.createVerticalStrut(20));
//        menuPanel.add(diffBtn);
//        menuPanel.add(Box.createVerticalStrut(20));
//        menuPanel.add(leadBtn);
//        menuPanel.add(Box.createVerticalStrut(20));
//        menuPanel.add(exitBtn);
//
//        mainPanel.add(menuPanel, "menu");
//    }
//
//    private void setupGamePanel() {
//        gamePanel.setLayout(new BorderLayout());
//        gamePanel.setBackground(new Color(30, 30, 50));
//
//        JButton backBtn = styledButton("Return to Menu");
//        backBtn.addActionListener(e -> {
//            gameTimer.stop();
//            hasSavedGame = true;
//            cardLayout.show(mainPanel, "menu");
//        });
//
//        JPanel top = new JPanel(new BorderLayout());
//        top.add(backBtn, BorderLayout.WEST);
//        timerLabel.setFont(customFont);
//        timerLabel.setForeground(Color.YELLOW);
//        top.add(timerLabel, BorderLayout.EAST);
//        top.setBackground(new Color(40, 40, 80));
//
//        boardPanel.setBackground(new Color(50, 50, 100));
//
//        gamePanel.add(top, BorderLayout.NORTH);
//        gamePanel.add(boardPanel, BorderLayout.CENTER);
//
//        mainPanel.add(gamePanel, "game");
//    }
//
//    private void startGame() {
//        boardPanel.removeAll();
//        cards.clear();
//        flipped.clear();
//        matchedPairs = 0;
//
//        if (difficulty.equals("Easy")) pairCount = 4;
//        else if (difficulty.equals("Normal")) pairCount = 8;
//        else if (difficulty.equals("Hard")) pairCount = 16;
//        else pairCount = 32;
//
//        cols = (pairCount <= 8) ? 4 : 8;
//        boardPanel.setLayout(new GridLayout(0, cols, 10, 10));
//
//        ArrayList<Integer> ids = new ArrayList<>();
//        for (int i = 0; i < pairCount; ++i) {
//            ids.add(i);
//            ids.add(i);
//        }
//        Collections.shuffle(ids);
//
//        for (int id : ids) {
//            Card c = new Card(id);
//            c.button.addActionListener(e -> onCardClick(c));
//            cards.add(c);
//            boardPanel.add(c.button);
//        }
//
//        startTime = System.currentTimeMillis();
//        if (gameTimer != null) gameTimer.stop();
//        gameTimer = new Timer(1000, e -> updateTimer());
//        gameTimer.start();
//
//        revalidate();
//        repaint();
//    }
//
//    private void updateTimer() {
//        int seconds = (int)((System.currentTimeMillis() - startTime) / 1000);
//        timerLabel.setText("Time: " + seconds + "s");
//    }
//
//    private void onCardClick(Card c) {
//        if (c.revealed || c.matched || flipped.size() == 2) return;
//
//        c.reveal();
//        flipped.add(c);
//
//        if (flipped.size() == 2) {
//            Card a = flipped.get(0), b = flipped.get(1);
//            if (a.id == b.id) {
//                a.match();
//                b.match();
//                matchedPairs++;
//                flipped.clear();
//                if (matchedPairs == pairCount) {
//                    gameTimer.stop();
//                    float time = (System.currentTimeMillis() - startTime) / 1000f;
//                    leaderboard.add(new ScoreEntry(time, difficulty));
//                    saveScores();
//                    JOptionPane.showMessageDialog(this, "You Win! Time: " + time + "s");
//                    cardLayout.show(mainPanel, "menu");
//                }
//            } else {
//                Timer delay = new Timer(600, evt -> {
//                    a.hide();
//                    b.hide();
//                    flipped.clear();
//                });
//                delay.setRepeats(false);
//                delay.start();
//            }
//        }
//    }
//
//    private void showLeaderboard() {
//        Collections.sort(leaderboard);
//        StringBuilder sb = new StringBuilder("Top 15 Scores:\n\n");
//        for (int i = 0; i < Math.min(15, leaderboard.size()); ++i) {
//            sb.append((i + 1)).append(". ").append(leaderboard.get(i)).append("\n");
//        }
//        JOptionPane.showMessageDialog(this, sb.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    private void loadScores() {
//        leaderboard.clear();
//        File file = new File("leaderboard.txt");
//        if (!file.exists()) return;
//        try (Scanner sc = new Scanner(file)) {
//            while (sc.hasNextFloat()) {
//                float t = sc.nextFloat();
//                String d = sc.next();
//                leaderboard.add(new ScoreEntry(t, d));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void saveScores() {
//        try (PrintWriter out = new PrintWriter("leaderboard.txt")) {
//            for (ScoreEntry e : leaderboard)
//                out.println(e.timeTaken + " " + e.difficulty);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new FlipAndFindGame());
//    }
//}


//public class FlipAndFindGame extends JFrame {
//    private JPanel mainPanel;
//    private JButton newGameBtn, resumeBtn, difficultyBtn, leaderboardBtn, exitBtn;
//    private String difficulty = "Easy";
//    private Image backgroundImage;
//
//    public FlipAndFindGame() {
//        setTitle("Flip and Find");
//        setSize(1000, 800);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setResizable(false);
//
//        // ✅ Load background image from absolute path
//        backgroundImage = new ImageIcon("C:\\Users\\lenovo L390\\OneDrive\\Desktop\\background.jpg").getImage();
//
//        // Background panel
//        setContentPane(new JPanel() {
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//            }
//        });
//
//        setLayout(new BorderLayout());
//
//        JLabel title = new JLabel("Flip and Find", JLabel.CENTER);
//        title.setFont(new Font("Arial", Font.BOLD, 48));
//        title.setForeground(Color.YELLOW);
//        title.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 10));
//        add(title, BorderLayout.NORTH);
//
//        mainPanel = new JPanel();
//        mainPanel.setOpaque(false);
//        mainPanel.setLayout(new GridLayout(5, 1, 0, 20));
//
//        newGameBtn = createStyledButton("New Game");
//        resumeBtn = createStyledButton("Resume Game");
//        difficultyBtn = createStyledButton("Difficulty: Easy");
//        leaderboardBtn = createStyledButton("Leaderboard");
//        exitBtn = createStyledButton("Exit");
//
//        mainPanel.add(newGameBtn);
//        mainPanel.add(resumeBtn);
//        mainPanel.add(difficultyBtn);
//        mainPanel.add(leaderboardBtn);
//        mainPanel.add(exitBtn);
//
//        JPanel centerPanel = new JPanel(new GridBagLayout());
//        centerPanel.setOpaque(false);
//        centerPanel.add(mainPanel);
//        add(centerPanel, BorderLayout.CENTER);
//
//        newGameBtn.addActionListener(e -> startGame());
//        resumeBtn.addActionListener(e -> resumeGame());
//        difficultyBtn.addActionListener(e -> {
//            cycleDifficulty();
//            difficultyBtn.setText("Difficulty: " + difficulty);
//        });
//        leaderboardBtn.addActionListener(e -> showLeaderboard());
//        exitBtn.addActionListener(e -> System.exit(0));
//    }
//
//    private JButton createStyledButton(String text) {
//        JButton button = new JButton(text);
//        button.setPreferredSize(new Dimension(300, 50));
//        button.setFont(new Font("Arial", Font.BOLD, 22));
//        button.setFocusPainted(false);
//        button.setBackground(new Color(0, 102, 204));
//        button.setForeground(Color.WHITE);
//        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//
//        button.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                button.setBackground(new Color(0, 153, 255));
//            }
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                button.setBackground(new Color(0, 102, 204));
//            }
//        });
//
//        return button;
//    }
//
//    private void cycleDifficulty() {
//        switch (difficulty) {
//            case "Easy": difficulty = "Normal"; break;
//            case "Normal": difficulty = "Hard"; break;
//            case "Hard": difficulty = "Master"; break;
//            default: difficulty = "Easy"; break;
//        }
//    }
//
//    private void startGame() {
//        JOptionPane.showMessageDialog(this, "Game Started at " + difficulty + " level!", "Game", JOptionPane.INFORMATION_MESSAGE);
//        // Game logic to launch main board goes here
//    }
//
//    private void resumeGame() {
//        JOptionPane.showMessageDialog(this, "Resume game logic goes here.", "Resume", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    private void showLeaderboard() {
//        JOptionPane.showMessageDialog(this, "Leaderboard not yet implemented.", "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new FlipAndFindGame().setVisible(true);
//        });
//    }
//}


//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Scanner;
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class FlipAndFindGame extends JFrame {
//    private JPanel mainMenuPanel, gamePanel, cardPanel;
//    private JLabel timerLabel, titleLabel, pairsLabel;
//    private JComboBox<String> difficultyBox;
//    private javax.swing.Timer gameTimer;
//    private long startTime;
//    private Image backgroundImage;
//
//    private ArrayList<Card> cards = new ArrayList<>();
//    private ArrayList<Card> flippedCards = new ArrayList<>();
//    private ArrayList<ScoreEntry> leaderboard = new ArrayList<>();
//    private int pairCount = 4, cols = 4, matchedPairs = 0;
//    private String difficulty = "Easy";
//    private boolean gameWon = false;
//
//    public FlipAndFindGame() {
//        setTitle("Flip and Find");
//        setSize(900, 700);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setLayout(new CardLayout());
//
//        // Load background image
//        loadBackgroundImage();
//        
//        loadScores();
//        createMainMenu();
//        setVisible(true);
//    }
//
//    private void loadBackgroundImage() {
//        try {
//            ImageIcon icon = new ImageIcon("C:\\Users\\lenovo L390\\OneDrive\\Desktop\\background.jpg");
//            backgroundImage = icon.getImage();
//        } catch (Exception e) {
//            System.out.println("Could not load background image: " + e.getMessage());
//            backgroundImage = null;
//        }
//    }
//
//    private void createMainMenu() {
//        mainMenuPanel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                if (backgroundImage != null) {
//                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//                } else {
//                    // Fallback gradient background
//                    Graphics2D g2d = (Graphics2D) g;
//                    GradientPaint gradient = new GradientPaint(0, 0, new Color(75, 0, 130), 
//                                                             0, getHeight(), new Color(25, 25, 112));
//                    g2d.setPaint(gradient);
//                    g2d.fillRect(0, 0, getWidth(), getHeight());
//                }
//            }
//        };
//        
//        mainMenuPanel.setLayout(new BorderLayout());
//
//        // Title panel
//        JPanel titlePanel = new JPanel();
//        titlePanel.setOpaque(false);
//        titleLabel = new JLabel(" Flip & Find", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
//        titleLabel.setForeground(Color.YELLOW);
//        titlePanel.add(titleLabel);
//
//        // Center panel with buttons
//        JPanel centerPanel = new JPanel(new GridBagLayout());
//        centerPanel.setOpaque(false);
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//
//        // Difficulty selection
//        gbc.gridx = 0; gbc.gridy = 0;
//        JLabel diffLabel = new JLabel("Difficulty Level:");
//        diffLabel.setForeground(Color.WHITE);
//        diffLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        centerPanel.add(diffLabel, gbc);
//
//        gbc.gridx = 1;
//        difficultyBox = new JComboBox<>(new String[]{"Easy (8 cards)", "Normal (16 cards)", "Hard (32 cards)", "Master (64 cards)"});
//        difficultyBox.setPreferredSize(new Dimension(200, 30));
//        difficultyBox.setFont(new Font("Arial", Font.PLAIN, 14));
//        centerPanel.add(difficultyBox, gbc);
//
//        // Buttons
//        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
//        JButton newGameBtn = createStyledButton(" Start New Game");
//        newGameBtn.addActionListener(e -> startNewGame());
//        centerPanel.add(newGameBtn, gbc);
//
//        gbc.gridy = 2;
//        JButton rulesBtn = createStyledButton(" Game Rules");
//        rulesBtn.addActionListener(e -> showRules());
//        centerPanel.add(rulesBtn, gbc);
//
//        gbc.gridy = 3;
//        JButton leaderboardBtn = createStyledButton(" Leaderboard");
//        leaderboardBtn.addActionListener(e -> showLeaderboard());
//        centerPanel.add(leaderboardBtn, gbc);
//
//        gbc.gridy = 4;
//        JButton exitBtn = createStyledButton(" Exit");
//        exitBtn.addActionListener(e -> System.exit(0));
//        centerPanel.add(exitBtn, gbc);
//
//        mainMenuPanel.add(titlePanel, BorderLayout.NORTH);
//        mainMenuPanel.add(centerPanel, BorderLayout.CENTER);
//        add(mainMenuPanel, "Menu");
//    }
//
//    private JButton createStyledButton(String text) {
//        JButton button = new JButton(text);
//        button.setPreferredSize(new Dimension(250, 45));
//        button.setFont(new Font("Arial", Font.BOLD, 16));
//        button.setForeground(Color.WHITE);
//        button.setBackground(new Color(34, 139, 34)); // Forest Green
//        button.setFocusPainted(false);
//        button.setBorderPainted(false);
//        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        
//        button.addMouseListener(new MouseAdapter() {
//            public void mouseEntered(MouseEvent evt) {
//                button.setBackground(new Color(50, 205, 50)); // Lime Green
//            }
//            public void mouseExited(MouseEvent evt) {
//                button.setBackground(new Color(34, 139, 34)); // Forest Green
//            }
//        });
//        
//        return button;
//    }
//
//    private void startNewGame() {
//        String selectedDifficulty = (String) difficultyBox.getSelectedItem();
//        
//        if (selectedDifficulty.startsWith("Easy")) {
//            difficulty = "Easy";
//            pairCount = 4;
//            cols = 4;
//        } else if (selectedDifficulty.startsWith("Normal")) {
//            difficulty = "Normal";
//            pairCount = 8;
//            cols = 4;
//        } else if (selectedDifficulty.startsWith("Hard")) {
//            difficulty = "Hard";
//            pairCount = 16;
//            cols = 8;
//        } else {
//            difficulty = "Master";
//            pairCount = 32;
//            cols = 8;
//        }
//
//        initializeGame();
//    }
//
//    private void initializeGame() {
//        cards.clear();
//        flippedCards.clear();
//        matchedPairs = 0;
//        gameWon = false;
//
//        // Create pairs with numbers
//        ArrayList<Integer> pairIds = new ArrayList<>();
//        for (int i = 0; i < pairCount; i++) {
//            pairIds.add(i + 1); // Numbers start from 1
//            pairIds.add(i + 1);
//        }
//        Collections.shuffle(pairIds);
//
//        // Create game panel with background
//        gamePanel = new JPanel(new BorderLayout()) {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                if (backgroundImage != null) {
//                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//                } else {
//                    // Fallback gradient background
//                    Graphics2D g2d = (Graphics2D) g;
//                    GradientPaint gradient = new GradientPaint(0, 0, new Color(75, 0, 130), 
//                                                             0, getHeight(), new Color(25, 25, 112));
//                    g2d.setPaint(gradient);
//                    g2d.fillRect(0, 0, getWidth(), getHeight());
//                }
//            }
//        };
//
//        // Top panel with game info
//        JPanel topPanel = new JPanel(new FlowLayout());
//        topPanel.setOpaque(false);
//        
//        JLabel gameTitle = new JLabel(" Flip And Find - " + difficulty + " Mode");
//        gameTitle.setFont(new Font("Arial", Font.BOLD, 20));
//        gameTitle.setForeground(Color.WHITE);
//        
//        timerLabel = new JLabel("Time: 0s");
//        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        timerLabel.setForeground(Color.YELLOW);
//        
//        pairsLabel = new JLabel("Pairs: 0/" + pairCount);
//        pairsLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        pairsLabel.setForeground(Color.CYAN);
//        
//        JButton menuBtn = new JButton(" Menu");
//        menuBtn.setFont(new Font("Arial", Font.BOLD, 14));
//        menuBtn.addActionListener(e -> returnToMenu());
//        
//        topPanel.add(gameTitle);
//        topPanel.add(Box.createHorizontalStrut(20));
//        topPanel.add(timerLabel);
//        topPanel.add(Box.createHorizontalStrut(20));
//        topPanel.add(pairsLabel);
//        topPanel.add(Box.createHorizontalStrut(20));
//        topPanel.add(menuBtn);
//
//        // Card panel
//        cardPanel = new JPanel(new GridLayout(0, cols, 10, 10));
//        cardPanel.setOpaque(false);
//        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        // Create cards with numbers
//        for (int i = 0; i < pairIds.size(); i++) {
//            Card card = new Card(i, pairIds.get(i));
//            cards.add(card);
//            cardPanel.add(card.getButton());
//        }
//
//        gamePanel.add(topPanel, BorderLayout.NORTH);
//        gamePanel.add(cardPanel, BorderLayout.CENTER);
//
//        add(gamePanel, "Game");
//        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Game");
//
//        // Start timer
//        startTime = System.currentTimeMillis();
//        if (gameTimer != null) gameTimer.stop();
//        
//        gameTimer = new javax.swing.Timer(1000, e -> {
//            if (!gameWon) {
//                long now = System.currentTimeMillis();
//                int seconds = (int)((now - startTime) / 1000);
//                timerLabel.setText("Time: " + seconds + "s");
//            }
//        });
//        gameTimer.start();
//    }
//
//    private void handleCardClick(Card clickedCard) {
//        if (gameWon || clickedCard.isRevealed() || clickedCard.isMatched() || flippedCards.size() >= 2) {
//            return;
//        }
//
//        // Reveal the card immediately when clicked
//        clickedCard.reveal();
//        flippedCards.add(clickedCard);
//
//        if (flippedCards.size() == 2) {
//            Card firstCard = flippedCards.get(0);
//            Card secondCard = flippedCards.get(1);
//
//            if (firstCard.getPairId() == secondCard.getPairId()) {
//                // Match found! Keep cards revealed and mark as matched
//                javax.swing.Timer matchTimer = new javax.swing.Timer(500, e -> {
//                    firstCard.setMatched();
//                    secondCard.setMatched();
//                    flippedCards.clear();
//                    matchedPairs++;
//                    pairsLabel.setText("Pairs: " + matchedPairs + "/" + pairCount);
//                    
//                    // Check for win condition
//                    if (matchedPairs == pairCount) {
//                        gameWon = true;
//                        gameTimer.stop();
//                        float gameTime = (System.currentTimeMillis() - startTime) / 1000f;
//                        leaderboard.add(new ScoreEntry(gameTime, difficulty));
//                        saveScores();
//                        
//                        SwingUtilities.invokeLater(() -> {
//                            JOptionPane.showMessageDialog(this, 
//                                " Congratulations!\n\nYou completed the " + difficulty + " level!\n" +
//                                "Time: " + String.format("%.1f", gameTime) + " seconds", 
//                                "Victory!", 
//                                JOptionPane.INFORMATION_MESSAGE);
//                        });
//                    }
//                });
//                matchTimer.setRepeats(false);
//                matchTimer.start();
//            } else {
//                // No match - flip cards back after delay
//                javax.swing.Timer flipBackTimer = new javax.swing.Timer(1000, e -> {
//                    firstCard.hide();
//                    secondCard.hide();
//                    flippedCards.clear();
//                });
//                flipBackTimer.setRepeats(false);
//                flipBackTimer.start();
//            }
//        }
//    }
//
//    private void returnToMenu() {
//        if (gameTimer != null) gameTimer.stop();
//        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Menu");
//        if (gamePanel != null) {
//            remove(gamePanel);
//        }
//        revalidate();
//        repaint();
//    }
//
//    private void showRules() {
//        JOptionPane.showMessageDialog(this,
//            " Flip and Find Rules \n\n" +
//            "• Click on cards to reveal their numbers\n" +
//            "• Find matching pairs of numbers\n" +
//            "• Matched pairs will stay revealed and turn green\n" +
//            "• Match all pairs to win the game\n" +
//            "• Your time is recorded for the leaderboard\n\n" +
//            "Difficulty Levels:\n" +
//            "• Easy: 8 cards (4 pairs)\n" +
//            "• Normal: 16 cards (8 pairs)\n" +
//            "• Hard: 32 cards (16 pairs)\n" +
//            "• Master: 64 cards (32 pairs)", 
//            "Game Rules", 
//            JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    private void showLeaderboard() {
//        Collections.sort(leaderboard);
//        StringBuilder sb = new StringBuilder("🏆 Top Scores:\n\n");
//        
//        if (leaderboard.isEmpty()) {
//            sb.append("No scores yet! Play a game to set a record.");
//        } else {
//            for (int i = 0; i < Math.min(15, leaderboard.size()); i++) {
//                ScoreEntry entry = leaderboard.get(i);
//                sb.append(String.format("#%d. %.1fs - %s\n", i+1, entry.getTime(), entry.getDifficulty()));
//            }
//        }
//        
//        JOptionPane.showMessageDialog(this, sb.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    private void saveScores() {
//        try (PrintWriter pw = new PrintWriter("leaderboard.txt")) {
//            for (ScoreEntry entry : leaderboard) {
//                pw.println(entry.getTime() + " " + entry.getDifficulty());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void loadScores() {
//        leaderboard.clear();
//        File file = new File("leaderboard.txt");
//        if (!file.exists()) return;
//
//        try (Scanner sc = new Scanner(file)) {
//            while (sc.hasNextFloat()) {
//                float time = sc.nextFloat();
//                String diff = sc.next();
//                leaderboard.add(new ScoreEntry(time, diff));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Inner Card class
//    private class Card {
//        private int id;
//        private int pairId;
//        private JButton button;
//        private boolean revealed = false;
//        private boolean matched = false;
//
//        public Card(int id, int pairId) {
//            this.id = id;
//            this.pairId = pairId;
//            
//            button = new JButton("?");
//            button.setFont(new Font("Arial", Font.BOLD, 32));
//            button.setPreferredSize(new Dimension(80, 80));
//            button.setBackground(new Color(70, 130, 180)); // Steel Blue
//            button.setForeground(Color.WHITE);
//            button.setFocusPainted(false);
//            button.setBorderPainted(true);
//            button.setBorder(BorderFactory.createRaisedBevelBorder());
//            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//            
//            button.addActionListener(e -> handleCardClick(this));
//            
//            button.addMouseListener(new MouseAdapter() {
//                public void mouseEntered(MouseEvent evt) {
//                    if (!revealed && !matched) {
//                        button.setBackground(new Color(100, 160, 210));
//                    }
//                }
//                public void mouseExited(MouseEvent evt) {
//                    if (!revealed && !matched) {
//                        button.setBackground(new Color(70, 130, 180));
//                    }
//                }
//            });
//        }
//
//        public void reveal() {
//            revealed = true;
//            button.setText(String.valueOf(pairId)); // Show the number
//            button.setBackground(new Color(65, 105, 225)); // Royal Blue
//            button.setForeground(Color.WHITE);
//        }
//
//        public void hide() {
//            revealed = false;
//            button.setText("?");
//            button.setBackground(new Color(70, 130, 180)); // Steel Blue
//            button.setForeground(Color.WHITE);
//        }
//
//        public void setMatched() {
//            matched = true;
//            revealed = true;
//            button.setText(String.valueOf(pairId)); // Keep showing the number
//            button.setBackground(new Color(34, 139, 34)); // Forest Green - MATCHED COLOR
//            button.setForeground(Color.WHITE);
//            button.setBorder(BorderFactory.createLoweredBevelBorder());
//        }
//
//        // Getters
//        public JButton getButton() { return button; }
//        public int getPairId() { return pairId; }
//        public boolean isRevealed() { return revealed; }
//        public boolean isMatched() { return matched; }
//    }
//
//    // ScoreEntry class
//    private static class ScoreEntry implements Comparable<ScoreEntry> {
//        private float time;
//        private String difficulty;
//
//        public ScoreEntry(float time, String difficulty) {
//            this.time = time;
//            this.difficulty = difficulty;
//        }
//
//        public float getTime() { return time; }
//        public String getDifficulty() { return difficulty; }
//
//        @Override
//        public int compareTo(ScoreEntry other) {
//            return Float.compare(this.time, other.time);
//        }
//
//        @Override
//        public String toString() {
//            return String.format("%.1fs - %s", time, difficulty);
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            new FlipAndFindGame();
//        });
//    }
//    
//
//}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FlipAndFindGame extends JFrame {
    private JPanel mainMenuPanel, gamePanel, cardPanel;
    private JLabel timerLabel, titleLabel, pairsLabel, coinsLabel;
    private JComboBox<String> difficultyBox;
    private javax.swing.Timer gameTimer;
    private long startTime;
    private Image backgroundImage;

    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Card> flippedCards = new ArrayList<>();
    private ArrayList<ScoreEntry> leaderboard = new ArrayList<>();
    private int pairCount = 4, cols = 4, matchedPairs = 0;
    private String difficulty = "Easy";
    private boolean gameWon = false;
    
    // Coin system variables
    private int totalCoins = 0;
    private int gameCoins = 0; // Coins earned in current game

    public FlipAndFindGame() {
        setTitle("Flip and Find");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        // Load background image
        loadBackgroundImage();
        
        loadScores();
        loadCoins(); // Load saved coins
        createMainMenu();
        setVisible(true);
    }

    private void loadBackgroundImage() {
        try {
            ImageIcon icon = new ImageIcon("C:\\Users\\lenovo L390\\OneDrive\\Desktop\\background.jpg");
            backgroundImage = icon.getImage();
        } catch (Exception e) {
            System.out.println("Could not load background image: " + e.getMessage());
            backgroundImage = null;
        }
    }

    private void createMainMenu() {
        mainMenuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback gradient background
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(75, 0, 130), 
                                                             0, getHeight(), new Color(25, 25, 112));
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        mainMenuPanel.setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titleLabel = new JLabel(" Flip & Find", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        
        // Add coins display to title panel
        JLabel coinsDisplay = new JLabel("💰 Coins: " + totalCoins, SwingConstants.CENTER);
        coinsDisplay.setFont(new Font("Arial", Font.BOLD, 20));
        coinsDisplay.setForeground(new Color(212, 175, 55));
        
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(coinsDisplay, BorderLayout.SOUTH);

        // Center panel with buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Difficulty selection
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel diffLabel = new JLabel("Difficulty Level:");
        diffLabel.setForeground(Color.WHITE);
        diffLabel.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(diffLabel, gbc);

        gbc.gridx = 1;
        difficultyBox = new JComboBox<>(new String[]{"Easy (8 cards)", "Normal (16 cards)", "Hard (32 cards)", "Master (64 cards)"});
        difficultyBox.setPreferredSize(new Dimension(200, 30));
        difficultyBox.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(difficultyBox, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JButton newGameBtn = createStyledButton(" Start New Game");
        newGameBtn.addActionListener(e -> startNewGame());
        centerPanel.add(newGameBtn, gbc);

        gbc.gridy = 2;
        JButton rulesBtn = createStyledButton(" Game Rules");
        rulesBtn.addActionListener(e -> showRules());
        centerPanel.add(rulesBtn, gbc);

        gbc.gridy = 3;
        JButton leaderboardBtn = createStyledButton(" Leaderboard");
        leaderboardBtn.addActionListener(e -> showLeaderboard());
        centerPanel.add(leaderboardBtn, gbc);

        gbc.gridy = 4;
        JButton exitBtn = createStyledButton(" Exit");
        exitBtn.addActionListener(e -> System.exit(0));
        centerPanel.add(exitBtn, gbc);

        mainMenuPanel.add(titlePanel, BorderLayout.NORTH);
        mainMenuPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainMenuPanel, "Menu");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 45));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(34, 139, 34)); // Forest Green
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(50, 205, 50)); // Lime Green
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(34, 139, 34)); // Forest Green
            }
        });
        
        return button;
    }

    private void startNewGame() {
        String selectedDifficulty = (String) difficultyBox.getSelectedItem();
        
        if (selectedDifficulty.startsWith("Easy")) {
            difficulty = "Easy";
            pairCount = 4;
            cols = 4;
        } else if (selectedDifficulty.startsWith("Normal")) {
            difficulty = "Normal";
            pairCount = 8;
            cols = 4;
        } else if (selectedDifficulty.startsWith("Hard")) {
            difficulty = "Hard";
            pairCount = 16;
            cols = 8;
        } else {
            difficulty = "Master";
            pairCount = 32;
            cols = 8;
        }

        gameCoins = 0; // Reset game coins
        initializeGame();
    }

    private void initializeGame() {
        cards.clear();
        flippedCards.clear();
        matchedPairs = 0;
        gameWon = false;

        // Create pairs with numbers
        ArrayList<Integer> pairIds = new ArrayList<>();
        for (int i = 0; i < pairCount; i++) {
            pairIds.add(i + 1); // Numbers start from 1
            pairIds.add(i + 1);
        }
        Collections.shuffle(pairIds);

        // Create game panel with background
        gamePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback gradient background
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(75, 0, 130), 
                                                             0, getHeight(), new Color(25, 25, 112));
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        // Top panel with game info
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setOpaque(false);
        
        JLabel gameTitle = new JLabel(" Flip And Find - " + difficulty + " Mode");
        gameTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gameTitle.setForeground(Color.WHITE);
        
        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.YELLOW);
        
        pairsLabel = new JLabel("Pairs: 0/" + pairCount);
        pairsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        pairsLabel.setForeground(Color.CYAN);
        
        coinsLabel = new JLabel("💰 " + totalCoins + " (+0)");
        coinsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        coinsLabel.setForeground(new Color(212, 175, 55));
        
        JButton hintBtn = new JButton("💡 Hint");
        hintBtn.setFont(new Font("Arial", Font.BOLD, 14));
        hintBtn.addActionListener(e -> showHintOptions());
        
        JButton menuBtn = new JButton(" Menu");
        menuBtn.setFont(new Font("Arial", Font.BOLD, 14));
        menuBtn.addActionListener(e -> returnToMenu());
        
        topPanel.add(gameTitle);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(timerLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(pairsLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(coinsLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(hintBtn);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(menuBtn);

        // Card panel
        cardPanel = new JPanel(new GridLayout(0, cols, 10, 10));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create cards with numbers
        for (int i = 0; i < pairIds.size(); i++) {
            Card card = new Card(i, pairIds.get(i));
            cards.add(card);
            cardPanel.add(card.getButton());
        }

        gamePanel.add(topPanel, BorderLayout.NORTH);
        gamePanel.add(cardPanel, BorderLayout.CENTER);

        add(gamePanel, "Game");
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Game");

        // Start timer
        startTime = System.currentTimeMillis();
        if (gameTimer != null) gameTimer.stop();
        
        gameTimer = new javax.swing.Timer(1000, e -> {
            if (!gameWon) {
                long now = System.currentTimeMillis();
                int seconds = (int)((now - startTime) / 1000);
                timerLabel.setText("Time: " + seconds + "s");
            }
        });
        gameTimer.start();
    }

    private void showHintOptions() {
        if (gameWon) return;
        
        String[] options = {"Basic Hint (10 coins)", "Precise Hint (30 coins)", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this,
            "Choose hint type:\n\n" +
            "Basic Hint (10 coins): Reveals general area with matches\n" +
            "Precise Hint (30 coins): Shows exact location of a matching pair\n\n" +
            "Your coins: " + totalCoins,
            "Hint Options",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2]);
            
        if (choice == 0) { // Basic hint
            if (totalCoins >= 10) {
                totalCoins -= 10;
                updateCoinsDisplay();
                saveCoins();
                showBasicHint();
            } else {
                JOptionPane.showMessageDialog(this, "Not enough coins! You need 10 coins for a basic hint.", 
                    "Insufficient Coins", JOptionPane.WARNING_MESSAGE);
            }
        } else if (choice == 1) { // Precise hint
            if (totalCoins >= 30) {
                totalCoins -= 30;
                updateCoinsDisplay();
                saveCoins();
                showPreciseHint();
            } else {
                JOptionPane.showMessageDialog(this, "Not enough coins! You need 30 coins for a precise hint.", 
                    "Insufficient Coins", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void showBasicHint() {
        // Find unmatched pairs and highlight a general area
        ArrayList<Card> unmatchedCards = new ArrayList<>();
        for (Card card : cards) {
            if (!card.isMatched()) {
                unmatchedCards.add(card);
            }
        }
        
        if (unmatchedCards.isEmpty()) return;
        
        // Find a pair that exists
        for (int i = 0; i < unmatchedCards.size(); i++) {
            for (int j = i + 1; j < unmatchedCards.size(); j++) {
                if (unmatchedCards.get(i).getPairId() == unmatchedCards.get(j).getPairId()) {
                    // Highlight these cards briefly
                    Card card1 = unmatchedCards.get(i);
                    Card card2 = unmatchedCards.get(j);
                    
                    // Change border color to indicate hint
                    card1.getButton().setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
                    card2.getButton().setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
                    
                    javax.swing.Timer hintTimer = new javax.swing.Timer(3000, e -> {
                        card1.getButton().setBorder(BorderFactory.createRaisedBevelBorder());
                        card2.getButton().setBorder(BorderFactory.createRaisedBevelBorder());
                    });
                    hintTimer.setRepeats(false);
                    hintTimer.start();
                    
                    JOptionPane.showMessageDialog(this, "💡 Basic Hint: Look for the highlighted cards!", 
                        "Hint", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }
    }

    private void showPreciseHint() {
        // Find unmatched pairs and reveal one pair briefly
        ArrayList<Card> unmatchedCards = new ArrayList<>();
        for (Card card : cards) {
            if (!card.isMatched()) {
                unmatchedCards.add(card);
            }
        }
        
        if (unmatchedCards.isEmpty()) return;
        
        // Find a pair that exists
        for (int i = 0; i < unmatchedCards.size(); i++) {
            for (int j = i + 1; j < unmatchedCards.size(); j++) {
                if (unmatchedCards.get(i).getPairId() == unmatchedCards.get(j).getPairId()) {
                    Card card1 = unmatchedCards.get(i);
                    Card card2 = unmatchedCards.get(j);
                    
                    // Temporarily reveal the cards
                    String originalText1 = card1.getButton().getText();
                    String originalText2 = card2.getButton().getText();
                    Color originalBg1 = card1.getButton().getBackground();
                    Color originalBg2 = card2.getButton().getBackground();
                    
                    card1.getButton().setText(String.valueOf(card1.getPairId()));
                    card2.getButton().setText(String.valueOf(card2.getPairId()));
                    card1.getButton().setBackground(Color.ORANGE);
                    card2.getButton().setBackground(Color.ORANGE);
                    
                    javax.swing.Timer hintTimer = new javax.swing.Timer(2000, e -> {
                        card1.getButton().setText(originalText1);
                        card2.getButton().setText(originalText2);
                        card1.getButton().setBackground(originalBg1);
                        card2.getButton().setBackground(originalBg2);
                    });
                    hintTimer.setRepeats(false);
                    hintTimer.start();
                    
                    JOptionPane.showMessageDialog(this, "💡 Precise Hint: These cards match! Remember their positions.", 
                        "Hint", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }
    }

    private void handleCardClick(Card clickedCard) {
        if (gameWon || clickedCard.isRevealed() || clickedCard.isMatched() || flippedCards.size() >= 2) {
            return;
        }

        // Reveal the card immediately when clicked
        clickedCard.reveal();
        flippedCards.add(clickedCard);

        if (flippedCards.size() == 2) {
            Card firstCard = flippedCards.get(0);
            Card secondCard = flippedCards.get(1);

            if (firstCard.getPairId() == secondCard.getPairId()) {
                // Match found! Award coins and keep cards revealed
                gameCoins += 10;
                totalCoins += 10;
                updateCoinsDisplay();
                
                javax.swing.Timer matchTimer = new javax.swing.Timer(500, e -> {
                    firstCard.setMatched();
                    secondCard.setMatched();
                    flippedCards.clear();
                    matchedPairs++;
                    pairsLabel.setText("Pairs: " + matchedPairs + "/" + pairCount);
                    
                    // Check for win condition
                    if (matchedPairs == pairCount) {
                        gameWon = true;
                        gameTimer.stop();
                        float gameTime = (System.currentTimeMillis() - startTime) / 1000f;
                        
                        // Award completion bonus
                        gameCoins += 100;
                        totalCoins += 100;
                        updateCoinsDisplay();
                        saveCoins();
                        
                        leaderboard.add(new ScoreEntry(gameTime, difficulty));
                        saveScores();
                        
                        SwingUtilities.invokeLater(() -> {
                            showVictoryDialog(gameTime);
                        });
                    }
                });
                matchTimer.setRepeats(false);
                matchTimer.start();
            } else {
                // No match - flip cards back after delay
                javax.swing.Timer flipBackTimer = new javax.swing.Timer(1000, e -> {
                    firstCard.hide();
                    secondCard.hide();
                    flippedCards.clear();
                });
                flipBackTimer.setRepeats(false);
                flipBackTimer.start();
            }
        }
    }

    private void showVictoryDialog(float gameTime) {
        String message = String.format(
            "🎉 CONGRATULATIONS! 🎉\n\n" +
            "You completed the %s level!\n\n" +
            "⏱️ Time: %.1f seconds\n" +
            "💰 Coins earned this game: %d\n" +
            "💰 Total coins: %d\n\n" +
            "Great job! Keep playing to earn more coins!",
            difficulty, gameTime, gameCoins, totalCoins
        );
        
        JOptionPane.showMessageDialog(this, message, "Victory!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateCoinsDisplay() {
        if (coinsLabel != null) {
            coinsLabel.setText("💰 " + totalCoins + " (+" + gameCoins + ")");
        }
    }

    private void saveCoins() {
        try (PrintWriter pw = new PrintWriter("coins.txt")) {
            pw.println(totalCoins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCoins() {
        File file = new File("coins.txt");
        if (!file.exists()) {
            totalCoins = 0;
            return;
        }

        try (Scanner sc = new Scanner(file)) {
            if (sc.hasNextInt()) {
                totalCoins = sc.nextInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
            totalCoins = 0;
        }
    }

    private void returnToMenu() {
        if (gameTimer != null) gameTimer.stop();
        saveCoins(); // Save coins when returning to menu
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Menu");
        if (gamePanel != null) {
            remove(gamePanel);
        }
        // Update coins display in main menu
        Component[] components = mainMenuPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Component[] subComponents = panel.getComponents();
                for (Component subComp : subComponents) {
                    if (subComp instanceof JLabel && ((JLabel) subComp).getText().contains("Coins:")) {
                        ((JLabel) subComp).setText("💰 Coins: " + totalCoins);
                        break;
                    }
                }
            }
        }
        revalidate();
        repaint();
    }

    private void showRules() {
        JOptionPane.showMessageDialog(this,
            " Flip and Find Rules \n\n" +
            "• Click on cards to reveal their numbers\n" +
            "• Find matching pairs of numbers\n" +
            "• Matched pairs will stay revealed and turn green\n" +
            "• Match all pairs to win the game\n" +
            "• Your time is recorded for the leaderboard\n\n" +
            "💰 Coin System:\n" +
            "• Earn 10 coins for each correct match\n" +
            "• Earn 100 coins for completing a game\n" +
            "• Use coins for hints during gameplay\n\n" +
            "💡 Hints:\n" +
            "• Basic Hint (10 coins): Highlights matching cards\n" +
            "• Precise Hint (30 coins): Briefly reveals a matching pair\n\n" +
            "Difficulty Levels:\n" +
            "• Easy: 8 cards (4 pairs)\n" +
            "• Normal: 16 cards (8 pairs)\n" +
            "• Hard: 32 cards (16 pairs)\n" +
            "• Master: 64 cards (32 pairs)", 
            "Game Rules", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void showLeaderboard() {
        Collections.sort(leaderboard);
        StringBuilder sb = new StringBuilder("🏆 Top Scores:\n\n");
        
        if (leaderboard.isEmpty()) {
            sb.append("No scores yet! Play a game to set a record.");
        } else {
            for (int i = 0; i < Math.min(15, leaderboard.size()); i++) {
                ScoreEntry entry = leaderboard.get(i);
                sb.append(String.format("#%d. %.1fs - %s\n", i+1, entry.getTime(), entry.getDifficulty()));
            }
        }
        
        JOptionPane.showMessageDialog(this, sb.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveScores() {
        try (PrintWriter pw = new PrintWriter("leaderboard.txt")) {
            for (ScoreEntry entry : leaderboard) {
                pw.println(entry.getTime() + " " + entry.getDifficulty());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScores() {
        leaderboard.clear();
        File file = new File("leaderboard.txt");
        if (!file.exists()) return;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextFloat()) {
                float time = sc.nextFloat();
                String diff = sc.next();
                leaderboard.add(new ScoreEntry(time, diff));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Inner Card class
    private class Card {
        private int id;
        private int pairId;
        private JButton button;
        private boolean revealed = false;
        private boolean matched = false;

        public Card(int id, int pairId) {
            this.id = id;
            this.pairId = pairId;
            
            button = new JButton("?");
            button.setFont(new Font("Arial", Font.BOLD, 32));
            button.setPreferredSize(new Dimension(80, 80));
            button.setBackground(new Color(70, 130, 180)); // Steel Blue
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(true);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            button.addActionListener(e -> handleCardClick(this));
            
            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    if (!revealed && !matched) {
                        button.setBackground(new Color(100, 160, 210));
                    }
                }
                public void mouseExited(MouseEvent evt) {
                    if (!revealed && !matched) {
                        button.setBackground(new Color(70, 130, 180));
                    }
                }
            });
        }

        public void reveal() {
            revealed = true;
            button.setText(String.valueOf(pairId)); // Show the number
            button.setBackground(new Color(65, 105, 225)); // Royal Blue
            button.setForeground(Color.WHITE);
        }

        public void hide() {
            revealed = false;
            button.setText("?");
            button.setBackground(new Color(70, 130, 180)); // Steel Blue
            button.setForeground(Color.WHITE);
        }

        public void setMatched() {
            matched = true;
            revealed = true;
            button.setText(String.valueOf(pairId)); // Keep showing the number
            button.setBackground(new Color(34, 139, 34)); // Forest Green - MATCHED COLOR
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createLoweredBevelBorder());
        }

        // Getters
        public JButton getButton() { return button; }
        public int getPairId() { return pairId; }
        public boolean isRevealed() { return revealed; }
        public boolean isMatched() { return matched; }
    }

    // ScoreEntry class
    private static class ScoreEntry implements Comparable<ScoreEntry> {
        private float time;
        private String difficulty;

        public ScoreEntry(float time, String difficulty) {
            this.time = time;
            this.difficulty = difficulty;
        }

        public float getTime() { return time; }
        public String getDifficulty() { return difficulty; }

        @Override
        public int compareTo(ScoreEntry other) {
            return Float.compare(this.time, other.time);
        }

        @Override
        public String toString() {
            return String.format("%.1fs - %s", time, difficulty);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
         try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new FlipAndFindGame();
        });
    }
}