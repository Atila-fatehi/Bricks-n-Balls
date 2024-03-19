package Pages;

import Music.MusicPlayer;
import Panels.GameArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Game extends JFrame {
    final int SCREEN_WIDTH = 600;
    final int SCREEN_HEIGHT = 900;

    public Game(MusicPlayer musicPlayer) {
        getContentPane().setBackground(new Color(0xA6C8EA));
        setTitle("Brick Breaker +");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        //game area
        GameArea gameArea = new GameArea();
        gameArea.setBackground(new Color(0xA6C8EA));
        gameArea.setBounds(0, 80, 600, 710);
        add(gameArea);

        //Top Panel for pause , time , score

        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(10, 10, 563, 70);
        topPanel.setBackground(new Color(0x002A5A));
        ImageIcon x = new ImageIcon(Paths.get("").toAbsolutePath() + "\\src\\images\\pause.png");
        Image img = x.getImage();
        Image newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        JButton pause = new JButton(newIcon);
        pause.setBackground(new Color(0xA6C8EA));
        pause.setFocusable(false);
        pause.setBounds(7, 7, 56, 56);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.pauseGame();
            }
        });

        x = new ImageIcon(Paths.get("").toAbsolutePath() + "\\src\\images\\back.png");
        img = x.getImage();
        newimg = img.getScaledInstance(56, 56, java.awt.Image.SCALE_SMOOTH);
        newIcon = new ImageIcon(newimg);
        JButton back = new JButton(newIcon);
        back.setBackground(new Color(0xA6C8EA));
        back.setFocusable(false);
        back.setBounds(7 + 56 + 5, 7, 56, 56);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StartPage(musicPlayer);
            }
        });

        topPanel.add(back);
        topPanel.add(pause);

        JLabel time = new JLabel("0");
        time.setForeground(new Color(0xA6C8EA));
        time.setBounds(500, 7, 56, 56);
        time.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 35));
        topPanel.add(time);

        JLabel score = new JLabel("0");
        score.setForeground(new Color(0xA6C8EA));
        score.setBounds(280, 7, 56, 56);
        score.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 35));
        topPanel.add(score);
        //game area

        //bottom panel for ball count
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setBounds(10, 785, 563, 70);
        bottomPanel.setBackground(new Color(0x002A5A));

        JLabel ballCount = new JLabel("0");
        ballCount.setForeground(new Color(0xA6C8EA));
        ballCount.setBounds(280, 7, 56, 56);
        ballCount.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 35));
        bottomPanel.add(ballCount);


        //add
        add(topPanel);
        add(bottomPanel);

    }


}
