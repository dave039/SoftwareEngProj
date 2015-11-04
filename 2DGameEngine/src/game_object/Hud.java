package game_object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Hud {
	
	private int score;
	private int playerMaxHealth;
	private int playerCurrentHealth;
	
	private float ratio;
	
	Font font;
	
	public Hud(){
		font = new Font("Arial", Font.PLAIN, 20);
		
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.WHITE);
		g.setFont(font);
		
		g.drawString("Score: " + score, 200, 20);
		
		g.setColor(Color.RED);
		g.fillRect(330, 10, (int)(ratio * 150.0), 30);
		
		g.setColor(Color.WHITE);
		g.drawRect(330, 10, 150, 30);
		
	}
	
	public void update(int s, int pch, int pmh){
		score = s;
		playerCurrentHealth = pch;
		playerMaxHealth = pmh;
		
		
		ratio = (float)playerCurrentHealth / (float)playerMaxHealth;
	}
	
	public void setPlayerCurrentHelath(int h){
		playerCurrentHealth = h;
	}
	
	public void setPlayerMaxHealth(int h){
		playerMaxHealth = h;
	}
}
