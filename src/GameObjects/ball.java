package GameObjects;


import java.awt.*;

public class ball extends GameObject {
    boolean isMoving;
    boolean isReadyToMove;
    int speed = 5;
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
    }

    public boolean checkCollisionWithFloor() {
        if (posY >= 705) {
            speedY = -speedY;
            isMoving = false;
            isReadyToMove = false;
            return true;
        }
        return false;
    }

    public boolean checkCollisionWithBrick(brick brick) {
        return (getPosX() + getWidth() > brick.getPosX()
                && getPosY() + getHeight() > brick.getPosY()
                && brick.getPosX() + brick.getWidth() > getPosX()
                && brick.getPosY() + brick.getHeight() > getPosY());
    }

    public void changeDir(brick brick) {

        double dx = brick.getPosX() + brick.getWidth() / 2.0 - (getPosX() + getWidth() / 2.0);
        double dy = brick.getPosY() + brick.getHeight() / 2.0 - (getPosY() + getHeight() / 2.0);
        double angle = Math.acos(dx / (Math.sqrt(dx * dx + dy * dy)));
        if (angle <= Math.atan2(1, 1)) {
            speedX = -Math.abs(speedX);//right
        } else if (angle <= Math.PI - Math.atan2(1, 1)) {
            if (dy > 0) {
                speedY = -Math.abs(speedY);//down
            } else {
                speedY = Math.abs(speedY);//up
            }
        } else {
            speedX = Math.abs(speedX);//left
        }
    }
}
