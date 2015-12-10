package game_object;

import java.awt.Graphics2D;

public class PowerUpObject extends GameObject {

    public PowerUpObject(int width, int height) {
        super(width, height);

        type = GameObject.POWERUP_TYPE;

    }

    public void render(Graphics2D g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public void collisionDetected(int type) {
        if (type == GameObject.PLAYER_TYPE) {
            isAlive = false;
        }
    }

    public void setisAlive() {

        this.isAlive = true;
    }

    public boolean getisAlive() {
        return isAlive;
    }
}
