package com.example.orbital_layoutfrontend.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface GameDao {

    @Query("SELECT * FROM Game WHERE playerId = :playerId")
    public Game[] loadAllGamesFromPlayer(long playerId);

    @Insert
    public void insertGame(Game game);

    @Query("SELECT * FROM Game")
    public Game[] loadAllGames();
}
