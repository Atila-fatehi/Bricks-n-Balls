package GameObjects;

public class brick extends GameObject{

    int num;

    public brick(int posX, int posY, int width, int height , int num) {
        super(posX, posY, width, height);
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
