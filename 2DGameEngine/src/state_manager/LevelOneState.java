package state_manager;

import game.GameWindow;
import game_object.GameObject;
import game_object.PlayerObject;

import java.awt.Graphics2D;
import java.util.ArrayList;

import utilities.FileLoader;

public class LevelOneState extends GameState{

	PlayerObject player;
	
	ArrayList<GameObject> objectList;
	
	public LevelOneState(){
		backgroundImage = FileLoader.loadImage("/resources/level_one_background.png");
		
		objectList = new ArrayList<GameObject>();
		
		player = new PlayerObject(250, 500, 50, 50);
		player.setDamage(5);
		player.setHealth(10);
		player.setSpeed(5);
		player.setImage(FileLoader.loadImage("/resources/player_image.png"));
		
		objectList.add(player);
	}
	
	private void keyController(){
		if(upKey){
			player.move(PlayerObject.UP);
		}
		if(leftKey){
			player.move(PlayerObject.LEFT);
		}
		if(rightKey){
			player.move(PlayerObject.RIGHT);
		}
		if(downKey){
			player.move(PlayerObject.DOWN);
		}
	}

    public void update() {
    	keyController();
    	
    	for(GameObject go : objectList){
    		go.update();
    	}
    }

    public void render(Graphics2D g) {
    	g.drawImage(backgroundImage, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);
    	
    	for(GameObject go : objectList){
    		go.render(g);
    	}
    }
}
