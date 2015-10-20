package game_object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class GameObject {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	protected BufferedImage image;
	
	protected Rectangle collisionBox;
	
	public GameObject(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		collisionBox = new Rectangle(x, y, width, height);
	}
	
	public void render(Graphics2D g){
		
	}
	
	public void update(){
		collisionBox.setBounds(x, y, width, height);
	}
	
	public void setImage(BufferedImage i){
		image = i;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getCollisionBox() {
		return collisionBox;
	}

	public void setCollisionBox(Rectangle collisionBox) {
		this.collisionBox = collisionBox;
	}
}
