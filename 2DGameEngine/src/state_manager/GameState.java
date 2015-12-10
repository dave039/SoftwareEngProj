package state_manager;

import game.GameWindow;
import game_object.Star;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GameState implements State {

    protected BufferedImage backgroundImage;
    private List<Star> starList;

    public GameState() {
        starList = new ArrayList<Star>();

        for (int num = 0; num < 250; num++) {
            Star temp = new Star();
            starList.add(temp);
        }
    }

    public void update(boolean[] keys) {
        for (Star star : starList) {
            star.update();
        }
    }

    public void render(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);

        for (Star star : starList) {
            star.render(g);
        }
    }
}
