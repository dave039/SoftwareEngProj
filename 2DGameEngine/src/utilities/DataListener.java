package utilities;

import game_object.AllyObject;
import game_object.GameObject;
import game_object.OnlineEnemyObject;
import game_object.OnlineProjectileObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import state_manager.MultiplayerState;

public class DataListener implements Runnable {

    private Socket server;
    private String input;
    private JSONObject recieve;
    private List<GameObject> objectList;

    public DataListener(Socket server, List<GameObject> objectList) {
        this.server = server;
        this.objectList = objectList;
        recieve = new JSONObject();
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(server.getInputStream()));
            // Get the client message
            while ((input = bufferedReader.readLine()) != null) {
                JSONParser parser = new JSONParser();
                try {
                    recieve = (JSONObject) parser.parse(input);
                } catch (ParseException ex) {
                }

                MultiplayerState.playerID = ((Long) recieve.get("playerID")).intValue();

                JSONArray msg = (JSONArray) recieve.get("players");

                Iterator<JSONObject> iterator = msg.iterator();
                while (iterator.hasNext()) {
                    boolean foundPlayer = false;
                    JSONObject updatePlayer = iterator.next();
                    for (GameObject object : objectList) {
                        if (object instanceof AllyObject) {
                            AllyObject tPlayer = (AllyObject) object;
                            if ((Long) updatePlayer.get("id") == tPlayer.getID()) {
                                foundPlayer = true;
                                tPlayer.resetLagOutTimer();
                                tPlayer.setPosition(((Long) updatePlayer.get("xPos")).intValue(), ((Long) updatePlayer.get("yPos")).intValue());
                            }
                        }
                    }

                    if (!foundPlayer) {
                        AllyObject temp = new AllyObject(250, 500, 50, 50);
                        temp.setType(GameObject.ALLY_TYPE);
                        temp.registerID(((Long) updatePlayer.get("id")).intValue());
                        if ((Long) updatePlayer.get("id") % 4 == 0) {
                            temp.setImage(FileLoader.loadImage("/resources/dwarf.png"));
                        } else if ((Long) updatePlayer.get("id") % 4 == 1) {
                            temp.setImage(FileLoader.loadImage("/resources/sanik.png"));
                        } else if ((Long) updatePlayer.get("id") % 4 == 2) {
                            temp.setImage(FileLoader.loadImage("/resources/shrek.png"));
                        } else if ((Long) updatePlayer.get("id") % 4 == 3) {
                            temp.setImage(FileLoader.loadImage("/resources/doge.png"));
                        }
                        objectList.add(temp);
                    }
                }

                JSONArray msg2 = (JSONArray) recieve.get("enemies");

                Iterator<JSONObject> iterator2 = msg2.iterator();
                while (iterator2.hasNext()) {
                    boolean foundEnemy = false;
                    JSONObject updateEnemy = iterator2.next();
                    for (GameObject object : objectList) {
                        if (object instanceof OnlineEnemyObject) {
                            OnlineEnemyObject tEnemy = (OnlineEnemyObject) object;
                            if ((Long) updateEnemy.get("id") == tEnemy.getID()) {
                                foundEnemy = true;
                                tEnemy.resetLagOutTimer();
                                tEnemy.setPosition(((Long) updateEnemy.get("xPos")).intValue(), ((Long) updateEnemy.get("yPos")).intValue());
                                if ((Boolean) updateEnemy.get("alive") == false) {
                                    tEnemy.setAlive(false);
                                }
                            }
                        }
                    }

                    if (!foundEnemy) {
                        if ((Boolean) updateEnemy.get("alive") == false) {
                        } else {
                            OnlineEnemyObject temp = new OnlineEnemyObject(((Long) updateEnemy.get("xPos")).intValue(), ((Long) updateEnemy.get("yPos")).intValue(), 50, 50);
                            temp.registerID(((Long) updateEnemy.get("id")).intValue());
                            //temp.setImage(FileLoader.loadImage("/resources/rubiks_cube.png"));
                            temp.setImage(MultiplayerState.getEnemyImage());
                            objectList.add(temp);
                        }
                    }
                }

                JSONArray msg3 = (JSONArray) recieve.get("bullets");

                Iterator<JSONObject> iterator3 = msg3.iterator();
                while (iterator3.hasNext()) {
                    boolean foundBullet = false;
                    JSONObject updateBullet = iterator3.next();
                    for (GameObject object : objectList) {
                        if (object instanceof OnlineProjectileObject) {
                            OnlineProjectileObject tBullet = (OnlineProjectileObject) object;
                            //System.out.println("bullet id " + ((Long) updateBullet.get("id")).intValue());
                            //System.out.println("player id " + ((Long) updateBullet.get("playerID")).intValue());
                            //System.out.println("actual id " + tBullet.getPlayerID());
                            if ((Long) updateBullet.get("playerID") == tBullet.getPlayerID()) {
                                if ((Long) updateBullet.get("id") == tBullet.getID()) {
                                    foundBullet = true;
                                    tBullet.resetLagOutTimer();
                                    if ((Boolean) updateBullet.get("alive") == false) {
                                        tBullet.setAlive(false);
                                    }
                                }
                            }
                        }
                    }

                    if (!foundBullet) {
                        if ((Boolean) updateBullet.get("alive") == false) {
                        } else {
                            int xPos = 0;
                            int yPos = 0;
                            int idNum = ((Long) updateBullet.get("playerID")).intValue();
                            for (GameObject object : objectList) {
                                if (object instanceof AllyObject) {
                                    AllyObject tAlly = (AllyObject) object;
                                    if (idNum == tAlly.getID()) {
                                        xPos = tAlly.getX() + tAlly.getWidth() / 2;
                                        yPos = tAlly.getY();
                                    }
                                }
                            }
                            //System.out.println("BUG");
                            OnlineProjectileObject temp = new OnlineProjectileObject(xPos, yPos, 10, 10);
                            temp.registerID(((Long) updateBullet.get("id")).intValue());
                            temp.registerPlayerID(idNum);
                            //temp.setImage(FileLoader.loadImage("/resources/rubiks_cube.png"));
                            temp.setImage(MultiplayerState.getProjectileImage());
                            objectList.add(temp);
                        }
                    }
                }
            }
            server.close();
        } catch (IOException ioe) {
            System.out.println("Quit");
            //System.out.println("Player " + idNum + " Quit");
            //System.out.println("IOException on socket listen: " + ioe);
            //ioe.printStackTrace();
        }
    }

}
