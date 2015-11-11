/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import game_object.AllyObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import state_manager.MultiplayerState;

/**
 *
 * @author Nicholas
 */
public class DataListener implements Runnable {

    Socket server;
    String input;
    JSONObject recieve;

    public DataListener(Socket server) {
        this.server = server;

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
                //int xPos = (Integer) recieve.get("xPos");
                //int yPos = (Integer) recieve.get("yPos");
                /*int xIndex = input.indexOf("x:");
                 int yIndex = input.indexOf("y:");
                 System.out.println(input);
                 //System.out.println("x: " + xIndex + "y: " + yIndex);
                 xPos = Integer.parseInt(input.substring(xIndex + 2, yIndex));
                 yPos = Integer.parseInt(input.substring(yIndex + 2, input.length()));
                 System.out.println("id: " + idNum + "x: " + xPos + "y: " + yPos);*/

                /*for (Player tPlayer : GameServer.playerList) {
                 if (!tPlayer.equals(this)) {
                 //output.println("id:" + tPlayer.idNum + "x:" + tPlayer.xPos + "y:" + tPlayer.yPos);
                 send.clear();
                 send.put("id", tPlayer.idNum);
                 send.put("xPos", tPlayer.xPos);
                 send.put("yPos", tPlayer.yPos);
                 output.println(send);
                 //System.out.println("Sending player data");
                 }
                 }*/
                boolean foundPlayer = false;

                JSONArray msg = (JSONArray) recieve.get("players");

                Iterator<JSONObject> iterator = msg.iterator();
                while (iterator.hasNext()) {
                    JSONObject updatePlayer = iterator.next();
                    for (AllyObject tPlayer : MultiplayerState.allyList) {
                        if ((Long)updatePlayer.get("id") == tPlayer.getID()) {
                            tPlayer.setPosition(((Long)updatePlayer.get("xPos")).intValue(),((Long)updatePlayer.get("yPos")).intValue());
                            foundPlayer = true;
                        }
                    }

                    if (!foundPlayer) {
                        AllyObject temp = new AllyObject(250, 500, 50, 50);
                        temp.registerID(((Long)updatePlayer.get("id")).intValue());
                        if ((Long)updatePlayer.get("id") == 0) {
                            temp.setImage(FileLoader.loadImage("/resources/dwarf.png"));
                        } else {
                            temp.setImage(FileLoader.loadImage("/resources/sanik.png"));
                        }
                        MultiplayerState.allyList.add(temp);
                    }
                }


                /*int idIndex = input.indexOf("id:");
                 int xIndex = input.indexOf("x:");
                 int yIndex = input.indexOf("y:");
                    
                 int idNum = Integer.parseInt(input.substring(idIndex + 3, xIndex));
                 int xPos = Integer.parseInt(input.substring(xIndex + 2, yIndex));
                 int yPos = Integer.parseInt(input.substring(yIndex + 2, input.length()));
                    
                 //System.out.println(input);
                 //System.out.println("x: " + xIndex + "y: " + yIndex);
                 //System.out.println("x: " + xPos + "y: " + yPos);
                 System.out.println("id: " + idNum + "x: " + xPos + "y: " + yPos);
                    
                 boolean foundPlayer = false;
                    
                 for (AllyObject tPlayer : MultiplayerState.allyList) {
                 if (idNum == tPlayer.getID()) {
                 tPlayer.setPosition(xPos, yPos);
                 foundPlayer = true;
                 }
                 }
                    
                 if (!foundPlayer) {
                 AllyObject temp = new AllyObject(250, 500, 50, 50);
                 temp.registerID(idNum);
                 if (idNum == 0) {
                 temp.setImage(FileLoader.loadImage("/resources/dwarf.png"));
                 } else {
                 temp.setImage(FileLoader.loadImage("/resources/sanik.png"));
                 }
                 MultiplayerState.allyList.add(temp);
                 }*/
            }

            //PrintStream out = new PrintStream(server.getOutputStream());
            server.close();
        } catch (IOException ioe) {
            System.out.println("No players");
            //System.out.println("Player " + idNum + "Quit");
            //System.out.println("IOException on socket listen: " + ioe);
            //ioe.printStackTrace();
        }
    }

}
