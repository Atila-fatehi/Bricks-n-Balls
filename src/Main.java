import javax.swing.*;
import App.Application;

public class Main implements Runnable{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
    @Override
    public void run() {
        new Application().run();
    }
}