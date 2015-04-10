package de.otto.roboapp.model;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataModel {
    String serverIp;
    List<Robo> roboList;
    List<Player> playerList;
    Map<Player, Robo> playerToRoboAssignmentMap;

    public String currentPlayerName;

    RacingData racingData = new RacingData();

    /* Array zum abspeichern der verfügbaren Roboter */
    public DataModel() {
        roboList = new ArrayList<Robo>();
        playerList = new ArrayList<Player>();
        playerToRoboAssignmentMap = new HashMap<Player, Robo>();

   }

    public Map<Player, Robo> getPlayerToRoboAssignmentMap() {
        return playerToRoboAssignmentMap;
    }

    public void clearPlayerList () {
        playerList.clear();
    }

    public void clearRoboList() {
        roboList.clear();
    }

    public void clearAssignmentMap() {
        playerToRoboAssignmentMap.clear();
    }

    /* Füge Player dem Array hinzu */
    public void addPlayerToArray(String playername, boolean isReady) {
        currentPlayerName = playername;
        playerList.add(new Player(playername, isReady));
    }

    public void addRoboToArray(String roboname) {
        roboList.add(new Robo(roboname));
    }
    public void assignPlayerToRobo(String playerName, String roboName){
        playerToRoboAssignmentMap.put(getPlayerByName(playerName), getRoboByName(roboName));
        System.out.println("Test" + playerToRoboAssignmentMap.isEmpty());
    }

    public Robo getRoboByName(String name) {
        for (Robo robo : roboList) {
            if (name.equals(robo.getName())) {
                return robo;
            }
        }
        return null;

    }
    public Player getPlayerByName(String name) {
        for (Player player : playerList) {
            if (name.equals(player.getName())) {
                return player;
            }
        }
        return null;
    }
    public List<Player> getUnassignedPlayer() {
        ArrayList<Player> unassignedPlayerList = new ArrayList<>();
            for (Player player : playerList) {
                 if(!playerToRoboAssignmentMap.containsKey(player)) {
                  unassignedPlayerList.add(player);
            }
        }
        return unassignedPlayerList;
    }

    public List<Player> getAssignedPlayer() {
        ArrayList<Player> assignedPlayerList = new ArrayList<>();
        for (Player player : playerList) {
            if(playerToRoboAssignmentMap.containsKey(player)) {
                assignedPlayerList.add(player);
            }
        }
        return assignedPlayerList;
    }

    public List<Robo> getAssignedRobo() {
        ArrayList<Robo> assignedRoboList = new ArrayList<>();
        for (Robo robo : roboList) {
            if(playerToRoboAssignmentMap.containsValue(robo)) {
                assignedRoboList.add(robo);
            }
        }
        return assignedRoboList;
    }

    public List<Robo> getUnassignedRobo() {
        ArrayList<Robo> unassignedRoboList = new ArrayList<>();
        for (Robo robo : roboList) {
            if(!playerToRoboAssignmentMap.containsValue(robo)) {
                unassignedRoboList.add(robo);
            }
        }
        return unassignedRoboList;
    }

    /* Robolist Setter */
    public void setRoboList(ListView selectRoboList) {

    }

    /* RoboList Getter */
    public List<Robo> getRoboList() {
        return roboList;
    }
    public List<Player> getPlayerList() {
        return  playerList;
    }

    public RacingData getRacingData() {
        return racingData;
    }


    public String getCurrentPlayerName() {
        return currentPlayerName;
    }
}
