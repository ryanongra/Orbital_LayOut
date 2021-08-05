package com.example.orbital_layoutfrontend.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TeamWithPlayers {
    @Embedded
    public Team team;

    @Relation(parentColumn = "teamId", entityColumn = "teamId")
    public List<Player> players;
}
