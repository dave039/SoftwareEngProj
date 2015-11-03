package game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import Sound.Sound;
import state_manager.MenuState;
import state_manager.State;
import state_manager.StateManager;
import utilities.InputManager;

public class GamePanel extends JPanel implements Runnable {

    private Font fpsFont;
	
    static private InputManager inputManager;
    static private StateManager runningState;

    static private boolean running = true;
    static private double fpsCap = 60;
    static private int fps;
    
    public GamePanel() {        
        inputManager = new InputManager();
        
        addKeyListener(inputManager);
        addFocusListener(inputManager);
        addMouseListener(inputManager);
        addMouseMotionListener(inputManager);
        
        runningState = new StateManager();
        runningState.setState(new MenuState());
        
        fpsFont = new Font("Arial", Font.PLAIN, 20);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //g2.setRenderingHints(rh);
        
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);
        
        runningState.render(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(fpsFont);
        g2.drawString("FPS: " + fps, 10, 15);
    }

    public void run() {
    	Sound.level.loop();
        int frames = 0;

        double unprocessedSeconds = 0;
        long lastTime = System.nanoTime();
        double secondsPerTick = 1 / fpsCap;
        int tickCount = 0;

        requestFocus();

        while (running) {
            long now = System.nanoTime();
            long passedTime = now - lastTime;
            lastTime = now;
            if (passedTime < 0) {
                passedTime = 0;
            }
            if (passedTime > 100000000) {
                passedTime = 100000000;
            }

            unprocessedSeconds += passedTime / 1000000000.0;

            boolean ticked = false;
            while (unprocessedSeconds > secondsPerTick) {
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;

                tickCount++;
                if (tickCount % fpsCap == 0) {
                    //System.out.println(frames + " fps");
                    fps = frames;
                    lastTime += 1000;
                    frames = 0;
                }
            }

            if (ticked) {
                repaint();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void tick() {
        runningState.update(inputManager.keys);
    }

    public static State getState() {
        return runningState;
    }

    public static void setState(State newState) {
        runningState.setState(newState);
    }
}
