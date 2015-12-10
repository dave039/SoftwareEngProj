package game_object;

import java.awt.Graphics2D;

import game.GameWindow;

public class OrangeObject extends GameObject {

    int idNum;
    static int idCounter = 0;

    private int xDir;
    private int yDir;
    private float theta;

    public OrangeObject(int x, int y, int width, int height, int dx, int dy) {
        super(x, y, width, height);

        xDir = dx;
        yDir = dy;

        if ((dx - x) != 0) {
            theta = (float) Math.tan((dy - y) / (dx - x));
        } else {
            theta = 0;
        }

        type = GameObject.ENEMY_PROJECTILE_TYPE;
        speed = 10;

        idNum = idCounter;
        idCounter++;
    }

    public void update() {
        super.update();

        float nx = (float) speed * (float) Math.sin(theta);
        float ny = (float) speed * (float) Math.cos(theta);

        x += (int) nx;
        y += (int) ny;

        if (x > GameWindow.WIDTH || x < GameWindow.WIDTH
                || y > GameWindow.HEIGHT || y < GameWindow.HEIGHT) {
            isAlive = false;
        }
    }

    public void render(Graphics2D g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public void collisionDetected(int type) {

        if (type == GameObject.PLAYER_TYPE) {
            isAlive = false;
        }
    }

    public int getID() {
        return idNum;
    }
}
