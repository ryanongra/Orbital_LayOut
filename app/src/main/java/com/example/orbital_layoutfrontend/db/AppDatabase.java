package com.example.orbital_layoutfrontend.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Team.class, Player.class, Game.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlayerDao playerDao();

    public abstract TeamDao teamDao();

    public abstract GameDao gameDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB_NAME")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}

//@Database(
//        entities = {Team.class, Player.class, Game.class},
//        version = 2,
//        autoMigrations = {
//                @AutoMigration(from = 1, to = 2)
//        })
//public abstract class AppDatabase extends RoomDatabase {
//
//    public abstract PlayerDao playerDao();
//
//    public abstract TeamDao teamDao();
//
//    public abstract GameDao gameDao();
//
//    private static AppDatabase INSTANCE;
//
//    public static AppDatabase getDbInstance(Context context) {
//
//        if (INSTANCE == null) {
//            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB_NAME")
//                    .allowMainThreadQueries()
//                    .build();
//        }
//        return INSTANCE;
//    }
//}

