package com.example.orbital_layoutfrontend.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TeamDao {
    @Query("SELECT `Team Name` FROM Team")
    public String[] loadAllTeamNames();

    @Insert
    public void insertTeam(Team team);

    @Query("SELECT teamId FROM Team WHERE `Team Name` = :teamName")
    public long getTeamId(String teamName);

    @Query("SELECT `Team Name` FROM Team WHERE teamId = :teamId")
    public String getTeamName(long teamId);

    @Query("SELECT * FROM Team WHERE teamId = :teamId")
    public Team getTeam(long teamId);

    @Delete
    public void delete(Team team);

}
