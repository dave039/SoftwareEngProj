package state_manager;

import game.GamePanel;
import game.GameWindow;
import game_object.GameObject;
import game_object.Hud;
import game_object.PlayerObject;
import game_object.ProjectileObject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.simple.JSONObject;
import utilities.DataListener;

import utilities.FileLoader;
import utilities.Keys;

public class MultiplayerState extends GameState {

    private DataListener dataListener;
    private JSONObject send;

    private int score;

    private boolean bossActive;

    private long bossTimer;

    private PlayerObject player;

    private List<GameObject> objectList;

    private static Socket socket;
    private static PrintWriter printWriter;

    private Hud hud;

    private BufferedImage playerImage;
    private BufferedImage projectileImage;
    private BufferedImage enemyImage;
    private BufferedImage bossImage;

    public MultiplayerState() {
        score = 0;

        bossTimer = -1;

        bossActive = false;

        backgroundImage = FileLoader.loadImage("/resources/level_one_background.png");
        playerImage = FileLoader.loadImage("/resources/dwarf.png");
        projectileImage = FileLoader.loadImage("/resources/banana.png");
        enemyImage = FileLoader.loadImage("/resources/rubiks_cube.png");
        bossImage = FileLoader.loadImage("/resources/boss_1.png");

        objectList = Collections.synchronizedList(new ArrayList<GameObject>());

        hud = new Hud();

        player = new PlayerObject(250, 500, 50, 50);
        player.setImage(playerImage);

        objectList.add(player);

        try {
            //socket = new Socket("localhost", 63400);
            socket = new Socket("ec2-54-68-103-36.us-west-2.compute.amazonaws.com", 63400);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            GamePanel.setState(new MenuState());
            System.out.println(e);
        }
        dataListener = new DataListener(socket, objectList);
        send = new JSONObject();
        Thread t = new Thread(dataListener);
        t.start();
    }

    public void update(boolean[] keys) {
        if (keys[Keys.ESCAPE]) {
            System.exit(0);
        }

        hud.update(score, player.getHealth(), player.getMaxHealth());

        checkCollision();
        removeDeadObjects();

        for (int i = 0; i < objectList.size(); i++) {
            GameObject go = objectList.get(i);

            if (go.getType() == GameObject.PLAYER_TYPE) {
                go.setKeyboardInput(keys);
            }

            go.update();
        }

        if (keys[Keys.SPACE]) {
            if (player.isAbleToFire()) {
                ProjectileObject p = player.fireProjectile();
                p.setImage(projectileImage);
                objectList.add(p);
            }
        }
        send.put("xPos", player.getX());
        send.put("yPos", player.getY());
        printWriter.println(send);
        //printWriter.println("x:" + player.getX() + "y:" + player.getY());
    }

    private void removeDeadObjects() {
        for (int i = 0; i < objectList.size(); i++) {
            GameObject go = objectList.get(i);

            if (!go.isAlive()) {
                objectList.remove(i);
                i--;

                int type = go.getType();

                if (type == GameObject.ENEMY_TYPE) {
                    score++;
                } else if (type == GameObject.PLAYER_TYPE) {
                    GamePanel.setState(new GameOverState());
                } else if (type == GameObject.BOSS_TYPE) {
                    GamePanel.setState(new LevelUpState(score));
                }
            }
        }
    }

    public void render(Graphics2D g) {
        g.drawImage(backgroundImage, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);

        for (int i = 0; i < objectList.size(); i++) {
            GameObject go = objectList.get(i);

            if (go.isAlive()) {
                go.render(g);
            }
        }

        hud.render(g);
    }

    private void checkCollision() {
        for (int i = 0; i < objectList.size(); i++) {
            for (int j = 0; j < objectList.size(); j++) {
                if (i == j) {
                    continue;
                }

                GameObject g1 = objectList.get(i);
                GameObject g2 = objectList.get(j);
                Rectangle r1 = g1.getCollisionBox();
                Rectangle r2 = g2.getCollisionBox();

                if (r1.intersects(r2)) {
                    g1.collisionDetected(g2.getType());
                }
            }
        }
    }
}
