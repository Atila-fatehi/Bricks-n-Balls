package App;

import Pages.StartPage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.nio.file.Paths;

public class Application implements Runnable {
    public void run() {
//        try {
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(Paths.get("").toAbsolutePath() + "\\src\\Music\\Theme.wav").getAbsoluteFile());
//            Clip clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
//            clip.start();
//            clip.loop(Clip.LOOP_CONTINUOUSLY);
//        } catch (Exception ex) {
//            System.out.println("Error with playing sound.");
//            ex.printStackTrace();
//        }

        new StartPage();
    }
}
