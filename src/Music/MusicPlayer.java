package Music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class MusicPlayer {
    Clip clip;

    public void replay() {
        File file = new File(Paths.get("").toAbsolutePath() + "\\src\\DataBase\\settings.txt");
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                scanner.nextLine();
                if (scanner.nextLine().equals("1")) {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(Paths.get("").toAbsolutePath() + "\\src\\Music\\Theme.wav").getAbsoluteFile());
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void stop() {
        clip.stop();
    }
}
