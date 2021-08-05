package com.example.orbital_layoutfrontend.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Player implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long playerId;

    @ColumnInfo(name = "Name")
    public String playerName;

    @ColumnInfo(name = "TeamId")
    public long teamId;

//    @ColumnInfo
//    public String experienceLevel;

    @ColumnInfo(name = "Experience Level")
    public String experienceLevel;
}

