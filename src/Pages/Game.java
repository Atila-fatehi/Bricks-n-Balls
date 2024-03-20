package Pages;

import Music.MusicPlayer;
import Panels.GameArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JFrame {
    final int SCREEN_WIDTH = 453;
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
        gameArea.setBounds(0, 70, 600, 720);
        add(gameArea);

        //Top Panel for pause , time , score

        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, 600, 70);
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
        newimg = img.getScaledInstance(53, 53, java.awt.Image.SCALE_SMOOTH);
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
        topPanel. setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topPanel.add(back);
        topPanel.add(pause);

        JLabel time = new JLabel("0");
        time.setForeground(new Color(0xA6C8EA));
        time.setBounds(380, 7, 56, 56);
        time.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        topPanel.add(time);



        JLabel score = new JLabel("score : 0");
        score.setForeground(new Color(0xA6C8EA));
        score.setBounds(150, 7, 300, 56);
        score.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        topPanel.add(score);

        //bottom panel for ball count
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setBounds(0, 785, 600, 80);
        bottomPanel.setBackground(new Color(0x002A5A));

        JLabel ballCount = new JLabel("ball count : 1");
        ballCount.setForeground(new Color(0xA6C8EA));
        ballCount.setBounds(150, 7, 400, 56);
        ballCount.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));

        java.util.Timer seconds = new Timer();
        seconds.schedule(new TimerTask() {
            int s = 0;
            @Override
            public void run() {
                s++;
                time.setText(String.valueOf(s));
                File file = new File(Paths.get("").toAbsolutePath() + "\\src\\DataBase\\gameStatus.txt");
                if (file.exists()) {
                    try{
                        Scanner scanner = new Scanner(file);
                        score.setText("Score : " + scanner.nextLine());
                        ballCount.setText("ball count : " + scanner.nextLine());
                    }catch (Exception e){

                    }
                } else {

                }
            }
        } , 1000 , 1000);
        bottomPanel.add(ballCount);


        //add
        add(topPanel);
        add(bottomPanel);

    }


}
