package GameObjects;

public class brick extends GameObject{

    int num;
    int startingNum;
    public brick(int posX, int posY, int width, int height , int num) {
        super(posX, posY, width, height);
        this.num = num;
        this.startingNum = num;
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
