package GameObjects;

import java.util.Random;

public class easyBrickGenerator extends brickGenerator{
    @Override
    public void generate() {
        for (int i = 0; i < 7; i++) {
            row[i] = false;
        }
        Random random = new Random();

        int a = random.nextInt(7);
        int b = random.nextInt(7);
        int c = random.nextInt(7);
        for (int i = 0; i < 7; i++) {
            if(i == a || i == b || i == c){
                row[i] = true;
            }
        }
    }
    @Override
    public boolean[] getRow() {
        return row;
    }
}
