package com.example.orbital_layoutfrontend.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Game {
    @PrimaryKey
    public long gameId;

    public int catches;

    public int pass;

    public int cut;

    public int drop;

    public int turnover;

    public int successfulPass;

    public int pointScored;

    public String gameTimeline;

    @ColumnInfo(name = "playerId")
    public long playerId;

    @ColumnInfo(name = "successfulCuts")
    public int successfulCuts;

    @ColumnInfo(name = "teamScore")
    public int teamScore;

    @ColumnInfo(name = "opponentScore")
    public int opponentScore;

    @ColumnInfo(name = "timeHistory")
    public String timeHistory;
}
