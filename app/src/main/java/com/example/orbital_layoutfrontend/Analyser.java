package com.example.orbital_layoutfrontend;

import com.example.orbital_layoutfrontend.db.Game;
import com.example.orbital_layoutfrontend.db.Player;

public class Analyser {

    public static String catchRate(Game[] gameHistory, int noOfGames) {

        Game[] games = new Game[noOfGames];
        System.arraycopy(gameHistory, gameHistory.length - noOfGames, games, 0, noOfGames);

        Float totalCatches = 0.0f;
        Float totalFailed = 0.0f;
        for (Game game : games) {
            totalCatches += game.catches;
            totalFailed += game.drop;
        }
        return  String.valueOf(((totalCatches - totalFailed) * 100) / totalCatches);
    }

    public static String cutRate(Game[] gameHistory, int noOfGames) {
        Game[] games = new Game[noOfGames];
        System.arraycopy(gameHistory, gameHistory.length - noOfGames, games, 0, noOfGames);

        Float totalCuts = 0.0f;
        Float totalSuccessful = 0.0f;
        for (Game game : games) {
            totalCuts += game.cut;
            totalSuccessful += game.successfulCuts;
        }
        return  String.valueOf((totalSuccessful * 100) / totalCuts);
    }

    public static String passRate(Game[] gameHistory, int noOfGames) {
        Game[] games = new Game[noOfGames];
        System.arraycopy(gameHistory, gameHistory.length - noOfGames, games, 0, noOfGames);

        Float totalPass = 0.0f;
        Float totalSuccessful = 0.0f;
        for (Game game : games) {
            totalPass += game.pass;
            totalSuccessful += game.successfulPass;
        }
        return  String.valueOf((totalSuccessful * 100) / totalPass);
    }

    public static float gameCatchRate(Game game) {
        if (game.catches == 0) {
            return 0;
        }
        return ((game.catches - game.drop) * 100) / game.catches;
    }

    public static float gameCutRate(Game game) {
        if (game.cut == 0) {
            return 0;
        }
        return (game.successfulCuts * 100) / game.cut;
    }

    public static float gamePassRate(Game game) {
        System.out.println(game.successfulPass / game.pass);
        return (game.successfulPass * 100) / game.pass;
    }

}
