package state_manager;

import game.GamePanel;
import game.GameWindow;
import game_object.GameObject;
import game_object.Hud;
import game_object.OnlineEnemyObject;
import game_object.PlayerObject;
import game_object.ProjectileObject;
import game_object.Star;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.simple.JSONArray;
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
    private List<Star> starList;

    private static Socket socket;
    private static PrintWriter printWriter;

    private Hud hud;

    private static BufferedImage player0Image;
    private static BufferedImage player1Image;
    private static BufferedImage player2Image;
    private static BufferedImage player3Image;
    private static BufferedImage projectileImage;
    private static BufferedImage enemyImage;
    private BufferedImage bossImage;

    public static Integer playerID;
    private boolean assignedImage = false;

    private boolean didCrash = false;

    static {
        enemyImage = FileLoader.loadImage("/resources/rubiks_cube.png");
        projectileImage = FileLoader.loadImage("/resources/banana.png");
        player0Image = FileLoader.loadImage("/resources/dwarf.png");
        player1Image = FileLoader.loadImage("/resources/sanik.png");
        player2Image = FileLoader.loadImage("/resources/shrek.png");
        player3Image = FileLoader.loadImage("/resources/doge.png");
    }

    public MultiplayerState(String ip, int port) {
        score = 0;

        bossTimer = -1;

        bossActive = false;

        backgroundImage = FileLoader.loadImage("/resources/level_one_background.png");

        bossImage = FileLoader.loadImage("/resources/boss_1.png");

        //objectList = Collections.synchronizedList(new ArrayList<GameObject>());
        objectList = new CopyOnWriteArrayList<GameObject>();
        starList = new ArrayList<Star>();

        for (int num = 0; num < 250; num++) {
            Star temp = new Star();
            starList.add(temp);
        }

        hud = new Hud();

        player = new PlayerObject(250, 500, 50, 50);
        player.setImage(player0Image);

        objectList.add(player);

        try {
            //socket = new Socket("localhost", 63400);
            //socket = new Socket("68.8.238.186", 63400);
            socket = new Socket(ip, port);
            //socket = new Socket("ec2-54-68-103-36.us-west-2.compute.amazonaws.com", 63400);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            dataListener = new DataListener(socket, objectList);
            send = new JSONObject();
            Thread t = new Thread(dataListener);
            t.start();
        } catch (Exception e) {
            System.out.println(e);
            didCrash = true;
        }
    }

    public void update(boolean[] keys) {
        if (didCrash) {
            GamePanel.setState(new ServerListState());
        } else {
            if (keys[Keys.ESCAPE]) {
                System.exit(0);
            }
            if (keys[Keys.Q]) {
                send.put("status", "quit");
                printWriter.println(send);
                try {
                    socket.close();
                } catch (IOException ex) {
                    //Logger.getLogger(MultiplayerState.class.getName()).log(Level.SEVERE, null, ex);
                }
                GamePanel.setState(new ServerListState());
            }

            hud.update(score, player.getHealth(), player.getMaxHealth());

            if (playerID != null && assignedImage == false) {
                assignedImage = true;
                if (playerID % 4 == 0) {
                    player.setImage(player0Image);
                } else if (playerID % 4 == 1) {
                    player.setImage(player1Image);
                } else if (playerID % 4 == 2) {
                    player.setImage(player2Image);
                } else if (playerID % 4 == 3) {
                    player.setImage(player3Image);
                }
            }

            checkCollision();

            send.clear();
            send.put("xPos", player.getX());
            send.put("yPos", player.getY());
            JSONArray hitDetection = new JSONArray();
            JSONArray bulletsFired = new JSONArray();
            for (GameObject object : objectList) {
                if (object instanceof OnlineEnemyObject) {
                    OnlineEnemyObject tEnemy = (OnlineEnemyObject) object;
                    if (tEnemy.wasHit()) {
                        JSONObject enemyDetails = new JSONObject();
                        enemyDetails.put("id", tEnemy.getID());
                        enemyDetails.put("alive", false);
                        hitDetection.add(enemyDetails);
                    }
                }
                if (object.getType() == GameObject.PROJECTILE_TYPE) {
                    ProjectileObject tBullet = (ProjectileObject) object;
                    JSONObject bulletDetails = new JSONObject();
                    bulletDetails.put("id", tBullet.getID());
                    bulletDetails.put("alive", tBullet.isAlive());
                    //System.out.println("id: " + tBullet.getID() + " alive: " + tBullet.isAlive());
                    if (!tBullet.isAlive()) {
                        //System.out.println("Dead bullet");
                    }
                    bulletsFired.add(bulletDetails);
                }
            }
            send.put("enemies", hitDetection);
            send.put("bullets", bulletsFired);
            send.put("status", "ok");
            printWriter.println(send);

            removeDeadObjects();

            for (Star star : starList) {
                star.update();
            }

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
        }
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
        //g.drawImage(backgroundImage, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);

        g.setColor(Color.black);
        g.fillRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);

        for (Star star : starList) {
            star.render(g);
        }

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

    public static BufferedImage getEnemyImage() {
        return enemyImage;
    }

    public static BufferedImage getProjectileImage() {
        return projectileImage;
    }

    public static BufferedImage getPlayer0Image() {
        return player0Image;
    }

    public static BufferedImage getPlayer1Image() {
        return player1Image;
    }

    public static BufferedImage getPlayer2Image() {
        return player2Image;
    }

    public static BufferedImage getPlayer3Image() {
        return player3Image;
    }
}
