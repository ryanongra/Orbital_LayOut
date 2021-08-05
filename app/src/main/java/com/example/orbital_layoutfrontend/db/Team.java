package com.example.orbital_layoutfrontend.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Team {
    @PrimaryKey(autoGenerate = true)
    public long teamId;

    @ColumnInfo(name = "Team Name")
    public String teamsName;
}

