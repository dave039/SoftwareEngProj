package game_object;

import game.GameWindow;

import java.awt.Graphics2D;

import Sound.Sound;
import utilities.Keys;

public class PlayerObject extends GameObject {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public static final int NORMAL_FIRE = 0;

    protected int currentFirePower = NORMAL_FIRE;

    protected int damage;

    protected boolean ableToFire;
    protected boolean isBlinking;
    protected boolean shouldRender;
    protected boolean isSmall;

    protected long blinkTime;
    protected long flashTimer;
    protected long powerupTime;

    public PlayerObject(int x, int y, int width, int height) {
        super(x, y, width, height);
        image = null;

        damage = 1;

        ableToFire = true;
        isBlinking = true;
        shouldRender = true;
        isSmall = false;

        blinkTime = -1;
        flashTimer = -1;

        speed = 5;
        health = 100;
        maxHealth = 100;
    }

    public void setKeyboardInput(boolean[] keys) {
        if (keys[Keys.UP]) {
            move(PlayerObject.UP);
        }
        if (keys[Keys.LEFT]) {
            move(PlayerObject.LEFT);
        }
        if (keys[Keys.RIGHT]) {
            move(PlayerObject.RIGHT);
        }
        if (keys[Keys.DOWN]) {
            move(PlayerObject.DOWN);
        }
        if (!keys[Keys.SPACE]) {
            ableToFire = true;
        }

    }

    public void move(int direction) {
        if (direction == PlayerObject.UP) {
            y -= speed;
        } else if (direction == PlayerObject.LEFT) {
            x -= speed;
        } else if (direction == PlayerObject.RIGHT) {
            x += speed;
        } else if (direction == PlayerObject.DOWN) {
            y += speed;
        }
    }

    public void render(Graphics2D g) {
        if (isBlinking) {
            if (flashTimer == -1) {
                flashTimer = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - flashTimer >= 50) {
                shouldRender = !shouldRender;
                flashTimer = System.currentTimeMillis();
            }

        }
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

        if (isBlinking) {
            if (blinkTime == -1) {
                blinkTime = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - blinkTime >= 1500) {
                isBlinking = false;
                shouldRender = true;
                blinkTime = -1;
            }
        }
        if (isSmall) {
            if (powerupTime == -1) {
                powerupTime = System.currentTimeMillis();
                this.width = 25;
                this.height = 25;
            }

            if (System.currentTimeMillis() - powerupTime >= 3000) {
                isSmall = false;
                this.width = 50;
                this.height = 50;
                powerupTime = -1;
            }
        }

    }

    public ProjectileObject fireProjectile() {
        if (currentFirePower == NORMAL_FIRE) {
            ableToFire = false;
            //Sound.gun.play();
            return new ProjectileObject(x + (width / 2) - 5, y, 10, 10);
        } else {
            return new ProjectileObject(x + (width / 2) - 5, y, 10, 10);
        }

    }

    public void collisionDetected(int type) {
        if (!isBlinking) {
            if (type == GameObject.ENEMY_TYPE) {
                health -= 10;
                isBlinking = true;
            }
            if (type == GameObject.BOSS_TYPE) {
                health -= 30;
                isBlinking = true;
            }
        }
        if (type == GameObject.POWERUP_TYPE) {
            isSmall = true;
            powerupTime = -1;
        }
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

    public void setDamage(int d) {
        damage = d;
    }

    public int getDamage() {
        return damage;
    }
}
