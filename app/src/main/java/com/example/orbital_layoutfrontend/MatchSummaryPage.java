package com.example.orbital_layoutfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orbital_layoutfrontend.db.AppDatabase;
import com.example.orbital_layoutfrontend.db.Game;
import com.example.orbital_layoutfrontend.db.GameDao;
import com.example.orbital_layoutfrontend.db.Player;
import com.example.orbital_layoutfrontend.db.PlayerDao;
import com.example.orbital_layoutfrontend.db.TeamDao;

public class MatchSummaryPage extends AppCompatActivity {
    AppDatabase db = AppDatabase.getDbInstance(MatchSummaryPage.this);
    TeamDao teamDao = db.teamDao();
    PlayerDao playerDao = db.playerDao();
    GameDao gameDao = db.gameDao();

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summary_page);

        Button backButton = findViewById(R.id.back_button);
        TextView guideText = findViewById(R.id.guide_text);

        player = (Player) getIntent().getSerializableExtra("player");

        String history = generateMatchHistory();

        guideText.setText(history);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MatchSummaryPage.this, PlayerPage.class);
                i.putExtra("player", player);
                startActivity(i);
            }
        });
    }

    private String generateMatchHistory() {
        Game[] games = gameDao.loadAllGamesFromPlayer(player.playerId);

        String actionString = games[games.length-1].gameTimeline;
        String timeString = games[games.length - 1].timeHistory;

        String[] actions = actionString.substring(1, actionString.length() - 1).split(", ");
        String[] times = timeString.substring(1, timeString.length() - 1).split(", ");

        String historyText = "";
        for (int i = 0; i < actions.length; i++) {
            String textToAdd;
            String action = actions[i];
            String time = times[i];
            System.out.println(action + "---------------------------------------");
            String name = player.playerName;

            System.out.println(action + time);

            switch (action) {
                case "catch": textToAdd = String.format("%s     %s made a catch!", time, name); break;
                case "pass": textToAdd = String.format("%s     %s attemped a pass!", time, name); break;
                case "cut": textToAdd = String.format("%s     %s made a cut!", time, name); break;
                case "drop": textToAdd = String.format("%s     %s dropped the disk!", time, name); break;
                case "turnover": textToAdd = String.format("%s     Turnover!", time, name); break;
                case "successful pass": textToAdd = String.format("%s     %s's pass was successful!", time, name); break;
                case "point scored": textToAdd = String.format("%s     Point Scored!", time, name); break;
                case "GAMESTART": textToAdd = "GAME START"; break;
                default: textToAdd = "unrecognised action";
            }

            historyText = historyText + textToAdd + "\n";
        }
        return historyText;




    }
}