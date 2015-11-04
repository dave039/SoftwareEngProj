package game_object;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

public class BossObject extends GameObject{
	
	private int iterator;
	private int radius;
	
	private double nextX;
	private double nextY;
	
	private float opacity;
	
	public BossObject(int x, int y, int width, int height) {
		super(x, y, width, height);
		type = GameObject.BOSS_TYPE;
		health = 300;
		iterator = 0;
		radius = 120;		
		opacity = 1.0f;
		
		
		nextX = 0;
		nextY = 0;
	}
	
	public void update(){
		super.update();
		if(health <= 0){
			isAlive = false;
		}
		
		nextX = radius * Math.sin(Math.toRadians(iterator));
		nextY = radius * Math.cos(Math.toRadians(iterator));
		
		x = (int)nextX + 250;
		y = (int)nextY + 250;
		
		iterator++;
		
		if(iterator >= 360){
			iterator = 0;
		}
	}
	

	public void render(Graphics2D g){
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g.drawImage(image, x, y, width, height, null);	
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
				
	}
	
	public void collisionDetected(int type){
		if(type == GameObject.PROJECTILE_TYPE){
			health -= 10;
			opacity -=0.02f;
		}
	}
}
