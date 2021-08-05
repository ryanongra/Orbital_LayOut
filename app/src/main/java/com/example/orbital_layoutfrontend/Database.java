package com.example.orbital_layoutfrontend;

import java.util.ArrayList;
import java.util.HashSet;

public class Database {

    private ArrayList<OldTeam> oldTeams = new ArrayList<>();
    private HashSet<OldPlayer> oldPlayers = new HashSet<>();

    public Database(boolean hardcode) {
        if (hardcode) {
            ArrayList<OldPlayer> team1OldPlayers = new ArrayList<>();
            ArrayList<OldPlayer> team2OldPlayers = new ArrayList<>();
            ArrayList<OldPlayer> team3OldPlayers = new ArrayList<>();

            OldPlayer oldPlayer1A = new OldPlayer("Player 1A", "Team 1", "Handler", "Beginner");
            OldPlayer oldPlayer1B = new OldPlayer("Player 1B", "Team 1", "Cutter", "Beginner");
            OldPlayer oldPlayer2A = new OldPlayer("Player 2A", "Team 2", "Handler", "Intermediate");
            OldPlayer oldPlayer2B = new OldPlayer("Player 2B", "Team 2", "Cutter", "Advanced");
            OldPlayer oldPlayer3A = new OldPlayer("Player 3A", "Team 3", "Handler", "Professional");
            OldPlayer oldPlayer3B = new OldPlayer("Player 3B", "Team 3", "Cutter", "Professional");

            team1OldPlayers.add(oldPlayer1A);
            team1OldPlayers.add(oldPlayer1B);
            team2OldPlayers.add(oldPlayer2A);
            team2OldPlayers.add(oldPlayer2B);
            team3OldPlayers.add(oldPlayer3A);
            team3OldPlayers.add(oldPlayer3B);

            oldPlayers.add(oldPlayer1A);
            oldPlayers.add(oldPlayer1B);
            oldPlayers.add(oldPlayer2A);
            oldPlayers.add(oldPlayer2B);
            oldPlayers.add(oldPlayer3A);
            oldPlayers.add(oldPlayer3B);

            OldTeam oldTeam1 = new OldTeam("Team 1", team1OldPlayers);
            OldTeam oldTeam2 = new OldTeam("Team 2", team2OldPlayers);
            OldTeam oldTeam3 = new OldTeam("Team 3", team3OldPlayers);

            OldPlayer placeHolderOldPlayer = new OldPlayer("", "", "Handler", "Beginner");
            ArrayList<OldPlayer> placeHolderOldPlayers = new ArrayList<>();
            placeHolderOldPlayers.add(placeHolderOldPlayer);

            oldTeams.add(new OldTeam("Select Team", placeHolderOldPlayers));
            oldTeams.add(oldTeam1);
            oldTeams.add(oldTeam2);
            oldTeams.add(oldTeam3);
        }
    }

    public ArrayList<OldTeam> getTeams() {
        return oldTeams;
    }

    public void updatePlayer(OldPlayer updatedOldPlayer) {
        if (oldPlayers.contains(updatedOldPlayer)) {
            oldPlayers.remove(updatedOldPlayer);
            oldPlayers.add(updatedOldPlayer);
        } else {
            oldPlayers.add(updatedOldPlayer);
        }
    }

    public void deletePlayer(OldPlayer oldPlayer) {
        if (oldPlayers.contains(oldPlayer)) {
            oldPlayers.remove(oldPlayer);
        }
    }
}
