package find_and_flip;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class LeaderboardDialog {
    public static void showLeaderboard(List<ScoreEntry> leaderboard, JFrame parent) {
        Collections.sort(leaderboard);
        StringBuilder sb = new StringBuilder("Top 15 Scores:\n\n");
        for (int i = 0; i < Math.min(15, leaderboard.size()); ++i) {
            sb.append((i + 1)).append(". ").append(leaderboard.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(parent, sb.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }
}
