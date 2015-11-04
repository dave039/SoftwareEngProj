package menu_manager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MenuManager {
	private ArrayList<MenuOption> optionList;
	
	private Font font;
	private Color color;
	
	private static int currentOption;
	
	public MenuManager(){
		optionList = new ArrayList<MenuOption>();
		currentOption = 0;
	}
	
	public void nextOption(){
		currentOption++;
		
		if(currentOption > optionList.size() - 1){
			currentOption = 0;
		}
	}
	
	public void previousOption(){
		currentOption--;
		
		if(currentOption < 0){
			currentOption = optionList.size() - 1;
		}
	}
	
	public void render(Graphics2D g){
		g.setFont(font);
		g.setColor(color);
		
		for(MenuOption m : optionList){
			g.drawString(m.getName(), m.getX(), m.getY());
		
		}
		
		g.setColor(Color.WHITE);
		MenuOption h = optionList.get(currentOption);
		g.drawRect(h.getX() - 3, h.getY() - font.getSize(), 14 * h.getName().length(), font.getSize() + 5);
	}
	
	public void addOption(String s, int x, int y){
		optionList.add(new MenuOption(s, x, y));
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color c) {
		this.color = c;
	}

	public static int getCurrentOption() {
		return currentOption;
	}

	public static void setCurrentOption(int currentOption) {
		MenuManager.currentOption = currentOption;
	}
	
	
	
	

	
	
	
	
}
