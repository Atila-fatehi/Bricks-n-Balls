package Panels;

import GameObjects.*;
import Graphic.Line;
import Music.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;
import java.util.Timer;

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
    boolean ballStillRunning;
    boolean launched;
    java.util.Timer timer;
    int ballCount;
    File file;
    PrintWriter printWriter;
    brickGenerator brickGenerator;
    boolean[] b;
    ArrayList<brick> bricks = new ArrayList<>();
    ArrayList<brick> Item = new ArrayList<>();
    int num = 1;
    int second = 0;
    boolean speedBoosted = false;
    boolean powerBoosted = false;
    boolean dizzy = false;
    int power = 1;
    double angle;
    boolean lightDance = false;
    AudioPlayer audioPlayer;
    boolean explode = false;
    int explosionX = 0;
    int explosionY = 0;
    int explosionR = 0;

    public GameArea() {
        audioPlayer = new AudioPlayer();
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
                    dizzy = false;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                angle = Math.atan2(e.getY() - balls.getFirst().getPosY(), e.getX() - balls.getFirst().getPosX());
                line.setX2((int) Math.round(Math.round(Math.cos(angle) * 50000)));
                line.setY2((int) Math.round(Math.round(Math.sin(angle) * 50000)));
                if (dizzy) {
                    if (new Random().nextBoolean()) {
                        line.setX2((int) Math.round(Math.random() * 50000));
                    } else {
                        line.setX2((int) -Math.round(Math.random() * 50000));
                    }
                    line.setY2((int) -Math.round(Math.random() * 50000) - 5000);
                }
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (explode) {
            g.setColor(Color.ORANGE);
            g.fillOval(explosionX, explosionY, explosionR, explosionR);
            explosionR += 2;
            explosionX -= 1;
            explosionY -= 1;
            if(explosionR == 120){
                explode = false;
                explosionR = 0 ;
            }
        }
        if (lightDance) {
            g.setColor(new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat()));
        } else {
            g.setColor(ballColor);
        }
        for (int i = 0; i < balls.size(); i++) {
            g.fillOval(balls.get(i).getPosX(), balls.get(i).getPosY(), balls.get(i).getWidth(), balls.get(i).getHeight());
        }
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).getColor() == Color.MAGENTA) {
                g.setColor(new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat()));
            } else {
                if (lightDance) {
                    g.setColor(new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat()));
                } else {
                    g.setColor(bricks.get(i).getColor());
                }
            }
            g.fillRect(bricks.get(i).getPosX(), bricks.get(i).getPosY(), bricks.get(i).getWidth(), bricks.get(i).getHeight());

            FontMetrics fm = g.getFontMetrics();
            int stringWidth = SwingUtilities.computeStringWidth(fm, bricks.get(i).getNum() + "");

            int centerX = (int) Math.round(bricks.get(i).getPosX() + bricks.get(i).getWidth() / 2.0 - stringWidth / 2.0);
            int centerY = bricks.get(i).getPosY() + (int) Math.round(bricks.get(i).getHeight() / 2.0 + (fm.getHeight() / 4.0));
            if (lightDance) {
                g.setColor(new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat()));
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(bricks.get(i).getNum() + "", centerX, centerY);
        }
        for (int i = 0; i < Item.size(); i++) {
            if (lightDance) {
                g.setColor(new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat()));
            } else {
                g.setColor(Item.get(i).getColor());
            }
            g.fillOval(Item.get(i).getPosX(), Item.get(i).getPosY(), Item.get(i).getWidth(), Item.get(i).getHeight());
        }

        if (aim && !launched) {
            if (mouseY <= balls.getFirst().getPosY() - 25) {
                line.paintComponent(g);
            }

        }
        if (lightDance) {
            setBackground(new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat()));
        } else {
            setBackground(new Color(0xA6C8EA));
        }
    }

    public void startGame() {
        second = 1;
        gameRunning = true;
        launched = false;
        balls.add(new ball(453 / 2 - 15, 700, 15, 15));
        ballCount = 1;
        ballStillRunning = false;
        java.util.Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                second++;
                if (!launched && gameRunning) {
                    if (bricks.isEmpty()) {
                        generateNewRow();
                    } else {
                        if (bricks.getLast().getPosY() > 60) {
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

    int index = -1;

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
                            if (balls.get(i).checkCollisionWithBrick(bricks.get(j))) {
                                //num--
                                balls.get(i).changeDir(bricks.get(j));
                                bricks.get(j).setNum(bricks.get(j).getNum() - power);
                                if (bricks.get(j).getNum() <= 0) {
                                    score += bricks.get(j).getStartingNum();
                                    score -= second / 15;
                                    save();
                                    if (bricks.get(j).getColor() == Color.MAGENTA) {
                                        lightDance = true;
                                        java.util.Timer timer1 = new Timer();
                                        timer1.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                lightDance = false;
                                                timer1.cancel();
                                            }
                                        }, 10000, 1);
                                    } else if (bricks.get(j).getColor() == Color.DARK_GRAY) {

                                        java.util.Timer timer1 = new Timer();
                                        java.util.Timer timer2 = new Timer();
                                        timer2.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                changeSize();
                                            }
                                        }, 0, 100);
                                        timer1.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                for (GameObjects.brick brick : bricks) {
                                                    brick.setHeight(60);
                                                    brick.setWidth(60);
                                                }
                                                timer2.cancel();
                                                timer1.cancel();
                                            }
                                        }, 10000, 1);
                                    } else if (bricks.get(j).getColor() == Color.ORANGE) {
                                        audioPlayer.explosion();
                                        explosionX = bricks.get(j).getPosX() + bricks.get(j).getWidth()/2;
                                        explosionY = bricks.get(j).getPosY() + bricks.get(j).getHeight()/2;
                                        explode = true;
                                        for (int k = 0; k < bricks.size(); k++) {
                                           if(new ball(bricks.get(j).getPosX() - 30, bricks.get(j).getPosY() - 30, 120 , 120).checkCollisionWithBrick(bricks.get(k))){
                                               bricks.get(k).setNum(bricks.get(k).getNum() - 50);
                                           }
                                        }
                                    }
                                    bricks.remove(j);
                                    j--;
                                }
                                for (int k = 0; k < bricks.size(); k++) {
                                    if(bricks.get(k).getNum() <= 0){
                                        bricks.remove(k);
                                        k--;
                                    }
                                }
                            }
                        }
                        for (int j = 0; j < Item.size(); j++) {
                            if (balls.get(i).checkCollisionWithBrick(Item.get(j))) {
                                if (Item.get(j).getColor() == Color.WHITE) {
                                    ball newBall = new ball(453 / 2 - 15, 900, 15, 15);
                                    newBall.setMoving(false);
                                    newBall.setReadyToMove(false);
                                    balls.add(newBall);
                                    ballCount++;
                                    Item.remove(j);
                                    j--;
                                } else if (Item.get(j).getColor() == Color.YELLOW) {
                                    if (!speedBoosted) {
                                        for (int k = 0; k < balls.size(); k++) {
                                            balls.get(k).setSpeed(10);
                                            balls.get(k).setSpeedX(2 * balls.get(k).getSpeedX());
                                            balls.get(k).setSpeedY(2 * balls.get(k).getSpeedY());
                                        }
                                        speedBoosted = true;
                                    }
                                    java.util.Timer timer1 = new Timer();
                                    timer1.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            for (int k = 0; k < balls.size(); k++) {
                                                balls.get(k).setSpeed(6);
                                            }
                                            if (ballStillRunning) {
                                                for (int k = 0; k < balls.size(); k++) {
                                                    balls.get(k).setSpeed(6);
                                                    balls.get(k).setSpeedX((int)Math.round(balls.get(k).getSpeedX() / 2.0));
                                                    balls.get(k).setSpeedY((int)Math.round(balls.get(k).getSpeedY() / 2.0));
                                                }
                                            }
                                            speedBoosted = false;
                                            timer1.cancel();
                                        }
                                    }, 15000, 1);
                                    Item.remove(j);
                                    j--;
                                } else if (Item.get(j).getColor() == Color.MAGENTA) {
                                    if (!powerBoosted) {
                                        power = 2;
                                        powerBoosted = true;
                                    }
                                    java.util.Timer timer1 = new Timer();
                                    timer1.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            power = 1;
                                            powerBoosted = false;
                                            timer1.cancel();
                                        }
                                    }, 15000, 1);

                                    Item.remove(j);
                                    j--;
                                } else if (Item.get(j).getColor() == Color.GRAY) {
                                    dizzy = true;
                                    Item.remove(j);
                                    j--;
                                } else if (Item.get(j).getColor() == Color.PINK) {
                                    reverse();
                                    Item.remove(j);
                                    j--;
                                } else if (Item.get(j).getColor() == Color.RED) {

                                    Item.remove(j);
                                    j--;
                                }
                            }
                        }
                        if (balls.get(i).checkCollisionWithFloor()) {
                            balls.get(i).setMoving(false);
                            balls.get(i).setReadyToMove(false);
                            if (index == -1) {
                                index = i;
                            }
                        }
                        ballStillRunning = false;
                        for (GameObjects.ball ball : balls) {
                            if (ball.isMoving()) {
                                ballStillRunning = true;
                                break;
                            }
                        }
                        if (!ballStillRunning && launched) {
                            ball newBall = new ball(453 / 2 - 15, 700, 15, 15);
                            newBall.setMoving(false);
                            newBall.setReadyToMove(false);
                            balls.add(newBall);
                            for (int j = 0; j < balls.size(); j++) {
                                balls.get(j).setPosY(700);
                                if (j != index) {
                                    balls.get(j).setPosX(balls.get(index).getPosX());
                                }
                                line.setX1(balls.get(index).getPosX() + balls.get(index).getWidth() / 2);
                            }
                            ballCount++;
                            dropAll();
                            save();
                            launched = false;
                            index = -1;
                        }
                    }
                }
                repaint();
            }
        }, 10, 15);
    }

    int rate = -2;

    public void changeSize() {
        for (int i = 0; i < bricks.size(); i++) {
            bricks.get(i).setWidth(bricks.get(i).getWidth() + rate);
            bricks.get(i).setHeight(bricks.get(i).getHeight() + rate);
        }
        if (bricks.getFirst().getWidth() <= 40) {
            rate = 2;
        }
        if (bricks.getFirst().getWidth() >= 60) {
            rate = -2;
        }
    }

    public void reverse() {
        for (int i = 0; i < bricks.size(); i++) {
            bricks.get(i).setPosY(bricks.get(i).getPosY() - 120);
        }
        for (int i = 0; i < Item.size(); i++) {
            Item.get(i).setPosY(Item.get(i).getPosY() - 120);
        }
    }

    public void dropAll() {
        for (int i = 0; i < bricks.size(); i++) {
            bricks.get(i).setPosY(bricks.get(i).getPosY() + 10 * difficulty);
        }
        for (int i = 0; i < Item.size(); i++) {
            Item.get(i).setPosY(Item.get(i).getPosY() + 10 * difficulty);
        }
    }

    public void constantDrop() {
        for (int i = 0; i < bricks.size(); i++) {
            bricks.get(i).setPosY(bricks.get(i).getPosY() + difficulty);
        }
        for (int i = 0; i < Item.size(); i++) {
            Item.get(i).setPosY(Item.get(i).getPosY() + difficulty);
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

    public void save() {
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
        int width = 60;
        int height = 60;
        if (!bricks.isEmpty()) {
            width = bricks.getFirst().getWidth();
            height = bricks.getFirst().getHeight();
        }
        if (b[0]) {
            bricks.add(new brick(0, 0, width, height, num));
            if (new Random().nextInt(25) == 0) {
                bricks.getLast().setColor(Color.MAGENTA);
            } else if (new Random().nextInt(20) == 0) {
                bricks.getLast().setColor(Color.DARK_GRAY);
                //earthquake
            } else if (new Random().nextInt(22) == 0) {
                bricks.getLast().setColor(Color.ORANGE);
                //bomb
            }
        }
        if (b[1]) {
            bricks.add(new brick(63, 0, width, height, num));
            if (new Random().nextInt(25) == 0) {
                bricks.getLast().setColor(Color.MAGENTA);
            } else if (new Random().nextInt(20) == 0) {
                bricks.getLast().setColor(Color.DARK_GRAY);
                //earthquake
            } else if (new Random().nextInt(22) == 0) {
                bricks.getLast().setColor(Color.ORANGE);
                //bomb
            }
        }
        if (b[2]) {
            bricks.add(new brick(126, 0, width, height, num));
            if (new Random().nextInt(25) == 0) {
                bricks.getLast().setColor(Color.MAGENTA);
            } else if (new Random().nextInt(20) == 0) {
                bricks.getLast().setColor(Color.DARK_GRAY);
                //earthquake
            } else if (new Random().nextInt(22) == 0) {
                bricks.getLast().setColor(Color.ORANGE);
                //bomb
            }
        }
        if (b[3]) {
            bricks.add(new brick(189, 0, width, height, num));
            if (new Random().nextInt(25) == 0) {
                bricks.getLast().setColor(Color.MAGENTA);
            } else if (new Random().nextInt(20) == 0) {
                bricks.getLast().setColor(Color.DARK_GRAY);
                //earthquake
            } else if (new Random().nextInt(22) == 0) {
                bricks.getLast().setColor(Color.ORANGE);
                //bomb
            }
        }
        if (b[4]) {
            bricks.add(new brick(252, 0, width, height, num));
            if (new Random().nextInt(25) == 0) {
                bricks.getLast().setColor(Color.MAGENTA);
            } else if (new Random().nextInt(20) == 0) {
                bricks.getLast().setColor(Color.DARK_GRAY);
                //earthquake
            } else if (new Random().nextInt(22) == 0) {
                bricks.getLast().setColor(Color.ORANGE);
                //bomb
            }
        }
        if (b[5]) {
            bricks.add(new brick(315, 0, width, height, num));
            if (new Random().nextInt(25) == 0) {
                bricks.getLast().setColor(Color.MAGENTA);
            } else if (new Random().nextInt(20) == 0) {
                bricks.getLast().setColor(Color.DARK_GRAY);
                //earthquake
            } else if (new Random().nextInt(22) == 0) {
                bricks.getLast().setColor(Color.ORANGE);
                //bomb
            }
        }
        if (b[6]) {
            bricks.add(new brick(378, 0, width, height, num));
            if (new Random().nextInt(25) == 0) {
                bricks.getLast().setColor(Color.MAGENTA);
            } else if (new Random().nextInt(20) == 0) {
                bricks.getLast().setColor(Color.DARK_GRAY);
                //earthquake
            } else if (new Random().nextInt(22) == 0) {
                bricks.getLast().setColor(Color.ORANGE);
                //bomb
            }
        }
        num += difficulty;
        for (int i = 0; i < 7; i++) {
            if (!b[i]) {
                if (new Random().nextInt(4) == 0) {
                    //common
                    Item.add(new brick(63 * i + 25, 25, 15, 15, Color.WHITE));
                    break;
                } else if (new Random().nextBoolean()) {
                    int rand = new Random().nextInt(20);
                    if (rand == 0) {
                        //speed
                        Item.add(new brick(63 * i + 25, 25, 15, 15, Color.YELLOW));
                    }
                    if (rand == 1) {
                        //power
                        Item.add(new brick(63 * i + 25, 25, 15, 15, Color.MAGENTA));
                    }
                    if (rand == 2) {
                        //dizzy
                        Item.add(new brick(63 * i + 25, 25, 15, 15, Color.GRAY));
                    }
                    if (rand == 3) {
                        //reverse
                        Item.add(new brick(63 * i + 25, 25, 15, 15, Color.PINK));
                    }
                    if (rand == 4) {
                        //heart
                        Item.add(new brick(63 * i + 25, 25, 15, 15, Color.RED));
                    }
                }

            }
        }


    }
}
