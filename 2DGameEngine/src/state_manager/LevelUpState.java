package state_manager;

import game.GamePanel;
import game.GameWindow;
import game_object.PlayerObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import menu_manager.MenuManager;
import utilities.Keys;

public class LevelUpState extends GameState {

    public static final int INCREASE_HEALTH_OPTION = 0;
    public static final int INCREASE_DAMAGE_OPTION = 1;
    public static final int NEXT_LEVEL_OPTION = 2;

    private int score;
    private int nextState;

    private boolean keyLock;

    private Font font;

    private MenuManager menuManager;

    private PlayerObject playerObject;

    public LevelUpState(int score, PlayerObject po, int ns) {
        super();

        font = new Font("Arial", Font.PLAIN, 20);

        nextState = ns;

        playerObject = po;

        keyLock = false;

        menuManager = new MenuManager();
        menuManager.addOption("INCREASE HEALTH", 100, 100);
        menuManager.addOption("INCREASE DAMAGE", 100, 130);
        menuManager.addOption("NEXT LEVEL", 100, 160);
        menuManager.setColor(new Color(255, 0, 0));
        menuManager.setFont(font);

        this.score = score;
    }

    public void render(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 200, 70);

        menuManager.render(g);
    }

    private void nextLevel() {
        switch (nextState) {
            case 1:
                GamePanel.setState(new LevelOneState());
                break;
            case 2:
                GamePanel.setState(new LevelTwoState());
                break;
        }
    }

    public void update(boolean[] keys) {
        if (keys[Keys.UP]) {
            if (!keyLock) {
                menuManager.previousOption();
                keyLock = true;
            }
        }
        if (keys[Keys.DOWN]) {
            if (!keyLock) {
                menuManager.nextOption();
                keyLock = true;
            }
        }
        if (!keys[Keys.UP] && !keys[Keys.DOWN] && !keys[Keys.ENTER]) {
            keyLock = false;
        }
        if (keys[Keys.ENTER]) {
            if (!keyLock) {
                keyLock = true;
                switch (MenuManager.getCurrentOption()) {
                    case NEXT_LEVEL_OPTION:
                        nextLevel();
                        break;
                    case INCREASE_HEALTH_OPTION:
                        if (score >= 3) {
                            playerObject.setHealth(playerObject.getHealth() + 10);
                            score -= 3;
                        }
                        break;
                    case INCREASE_DAMAGE_OPTION:
                        if (score >= 3) {
                            playerObject.setDamage(playerObject.getDamage() + 1);
                            score -= 3;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
