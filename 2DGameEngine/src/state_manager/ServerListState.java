package state_manager;

import game.GamePanel;
import game.GameWindow;
import game_object.GameObject;
import game_object.Server;
import game_object.Star;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.simple.JSONObject;

import utilities.FileLoader;
import utilities.Keys;
import utilities.ServerListener;

public class ServerListState extends GameState {

    private ServerListener serverListener;
    private JSONObject send;

    private List<Server> serverList;
    private List<Server> deleteServerList;
    private List<GameObject> objectList;
    private List<Star> starList;

    private static Socket socket;
    private static PrintWriter printWriter;

    int selectedServer = 1;
    private boolean keyLock;

    public ServerListState() {
        backgroundImage = FileLoader.loadImage("/resources/level_one_background.png");

        //objectList = Collections.synchronizedList(new ArrayList<GameObject>());
        serverList = new CopyOnWriteArrayList<Server>();
        deleteServerList = new ArrayList<Server>();
        objectList = new CopyOnWriteArrayList<GameObject>();
        starList = new ArrayList<Star>();

        for (int num = 0; num < 250; num++) {
            Star temp = new Star();
            starList.add(temp);
        }

        try {
            //socket = new Socket("localhost", 63400);
            //socket = new Socket("68.8.238.186", 63400);
            socket = new Socket("ec2-54-68-103-36.us-west-2.compute.amazonaws.com", 63400);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            GamePanel.setState(new MenuState());
            System.out.println(e);
        }
        serverListener = new ServerListener(socket, serverList);
        send = new JSONObject();
        send.put("type", "player");
        printWriter.println(send);
        Thread t = new Thread(serverListener);
        t.start();
    }

    public void update(boolean[] keys) {
        if (keys[Keys.ESCAPE]) {
            System.exit(0);
        }
        if (keys[Keys.SPACE]) {
            if (serverList.size() != 0) {
                GamePanel.setState(new MultiplayerState(serverList.get(selectedServer - 1).getIP(), serverList.get(selectedServer - 1).getPort()));
            }
        }

        if (keys[Keys.UP]) {
            if (!keyLock) {
                selectedServer--;
                if (selectedServer <= 0) {
                    selectedServer = serverList.size();
                }
                keyLock = true;
            }
        }
        if (keys[Keys.DOWN]) {
            if (!keyLock) {
                selectedServer++;
                if (selectedServer > serverList.size()) {
                    selectedServer = 1;
                }
                keyLock = true;
            }
        }

        if (!keys[Keys.UP] && !keys[Keys.DOWN]) {
            keyLock = false;
        }

        send.clear();
        send.put("status", "ok");
        printWriter.println(send);

        for (Server tServer : serverList) {
            tServer.decrementCountdown();
            if (tServer.getLagOutTimer() < 0) {
                deleteServerList.add(tServer);
            }
        }
        for (Server tServer : deleteServerList) {
            serverList.remove(tServer);
            if (selectedServer > serverList.size()) {
                selectedServer--;
            }
        }
        deleteServerList.clear();

        removeDeadObjects();

        for (Star star : starList) {
            star.update();
        }

        for (int i = 0; i < objectList.size(); i++) {
            GameObject go = objectList.get(i);

            if (go.getType() == GameObject.PLAYER_TYPE) {
                go.setKeyboardInput(keys);
            }

            go.update();
        }
    }

    private void removeDeadObjects() {
        for (int i = 0; i < objectList.size(); i++) {
            GameObject go = objectList.get(i);

            if (!go.isAlive()) {
                objectList.remove(i);
                i--;

                int type = go.getType();

                /*if (type == GameObject.ENEMY_TYPE) {
                    score++;
                } else if (type == GameObject.PLAYER_TYPE) {
                    GamePanel.setState(new GameOverState());
                } else if (type == GameObject.BOSS_TYPE) {
                    GamePanel.setState(new LevelUpState(score));
                }*/
            }
        }
    }

    public void render(Graphics2D g) {
        //g.drawImage(backgroundImage, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);

        g.setColor(Color.black);
        g.fillRect(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);

        for (Star star : starList) {
            star.render(g);
        }

        for (int i = 0; i < objectList.size(); i++) {
            GameObject go = objectList.get(i);

            if (go.isAlive()) {
                go.render(g);
            }
        }

        int skipNum = 0;
        int incNum = 1;

        for (Server server : serverList) {
            if (incNum == selectedServer) {
                g.setColor(Color.blue);
            } else {
                g.setColor(Color.red);
            }
            g.drawString("Server: " + server.getIP() + " On port: " + server.getPort() + " Player Count: " + server.getPlayerCounter() + "/10", 50, 50 + skipNum);
            skipNum += 25;
            incNum++;
        }
    }
}
