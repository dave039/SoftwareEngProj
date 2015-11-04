package state_manager;

import game.GamePanel;
import game.GameWindow;

import java.awt.Graphics2D;

import utilities.FileLoader;
import utilities.Keys;

public class GameOverState extends GameState{
	
	public GameOverState(){
		backgroundImage = FileLoader.loadImage("/resources/dead_dwarf.png");
	}
	
	public void update(boolean[] keys){
		if(keys[Keys.ESCAPE]){
			System.exit(0);
		}
		if(keys[Keys.ENTER]){
			GamePanel.setState(new LevelOneState());
		}
	}
	
	public void render(Graphics2D g){
		g.drawImage(backgroundImage, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);
	}
}
