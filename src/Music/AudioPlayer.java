package Music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class AudioPlayer {


    public void explosion() {
       try {
           Clip clip;
           AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(Paths.get("").toAbsolutePath() + "\\src\\Music\\explosion.wav").getAbsoluteFile());
           clip = AudioSystem.getClip();
           clip.open(audioInputStream);
           clip.start();
       }catch (Exception e){

       }
    }
}
