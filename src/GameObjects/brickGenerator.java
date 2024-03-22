package GameObjects;

public abstract class brickGenerator {
    boolean[] row = new boolean[7];
    public abstract void generate();
    public abstract boolean[] getRow();
}
