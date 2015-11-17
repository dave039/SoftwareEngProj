package game_object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class GameObject {

    public static final int PLAYER_TYPE = 0;
    public static final int PROJECTILE_TYPE = 1;
    public static final int ENEMY_TYPE = 2;
    public static final int BOSS_TYPE = 3;
    public static final int ALLY_TYPE = 4;
    public static final int ALLY_PROJECTILE_TYPE = 5;

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int speed;
    protected int health;
    protected int maxHealth;
    protected int type;

    protected boolean isAlive;

    protected BufferedImage image;

    protected Rectangle collisionBox;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        isAlive = true;

        collisionBox = new Rectangle(x, y, width, height);
    }

    public void render(Graphics2D g) {

    }

    public void update() {
        collisionBox.setBounds(x, y, width, height);
    }

    public void collisionDetected(int type) {

    }

    public void setKeyboardInput(boolean[] keys) {

    }

    public void setImage(BufferedImage i) {
        image = i;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public BufferedImage getImage() {
        return image;
    }
}
