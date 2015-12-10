package state_manager;

import Sound.Sound;
import game.GamePanel;
import game.GameWindow;
import game_object.BossObject;
import game_object.DuckObject;
import game_object.EnemyObject;
import game_object.GameObject;
import game_object.Hud;
import game_object.OrangeObject;
import game_object.PlayerObject;
import game_object.PowerUpObject;
import game_object.ProjectileObject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import utilities.FileLoader;
import utilities.Keys;

public class LevelTwoState extends GameState {

    private int score;

    private boolean bossActive;

    private long bossTimer;

    private PlayerObject player;

    private ArrayList<GameObject> objectList;
    private ArrayList<DuckObject> duckList;

    private Hud hud;

    private BufferedImage playerImage;
    private BufferedImage projectileImage;
    private BufferedImage enemyImage;
    private BufferedImage bossImage;
    private BufferedImage duckImage;
    private BufferedImage orangeImage;
    private BufferedImage powerupImage;

    public LevelTwoState() {
        score = 0;

        bossTimer = -1;

        bossActive = false;

        //backgroundImage = FileLoader.loadImage("/resources/level_one_background.png");
        playerImage = FileLoader.loadImage("/resources/dwarf.png");
        projectileImage = FileLoader.loadImage("/resources/banana.png");
        enemyImage = FileLoader.loadImage("/resources/rubiks_cube.png");
        bossImage = FileLoader.loadImage("/resources/boss_1.png");
        duckImage = FileLoader.loadImage("/resources/duck.png");
        orangeImage = FileLoader.loadImage("/resources/orange.png");
        powerupImage = FileLoader.loadImage("/resources/powerup.png");

        objectList = new ArrayList<GameObject>();
        duckList = new ArrayList<DuckObject>();

        hud = new Hud();

        player = new PlayerObject(250, 500, 50, 50);
        player.setImage(playerImage);

        objectList.add(player);
    }

    public void update(boolean[] keys) {
        super.update(keys);
        
        if (keys[Keys.ESCAPE]) {
            System.exit(0);
        }

        hud.update(score, player.getHealth(), player.getMaxHealth());

        checkCollision();
        removeDeadObjects();
        generateEnemy();
        generatePowerUp();

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
                Sound.gun.play();
            }
        }

        duckFire();
    }

    private void duckFire() {
        for (int i = 0; i < duckList.size(); i++) {
            DuckObject d = duckList.get(i);

            if (d.isShouldFire()) {
                OrangeObject o = d.fireOrange(player.getX(), player.getY());
                d.setShouldFire(true);

                o.setImage(orangeImage);
                objectList.add(o);
            }
        }
    }

    private void generateEnemy() {
        Random rand = new Random();

//    	if(bossTimer == -1){
//    		bossTimer = System.currentTimeMillis();
//    	}
//    	
//    	if(System.currentTimeMillis() - bossTimer <= 10000){
//    		if(rand.nextInt(50) == 0){
//        		EnemyObject e = new EnemyObject(rand.nextInt(GameWindow.WIDTH), 0, 50, 50);
//        		e.setImage(enemyImage);
//        		objectList.add(e);
//        	}
//    	}else if(!bossActive){
//    		BossObject b = new BossObject(250, 250, 100, 100);
//    		b.setImage(bossImage);
//    		objectList.add(b);
//    		
//    		bossActive = true;
//    	}
        if (score >= 10) {
            if (!bossActive) {
                BossObject b = new BossObject(250, 250, 100, 100);
                b.setImage(bossImage);
                objectList.add(b);

                bossActive = true;
            }
        } else if (rand.nextInt(50) == 0) {
            EnemyObject e = new EnemyObject(rand.nextInt(GameWindow.WIDTH), 0, 50, 50);
            e.setImage(enemyImage);
            objectList.add(e);
        }

        if (rand.nextInt(200) == 0) {
            DuckObject d = new DuckObject(rand.nextInt(GameWindow.WIDTH), 0, 50, 50);
            d.setImage(duckImage);
            duckList.add(d);
            objectList.add(d);
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
                    GamePanel.setState(new LevelUpState(score, player, 1));
                }
            }
        }
    }

    public void render(Graphics2D g) {
        //g.drawImage(backgroundImage, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);
        super.render(g);

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

    private void generatePowerUp() {
        //Values for the probability for the powerup to show up	
        int Min = 0;
        int Max = 1000;
        double random = Min + (int) (Math.random() * ((Max - Min) + 1));
        //Set random variables for the position of the powerup
        double randomX = Min + (int) (Math.random() * ((GameWindow.WIDTH - Min) + 1));
        double randomY = Min + (int) (Math.random() * ((GameWindow.HEIGHT) - Min) + 1);
        //if (random < 2 && powerup.getisAlive() == false) {
        if (random < 2) {
            PowerUpObject powerup = new PowerUpObject(50, 50);
            powerup.setImage(powerupImage);
            powerup.setisAlive();
            powerup.setX((int) randomX);
            powerup.setY((int) randomY);
            objectList.add(powerup);
        }
    }
}
