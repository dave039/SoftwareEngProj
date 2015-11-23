package utilities;

import game_object.Server;
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

public class ServerListener implements Runnable {

    private Socket socket;
    private String input;
    private JSONObject recieve;
    private List<Server> serverList;

    public ServerListener(Socket socket, List<Server> serverList) {
        this.socket = socket;
        this.serverList = serverList;
        recieve = new JSONObject();
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Get the client message
            while ((input = bufferedReader.readLine()) != null) {
                JSONParser parser = new JSONParser();
                try {
                    recieve = (JSONObject) parser.parse(input);
                } catch (ParseException ex) {
                }

                JSONArray msg = (JSONArray) recieve.get("servers");

                Iterator<JSONObject> iterator = msg.iterator();
                while (iterator.hasNext()) {
                    boolean foundServer = false;
                    JSONObject updateServer = iterator.next();
                    for (Server server : serverList) {
                        if (((String) updateServer.get("ip")).equals(server.getIP()) && (Long) updateServer.get("port") == server.getPort()) {
                            foundServer = true;
                            server.setPlayers(((Long) updateServer.get("totalPlayers")).intValue());
                            server.resetLagOutTimer();
                            //tPlayer.resetLagOutTimer();
                            //tPlayer.setPosition(((Long) updatePlayer.get("xPos")).intValue(), ((Long) updatePlayer.get("yPos")).intValue());
                        }
                    }

                    if (!foundServer) {
                        Server temp = new Server((String) updateServer.get("ip"), ((Long) updateServer.get("port")).intValue(), ((Long) updateServer.get("totalPlayers")).intValue());
                        serverList.add(temp);
                    }
                }
            }
            socket.close();
        } catch (IOException ioe) {
            System.out.println("Quit");
            //System.out.println("Player " + idNum + " Quit");
            //System.out.println("IOException on socket listen: " + ioe);
            //ioe.printStackTrace();
        }
    }

}
