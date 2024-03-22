package Thread;

import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class myThread extends Thread {
    JLabel time;
    JLabel score;
    JLabel ballCount;
    File file;
    boolean run = true;
    int s = 0;

    public myThread(JLabel time, JLabel score, JLabel ballCount) {
        this.time = time;
        this.score = score;
        this.ballCount = ballCount;
    }
    public myThread(JLabel time, JLabel score, JLabel ballCount , int seconds) {
        this.s = seconds;
        this.time = time;
        this.score = score;
        this.ballCount = ballCount;
    }
    @Override
    public void run() {
        while (run) {
            s++;
            time.setText(String.valueOf(s));
            file = new File(Paths.get("").toAbsolutePath() + "\\src\\DataBase\\gameStatus.txt");
            if (file.exists()) {
                try {
                    Scanner scanner = new Scanner(file);
                    String s1 = scanner.nextLine();
                    String s2 = scanner.nextLine();
                    score.setText("Score : " + s1);
                    ballCount.setText("ball count : " + s2);
                } catch (Exception e) {

                }
            } else {

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void stopThread(){
        run = false;
    }
    public void continueThread(){
        run = true;
    }

    public int getS() {
        return s;
    }
}
