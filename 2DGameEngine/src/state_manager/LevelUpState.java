package state_manager;

import game.GamePanel;
import game.GameWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import menu_manager.MenuManager;
import utilities.Keys;

public class LevelUpState extends GameState{
	public static final int INCREASE_HEALTH_OPTION = 0;
	public static final int INCREASE_DAMAGE_OPTION = 1;
	public static final int NEXT_LEVEL_OPTION = 2;
	
	private int score;

	private boolean keyLock;
	
	private Font font;
	
	private MenuManager menuManager;
	
	public LevelUpState(int score){
		super();
		
		font = new Font("Arial", Font.PLAIN, 20);
		
		keyLock = false;
		
		menuManager = new MenuManager();	
		menuManager.addOption("INCREASE HEALTH", 100, 100);
		menuManager.addOption("INCREASE DAMAGE", 100, 130);
		menuManager.addOption("NEXT LEVEL", 100, 160);
		menuManager.setColor(new Color(255, 0, 0));
		menuManager.setFont(font);
		
		this.score = score;
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.BLUE);
		g.fillRect(0,  0, GameWindow.WIDTH, GameWindow.HEIGHT);
		
		g.drawString("Score: " + score, 200, 70);
		
		menuManager.render(g);
	}
	
	public void update(boolean[] keys){
		if(keys[Keys.UP]){
			if(!keyLock){
				menuManager.previousOption();
				keyLock = true;
			}
		}
		if(keys[Keys.DOWN]){
			if(!keyLock){
				menuManager.nextOption();
				keyLock = true;
			}
		}
		if(!keys[Keys.UP] && !keys[Keys.DOWN]){
			keyLock = false;
		}
		if(keys[Keys.ENTER]){
			switch(MenuManager.getCurrentOption()){
			case NEXT_LEVEL_OPTION:
				GamePanel.setState(new LevelOneState());
				break;
			default:
				break;
			}
		}
	}
	
	
}
