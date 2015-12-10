package game_object;

import java.awt.Graphics2D;

import game.GameWindow;

public class DuckObject extends GameObject {

    private boolean shouldFire;

    public DuckObject(int x, int y, int width, int height) {
        super(x, y, width, height);

        type = GameObject.ENEMY_TYPE;
        speed = 5;

        shouldFire = false;
    }

    public OrangeObject fireOrange(int px, int py) {
        return new OrangeObject(x, y, 10, 10, px, py);
    }

    public void render(Graphics2D g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public void update() {
        super.update();

        y++;
        if (y >= GameWindow.HEIGHT / 3) {
            y = GameWindow.HEIGHT / 3;
            shouldFire = true;
        }
    }

    public void collisionDetected(int type) {
        if (type == GameObject.PROJECTILE_TYPE) {
            isAlive = false;
        }
    }

    public boolean isShouldFire() {
        return shouldFire;
    }

    public void setShouldFire(boolean shouldFire) {
        this.shouldFire = shouldFire;
    }
}
