package GameObjects;

import java.awt.*;

public class brick extends GameObject {
    Color color;
    int num;
    int startingNum;

    public brick(int posX, int posY, int width, int height, int num) {
        super(posX, posY, width, height);
        this.num = num;
        this.startingNum = num;
        this.color = new Color(0xB40228);
    }
    public brick(int posX, int posY, int width, int height, int num , Color color) {
        super(posX, posY, width, height);
        this.num = num;
        this.startingNum = num;
        this.color = color;
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public brick(int posX, int posY, int width, int height , Color color) {
        super(posX, posY, width, height);
        this.color = color;
    }

    public int getStartingNum() {
        return startingNum;
    }

    public void setStartingNum(int startingNum) {
        this.startingNum = startingNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
