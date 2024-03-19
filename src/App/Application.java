package App;

import Music.MusicPlayer;
import Pages.Game;
import Pages.StartPage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;


public class Application implements Runnable {
    public void run() {
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.replay();
        new StartPage(musicPlayer);
        //new Game();
    }
}
