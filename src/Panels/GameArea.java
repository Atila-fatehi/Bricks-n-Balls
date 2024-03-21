package Panels;

import GameObjects.ball;
import Graphic.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class GameArea extends JPanel {
    int difficulty;
    int red;
    int green;
    int blue;
    Color ballColor;
    int mouseX;
    int mouseY;
    int score;
    final int WIDTH = 453;
    final int HEIGHT = 900;
    Line line;
    ArrayList<ball> balls = new ArrayList<>();
    boolean aim;
    boolean gameRunning;
    boolean ballStillRunning = false;
    boolean launched;
    java.util.Timer timer;
    int ballCount;
    File file;
    PrintWriter printWriter;

    public GameArea() {
        file = new File(Paths.get("").toAbsolutePath() + "\\src\\DataBase\\gameStatus.txt");
        setFocusable(true);
        try {
            printWriter = new PrintWriter(file);
            printWriter.println("0");
            printWriter.println("1");
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {

        }
        fileStuff();
        ballColor = new Color(red, green, blue);
        startGame();
        line = new Line(balls.getFirst().getPosX() + balls.getFirst().getWidth() / 2, balls.getFirst().getPosY() + balls.getFirst().getWidth() / 2, 0, 0);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (mouseY >= balls.getFirst().getPosY() - 20) {
                    return;
                }
                if (!launched) {
                    launched = true;
                    java.util.Timer timer1 = new java.util.Timer();
                    timer1.schedule(new TimerTask() {
                        int i = 0;

                        @Override
                        public void run() {
                            balls.get(i).setReadyToMove(true);
                            if (i == balls.size() - 1) {
                                timer1.cancel();
                            }
                            i++;
                        }
                    }, 0, 100);
                    double angle = Math.atan2(line.getY2() - balls.getFirst().getPosY(), line.getX2() - balls.getFirst().getPosX());
                    for (int i = 0; i < balls.size(); i++) {
                        balls.get(i).setSpeedX((int) Math.round(balls.getFirst().getSpeed() * Math.cos(angle)));
                        balls.get(i).setSpeedY((int) Math.round(balls.getFirst().getSpeed() * Math.sin(angle)));
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double angle = Math.atan2(e.getY() - balls.getFirst().getPosY(), e.getX() - balls.getFirst().getPosX());
                line.setX2((int) Math.round(Math.cos(angle) * 10000));
                line.setY2((int) Math.round(Math.sin(angle) * 10000));
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(ballColor);
        for (int i = 0; i < balls.size(); i++) {
            g.fillOval(balls.get(i).getPosX(), balls.get(i).getPosY(), balls.get(i).getWidth(), balls.get(i).getHeight());
        }
//        g.setColor(new Color(0x9D0625));
//        g.fillRect(0 ,0 ,60 , 60);
//        g.fillRect(63 ,0 ,60 , 60);
//        g.fillRect(126 ,0 ,60 , 60);
//        g.fillRect(189 ,0 ,60 , 60);
//        g.fillRect( 252 ,0 ,60 , 60);
//        g.fillRect( 315 ,0 ,60 , 60);
//        g.fillRect( 378 ,0 ,60 , 60);

        if (aim && !launched) {
            if (mouseY <= balls.getFirst().getPosY() - 25) {
                line.paintComponent(g);
            }

        }
    }

    public void startGame() {
        gameRunning = true;
        launched = false;
        balls.add(new ball(453 / 2 - 15, 700, 15, 15));
        ballCount = 1;
        ballStillRunning = false;
        timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Move ball
                for (int i = 0; i < balls.size(); i++) {
                    if (balls.get(i).isReadyToMove()) {
                        balls.get(i).move();
                        balls.get(i).checkCollisionWithWalls();
                        //for bricks
                        if (balls.get(i).checkCollisionWithFloor()) {
                            balls.get(i).setMoving(false);
                            balls.get(i).setReadyToMove(false);
                        }
                        ballStillRunning = false;
                        for (GameObjects.ball ball : balls) {
                            if (ball.isMoving()) {
                                ballStillRunning = true;
                                break;
                            }
                        }
                        if (!ballStillRunning && launched) {
                            balls.add(new ball(453 / 2 - 15, 700, 15, 15));
                            for (int j = 1; j < balls.size(); j++) {
                                balls.get(j).setPosY(700);
                                balls.getFirst().setPosY(700);
                                balls.get(j).setPosX(balls.getFirst().getPosX());
                                line.setX1(balls.getFirst().getPosX() + balls.getFirst().getWidth() / 2);
                            }
                            ballCount++;
                            save(score, ballCount);
                            launched = false;
                        }
//                        repaint();
                    }
                }
                repaint();
            }
        }, 10, 10);
    }

    public void pauseGame() {
        if (gameRunning) {
            gameRunning = false;
            timer.cancel();
        } else {
            timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //Move ball
                    for (int i = 0; i < balls.size(); i++) {
                        if (balls.get(i).isReadyToMove()) {
                            balls.get(i).move();
                            balls.get(i).checkCollisionWithWalls();
                            //for bricks
                            if (balls.get(i).checkCollisionWithFloor()) {
                                balls.get(i).setMoving(false);
                                balls.get(i).setReadyToMove(false);
                            }
                            ballStillRunning = false;
                            for (GameObjects.ball ball : balls) {
                                if (ball.isMoving()) {
                                    ballStillRunning = true;
                                    break;
                                }
                            }
                            if (!ballStillRunning && launched) {
                                balls.add(new ball(453 / 2 - 15, 700, 15, 15));
                                for (int j = 1; j < balls.size(); j++) {
                                    balls.get(j).setPosY(700);
                                    balls.getFirst().setPosY(700);
                                    balls.get(j).setPosX(balls.getFirst().getPosX());
                                    line.setX1(balls.getFirst().getPosX() + balls.getFirst().getWidth() / 2);
                                }
                                ballCount++;
                                save(score, ballCount);
                                launched = false;
                            }
//                        repaint();
                        }
                    }
                    repaint();
                }
            }, 10, 10);
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

            } catch (Exception e) {

            }
        } else {
            difficulty = 1;
            red = 0;
            green = 0;
            blue = 0;
        }
    }

    public void save(int a, int b) {
        try {
            printWriter = new PrintWriter(file);
            printWriter.println(score);
            printWriter.println(ballCount);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {

        }
    }
}
