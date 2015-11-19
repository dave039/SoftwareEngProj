package game_object;

import java.awt.Color;
import java.awt.Graphics2D;

public class OnlineEnemyObject extends EnemyObject {

    protected final int LAG_OUT_TIMER = 120;
    protected int lagOut = LAG_OUT_TIMER;

    int idNum;
    boolean wasHit = false;

    public OnlineEnemyObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void render(Graphics2D g) {
        g.drawImage(image, x, y, width, height, null);
        //g.setColor(Color.red);
        //g.fillRect(x, y, width, height);
    }

    public void update() {
        //super.update();
        collisionBox.setBounds(x, y, width, height);

        lagOut--;
        if (lagOut < 0) {
            isAlive = false;
        }
    }

    public void collisionDetected(int type) {
        if (type == GameObject.PROJECTILE_TYPE) {
            wasHit = true;
        }
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

    public boolean wasHit() {
        return wasHit;
    }

    public void resetLagOutTimer() {
        lagOut = LAG_OUT_TIMER;
    }
}
