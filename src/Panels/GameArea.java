package Panels;

import GameObjects.*;
import Graphic.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    brickGenerator brickGenerator;
    boolean[] b;
    ArrayList<brick> bricks = new ArrayList<>();
    int num = 1;

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
        if (difficulty == 1) {
            brickGenerator = new easyBrickGenerator();
        } else if (difficulty == 2) {
            brickGenerator = new mediumBrickGenerator();
        } else {
            brickGenerator = new hardBrickGenerator();
        }
        generateNewRow();
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
        for (GameObjects.ball ball : balls) {
            g.fillOval(ball.getPosX(), ball.getPosY(), ball.getWidth(), ball.getHeight());
        }

        for (int i = 0; i < bricks.size(); i++) {
            g.setColor(new Color(0xB40228));
            g.fillRect(bricks.get(i).getPosX(), bricks.get(i).getPosY(), bricks.get(i).getWidth(), bricks.get(i).getHeight());

            FontMetrics fm = g.getFontMetrics();
            int stringWidth = SwingUtilities.computeStringWidth(fm, bricks.get(i).getNum() + "");

            int centerX = (int) Math.round(bricks.get(i).getPosX() + bricks.get(i).getWidth() / 2.0 - stringWidth / 2.0);
            int centerY = bricks.get(i).getPosY() + (int) Math.round(bricks.get(i).getHeight() / 2.0 + (fm.getHeight() / 4.0));

            g.setColor(Color.WHITE);
            g.drawString(bricks.get(i).getNum() + "", centerX, centerY);
        }

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
        java.util.Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!launched && gameRunning) {
                    if(bricks.isEmpty()){
                        generateNewRow();
                    }else{
                        if(bricks.getLast().getPosY() > 60){
                            generateNewRow();
                        }
                    }
                }
            }
        }, 1000, 1000);
        startTimer();
    }

    public void pauseGame() {
        if (gameRunning) {
            gameRunning = false;
            timer.cancel();
        } else {
            startTimer();
            gameRunning = true;
        }

    }

    public void startTimer() {
        timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!launched) {
                    constantDrop();
                }
                //Move ball
                for (int i = 0; i < balls.size(); i++) {
                    if (balls.get(i).isReadyToMove()) {
                        balls.get(i).move();
                        balls.get(i).checkCollisionWithWalls();
                        //for bricks
                        for (int j = 0; j < bricks.size(); j++) {
                            if(balls.get(i).checkCollisionWithBrick(bricks.get(j))){
                                //num--
                                balls.get(i).changeDir(bricks.get(j));
                                bricks.get(j).setNum(bricks.get(j).getNum() - 1);
                                if(bricks.get(j).getNum() <= 0){
                                    bricks.remove(j);
                                    j--;
                                }
                            }
                        }
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
                            dropAll();
                            save(score, ballCount);
                            launched = false;
                        }
                    }
                }
                repaint();
            }
        }, 10, 15);
    }

    public void dropAll() {
        for (int i = 0; i < bricks.size(); i++) {
            bricks.get(i).setPosY(bricks.get(i).getPosY() + 10 * difficulty);
        }
    }

    public void constantDrop() {
        for (int i = 0; i < bricks.size(); i++) {
            bricks.get(i).setPosY(bricks.get(i).getPosY() + difficulty);
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

    public void generateNewRow() {
        brickGenerator.generate();
        b = brickGenerator.getRow();
        if (b[0]) {
            bricks.add(new brick(0, 0, 60, 60, num));
        }
        if (b[1]) {
            bricks.add(new brick(63, 0, 60, 60, num));
        }
        if (b[2]) {
            bricks.add(new brick(126, 0, 60, 60, num));
        }
        if (b[3]) {
            bricks.add(new brick(189, 0, 60, 60, num));
        }
        if (b[4]) {
            bricks.add(new brick(252, 0, 60, 60, num));
        }
        if (b[5]) {
            bricks.add(new brick(315, 0, 60, 60, num));
        }
        if (b[6]) {
            bricks.add(new brick(378, 0, 60, 60, num));
        }
        num += difficulty;
    }
}
