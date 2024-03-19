package Panels;

import Graphic.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameArea extends JPanel {
    int difficulty;
    int red;
    int green;
    int blue;
    Color ballColor;
    final int WIDTH = 300;
    final int HEIGHT = 700;
    Line line;
    boolean aim;
    boolean gameRunning;
    Timer timer;

    public GameArea() {
        fileStuff();
        ballColor = new Color(red , green , blue);
        line = new Line(300, 700, 0, 0);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double angle = Math.atan2(e.getY() - 700, e.getX() - 300);
                line.setX2((int) Math.round(Math.cos(angle) * 1000000));
                line.setY2((int) Math.round(Math.sin(angle) * 1000000));
            }
        });
        startGame();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(ballColor);
        g.fillOval(50,50,50,50);

        if (aim) {
            line.paintComponent(g);
        }
    }

    public void startGame() {
        gameRunning = true;
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    public void pauseGame() {
        if (gameRunning) {
            timer.stop();
            gameRunning = false;
        } else {
            timer.start();
            gameRunning = true;
        }

    }

    private void fileStuff() {
        File file = new File(Paths.get("").toAbsolutePath() + "\\src\\DataBase\\settings.txt");
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                if (scanner.nextLine().equals("1")) {
                    aim = true;
                } else {
                    aim = false;
                }
            } catch (Exception e) {

            }
        } else {
            aim = true;
        }
        file = new File(Paths.get("").toAbsolutePath() + "\\src\\DataBase\\prep.txt");
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                difficulty = Integer.parseInt(scanner.nextLine());
                red = Integer.parseInt(scanner.nextLine());
                green = Integer.parseInt(scanner.nextLine());
                blue = Integer.parseInt(scanner.nextLine());

            }catch (Exception e){

            }
        } else {
          difficulty = 1;
          red = 0;
          green = 0;
          blue = 0;
        }
    }
}
