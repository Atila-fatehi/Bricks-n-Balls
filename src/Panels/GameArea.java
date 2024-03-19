package Panels;

import Graphic.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameArea extends JPanel {
    final int WIDTH = 300;
    final int HEIGHT = 700;
    Line line;
    boolean aim;
    Timer timer;

    public GameArea() {
        fileStuff();
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

        //Draw aim Line
        if (aim) {
            //if (line.getY2() < balls.getFirst().getY() - 15) {
            line.paintComponent(g);
            //}
        }
    }

    public void startGame() {
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
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
    }
}
