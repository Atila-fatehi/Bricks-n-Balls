package GameObjects;


public class ball extends GameObject {
    boolean isMoving;
    boolean isReadyToMove;
    int speed = 6;
    int speedX;
    int speedY;

    public void move() {
        if (isReadyToMove) {
            isMoving = true;
            posX = posX + speedX;
            posY = posY + speedY;
        }
    }

    public int getSpeedX() {
        return speedX;
    }

    public boolean isReadyToMove() {
        return isReadyToMove;
    }

    public void setReadyToMove(boolean readyToMove) {
        isReadyToMove = readyToMove;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public ball(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void checkCollisionWithWalls() {
        if (posX <= 0 || posX >= 441 - width) {
            speedX = -speedX;
        }
        if (posY <= 0) {
            speedY = -speedY;
        }
        if (posY >= 705) {
            speedY = -speedY;
            isMoving = false;
            isReadyToMove = false;
        }
    }
}
