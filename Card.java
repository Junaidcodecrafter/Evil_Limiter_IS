package find_and_flip;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class Card {
//    public int id;
//    public boolean revealed = false;
//    public boolean matched = false;
//    public JButton button;
//
//    public Card(int id) {
//        this.id = id;
//        button = new JButton("?");
//        button.setFont(new Font("Arial", Font.BOLD, 18));
//        button.setBackground(Color.BLUE);
//        button.setForeground(Color.WHITE);
//    }
//
//    public void reveal() {
//        button.setText(String.valueOf(id));
//        revealed = true;
//        button.setBackground(Color.GREEN);
//    }
//
//    public void hide() {
//        button.setText("?");
//        revealed = false;
//        button.setBackground(Color.BLUE);
//    }
//
//    public void match() {
//        matched = true;
//        button.setBackground(Color.GRAY);
//    }
//}
import javax.swing.*;
import java.awt.*;

public class Card {
    public int id;
    public boolean revealed = false;
    public boolean matched = false;
    public JButton button;

    public Card(int id) {
        this.id = id;
        button = new JButton();
        button.setPreferredSize(new Dimension(80, 100));
        hide();
    }

    public void reveal() {
        revealed = true;
        button.setText(String.valueOf(id));
        button.setBackground(new Color(0, 200, 0));
    }

    public void hide() {
        revealed = false;
        button.setText("");
        button.setBackground(new Color(40, 40, 120));
    }

    public void match() {
        matched = true;
        button.setBackground(new Color(0, 100, 0));
        button.setText(String.valueOf(id));
    }
}
