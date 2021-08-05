package com.example.orbital_layoutfrontend;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Objects;

public class OldPlayer implements Serializable {
    private String name;
    private String teamName;
    private ArrayList<OldGame> oldGameHistory;
    private String position;
    private String experienceLevel;

    /**
     * Constructor for a new player with no game history.
     * @param name
     * @param teamName
     * @param position
     * @param experienceLevel
     */
    public OldPlayer(String name, String teamName,
                     String position, String experienceLevel) throws InvalidParameterException {
        this.name = name;
        this.teamName = teamName;
        oldGameHistory = new ArrayList<>();

        if (position.equals("Handler") || position.equals("Cutter") ||
                position.equals("Beginner")) {
            this.position = position;
        } else {
            throw new InvalidParameterException("Invalid Position");
        }

        if (experienceLevel.equals("Beginner") || experienceLevel.equals("Intermediate") ||
                experienceLevel.equals("Advanced") || experienceLevel.equals("Professional")) {
            this.experienceLevel = experienceLevel;
        } else {
            throw new InvalidParameterException("Invalid Experience Level");
        }
    }

    public OldPlayer(String name, String teamName, ArrayList<OldGame> oldGameHistory,
                     String position, String experienceLevel) throws InvalidParameterException {
        this.name = name;
        this.teamName = teamName;
        this.oldGameHistory = oldGameHistory;

        if (position.equals("Handler") || position.equals("Cutter") ||
                position.equals("Beginner")) {
            this.position = position;
        } else {
            throw new InvalidParameterException("Invalid Position");
        }

        if (experienceLevel.equals("Beginner") || experienceLevel.equals("Intermediate") ||
                experienceLevel.equals("Advanced") || experienceLevel.equals("Professional")) {
            this.experienceLevel = experienceLevel;
        } else {
            throw new InvalidParameterException("Invalid Experience Level");
        }
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return teamName;
    }

    public String toString() {
        return name;
    }

    public void addGame(OldGame newOldGame) {
        oldGameHistory.add(newOldGame);
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public Integer getGameCount() {
        return oldGameHistory.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OldPlayer oldPlayer = (OldPlayer) o;
        return Objects.equals(name, oldPlayer.name);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void changeTeam(String newTeam) {
        this.teamName = newTeam;
    }

    public void changeExperienceLevel(String experienceLevel) {
        if (experienceLevel.equals("Beginner") || experienceLevel.equals("Intermediate") ||
                experienceLevel.equals("Advanced") || experienceLevel.equals("Professional")) {
            this.experienceLevel = experienceLevel;
        } else {
            throw new InvalidParameterException("Invalid Experience Level");
        }
    }

    public ArrayList<OldGame> getGameHistory() {
        return oldGameHistory;
    }

    public OldGame getLatestGame() {
        return oldGameHistory.get(getGameCount() - 1);
    }
}
