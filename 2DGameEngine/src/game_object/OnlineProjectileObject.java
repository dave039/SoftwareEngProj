package game_object;

import java.awt.Graphics2D;

public class OnlineProjectileObject extends GameObject {

    protected final int LAG_OUT_TIMER = 120;
    protected int lagOut = LAG_OUT_TIMER;

    int idNum;
    int playerIDNum;

    public int countDown = 120;

    public OnlineProjectileObject(int x, int y, int width, int height) {
        super(x, y, width, height);
        
        speed = 10;
        type = GameObject.ALLY_PROJECTILE_TYPE;
    }

    public void update() {
        super.update();

        y -= speed;

        if (y < 0) {
            isAlive = false;
        }
    }

    public void render(Graphics2D g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public void collisionDetected(int type) {
    }

    public void registerID(int idNum) {
        this.idNum = idNum;
    }

    public int getID() {
        return idNum;
    }

    public void registerPlayerID(int playerIDNum) {
        this.playerIDNum = playerIDNum;
    }

    public int getPlayerID() {
        return playerIDNum;
    }

    public void resetLagOutTimer() {
        lagOut = LAG_OUT_TIMER;
    }
}
