package App;

import Pages.StartPage;


public class Application implements Runnable {
    public void run() {
        new StartPage();
    }
}
