package game_object;

import game.GameWindow;

import java.awt.Graphics2D;
import java.util.Random;

public class EnemyMainObject extends GameObject {

    protected Random xPosition = new Random();
    protected int speed;
    protected int min = 2;
    protected int max = 7;

    public EnemyMainObject(int width, int height) {
        super(width, height);
        type = GameObject.ENEMY_TYPE;
        speed = min + (int) (Math.random() * ((max - min) + 1));

    }

    public void render(Graphics2D g) {

        g.drawImage(image, x, y, width, height, null);

    }

    /**
     * Here it�s possible to implement different movements
     */
    public void straightmovement() {
        y += speed;

        if (x <= 0) {
            x = 0;
        } else if (x >= GameWindow.WIDTH - width) {
            x = GameWindow.WIDTH - width;
        }

        if (y <= 0) {
            y = 0;
        } else if (y >= GameWindow.HEIGHT - width - 24) {
            y = 0;
            x = xPosition.nextInt(GameWindow.WIDTH);
        }
    }

    public void update() {

        super.update();
        straightmovement();
    }

    public void collisionDetected(int type) {
        if (type == GameObject.PROJECTILE_TYPE) {
            isAlive = false;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int n) {

        x = n;

    }

}
