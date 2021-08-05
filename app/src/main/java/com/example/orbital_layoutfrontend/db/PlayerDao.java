package com.example.orbital_layoutfrontend.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PlayerDao {

    @Query("SELECT Name FROM Player WHERE TeamId = :teamID")
    public String[] loadAllPlayerNamesFromTeam(long teamID);

    @Insert
    public void insertPlayer(Player player);

    @Query("SELECT * FROM Player WHERE Name = :name")
    public Player loadPlayer(String name);

    @Delete
    void delete(Player player);
}
