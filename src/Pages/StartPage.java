package Pages;

import Music.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class StartPage extends JFrame {
    public StartPage(MusicPlayer musicPlayer) {
        getContentPane().setBackground(new Color(0xA6C8EA));
        setTitle("Swipe Brick Breaker+");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        int highScore = 0;

        JLabel label = new JLabel("HighScore : " + String.valueOf(highScore));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(100, 100, 300, 100);
        label.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label.setForeground(new Color(0x002A5A));

        JButton button1 = new JButton("New Game");
        button1.setBounds(100, 200, 300, 50);
        button1.setFocusable(false);
        button1.setHorizontalAlignment(JButton.CENTER);
        button1.setHorizontalTextPosition(JButton.CENTER);
        button1.setBackground(new Color(0x002A5A));
        button1.setForeground(new Color(0x7AB2E1));
        button1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GamePrepPage(musicPlayer);
            }
        });

        JButton button2 = new JButton("History");
        button2.setBounds(100, 270, 300, 50);
        button2.setFocusable(false);
        button2.setHorizontalAlignment(JButton.CENTER);
        button2.setHorizontalTextPosition(JButton.CENTER);
        button2.setBackground(new Color(0x002A5A));
        button2.setForeground(new Color(0x7AB2E1));
        button2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HistoryPage(musicPlayer);
            }
        });

        JButton button3 = new JButton("Settings");
        button3.setBounds(100, 340, 300, 50);
        button3.setFocusable(false);
        button3.setHorizontalAlignment(JButton.CENTER);
        button3.setHorizontalTextPosition(JButton.CENTER);
        button3.setBackground(new Color(0x002A5A));
        button3.setForeground(new Color(0x7AB2E1));
        button3.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new SettingPage(musicPlayer);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton button4 = new JButton("Exit");
        button4.setBounds(100, 410, 300, 50);
        button4.setFocusable(false);
        button4.setHorizontalAlignment(JButton.CENTER);
        button4.setHorizontalTextPosition(JButton.CENTER);
        button4.setBackground(new Color(0x002A5A));
        button4.setForeground(new Color(0x7AB2E1));
        button4.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(label);


        setVisible(true);
    }


}
