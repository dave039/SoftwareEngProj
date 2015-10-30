package state_manager;

import game.GamePanel;
import game.GameWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.net.Socket;

import menu_manager.MenuManager;
import utilities.FileLoader;
import utilities.Keys;

public class MenuState extends GameState {

    private static Socket socket;
    private static PrintWriter printWriter;

    public static final int START_OPTION = 0;
    public static final int EXIT_OPTION = 1;

    private boolean keyLock;

    private Font font;

    private MenuManager menuManager;

    public MenuState() {
        keyLock = false;
        backgroundImage = FileLoader.loadImage("/resources/dwarf.png");
        font = new Font("Times New Roman", Font.BOLD, 20);

        menuManager = new MenuManager();
        menuManager.setColor(new Color(255, 0, 255));
        menuManager.setFont(font);
        menuManager.addOption("START", 220, 300);
        menuManager.addOption("EXIT", 220, 330);

        try {
            socket = new Socket("localhost", 63400);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("Hello Socket");
            printWriter.println("EYYYYYAAAAAAAA!!!!");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void update(boolean[] keys) {
        if (keys[Keys.ENTER]) {
            switch (MenuManager.getCurrentOption()) {
                case START_OPTION:
                    GamePanel.setState(new LevelOneState());
                    break;
                case EXIT_OPTION:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
        if (keys[Keys.UP]) {
            if (!keyLock) {
                menuManager.nextOption();
                keyLock = true;
            }
        }
        if (keys[Keys.DOWN]) {
            if (!keyLock) {
                menuManager.previousOption();
                keyLock = true;
            }
        }

        if (!keys[Keys.UP] && !keys[Keys.DOWN]) {
            keyLock = false;
        }
    }

    public void render(Graphics2D g) {
        g.drawImage(backgroundImage, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);

        g.setFont(font);

        menuManager.render(g);
    }
}
