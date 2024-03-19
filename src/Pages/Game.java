package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

public class Game extends JFrame {
    public Game() {

        getContentPane().setBackground(new Color(0xA6C8EA));
        setTitle("Brick Breaker +");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);

    }
}
