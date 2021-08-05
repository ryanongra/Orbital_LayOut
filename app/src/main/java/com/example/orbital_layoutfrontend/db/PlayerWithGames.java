package com.example.orbital_layoutfrontend.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PlayerWithGames {
    @Embedded
    public Player player;

    @Relation(parentColumn = "playerId", entityColumn = "playerId")
    public List<Game> games;
}
