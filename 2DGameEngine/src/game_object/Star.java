package game_object;

import game.GameWindow;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Star {

    float x;
    float y;
    float size;

    public Star() {
        Random rand = new Random();
        x = rand.nextInt(GameWindow.WIDTH);
        y = rand.nextInt(GameWindow.HEIGHT);
        size = (rand.nextFloat() * 3) + 1;
    }

    public void update() {
        y += ((float)size)/2;

        if (y > GameWindow.HEIGHT + size) {
            reset();
        }
    }

    public void reset() {
        Random rand = new Random();
        x = rand.nextInt(GameWindow.WIDTH);
        y = -10 - rand.nextInt(50);
        size = (rand.nextFloat() * 3) + 1;
    }

    public void render(Graphics2D g) {
        g.setColor(Color.white);
        g.fillRect((int) x, (int) y, (int) size, (int) size);
    }
}
