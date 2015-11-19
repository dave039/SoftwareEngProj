package game_object;

import game.GameWindow;

import java.awt.Graphics2D;

import Sound.Sound;
import utilities.Keys;

public class AllyObject extends GameObject {

    public static final int NORMAL_FIRE = 0;

    protected int currentFirePower = NORMAL_FIRE;

    protected final int LAG_OUT_TIMER = 120;
    protected int lagOut = LAG_OUT_TIMER;

    protected boolean ableToFire;
    //protected boolean isBlinking;
    protected boolean shouldRender;

    protected long blinkTime;
    protected long flashTimer;

    int idNum;

    public AllyObject(int x, int y, int width, int height) {
        super(x, y, width, height);
        image = null;

        ableToFire = true;
        //isBlinking = true;
        shouldRender = true;

        blinkTime = -1;
        flashTimer = -1;

        speed = 5;
        health = 100;
        maxHealth = 100;
    }

    public void setKeyboardInput(boolean[] keys) {

    }

    public void render(Graphics2D g) {
        /*if (isBlinking) {
            if (flashTimer == -1) {
                flashTimer = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - flashTimer >= 50) {
                shouldRender = !shouldRender;
                flashTimer = System.currentTimeMillis();
            }

        }*/
        if (shouldRender) {
            g.drawImage(image, x, y, width, height, null);
        }
    }

    public void update() {
        super.update();

        if (x <= 0) {
            x = 0;
        } else if (x >= GameWindow.WIDTH - width) {
            x = GameWindow.WIDTH - width;
        }

        if (y <= 0) {
            y = 0;
        } else if (y >= GameWindow.HEIGHT - width - 24) {
            y = GameWindow.HEIGHT - width - 24;
        }

        if (health <= 0) {
            isAlive = false;
            Sound.death.play();
        }

        lagOut--;
        if (lagOut < 0) {
            isAlive = false;
        }

        /*if (isBlinking) {
            if (blinkTime == -1) {
                blinkTime = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - blinkTime >= 1500) {
                isBlinking = false;
                shouldRender = true;
                blinkTime = -1;
            }
        }*/
    }

    public ProjectileObject fireProjectile() {
        if (currentFirePower == NORMAL_FIRE) {
            ableToFire = false;
            Sound.gun.play();
            return new ProjectileObject(x + (width / 2) - 5, y, 10, 10);
        } else {
            return new ProjectileObject(x + (width / 2) - 5, y, 10, 10);
        }

    }

    public void collisionDetected(int type) {
        /*if (!isBlinking) {
            if (type == GameObject.ENEMY_TYPE) {
                health -= 10;
                isBlinking = true;
            }
            if (type == GameObject.BOSS_TYPE) {
                health -= 30;
                isBlinking = true;
            }
        }*/
    }

    public boolean isAbleToFire() {
        return ableToFire;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setPosition(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }

    public void registerID(int idNum) {
        this.idNum = idNum;
    }

    public int getID() {
        return idNum;
    }

    public void resetLagOutTimer() {
        lagOut = LAG_OUT_TIMER;
    }
}
