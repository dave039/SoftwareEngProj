package utilities;

import game_object.AllyObject;
import game_object.GameObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
                        if ((Long) updatePlayer.get("id") == 0) {
                            temp.setImage(FileLoader.loadImage("/resources/dwarf.png"));
                        } else {
                            temp.setImage(FileLoader.loadImage("/resources/sanik.png"));
                        }
                        objectList.add(temp);
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
