package com.example.orbital_layoutfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orbital_layoutfrontend.db.AppDatabase;
import com.example.orbital_layoutfrontend.db.GameDao;
import com.example.orbital_layoutfrontend.db.Player;
import com.example.orbital_layoutfrontend.db.PlayerDao;
import com.example.orbital_layoutfrontend.db.Team;
import com.example.orbital_layoutfrontend.db.TeamDao;

public class PlayerPage extends AppCompatActivity {

    private final String[] experienceLevels = {"Beginner", "Intermediate", "Advanced", "Professional"};

    //private final String[] teams = {"Team 1", "Team 2", "Team 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_page);

        AppDatabase db = AppDatabase.getDbInstance(PlayerPage.this);
        TeamDao teamDao = db.teamDao();
        PlayerDao playerDao = db.playerDao();
        GameDao gameDao = db.gameDao();

        TextView playerTitle = findViewById(R.id.player_name_title);
        TextView teamTitle = findViewById(R.id.player_team_title);
        TextView gamesText = findViewById(R.id.games_text);
        TextView experienceText = findViewById(R.id.experience_text);

        Intent fromLastPage = getIntent();
        Player player = (Player) fromLastPage.getSerializableExtra("player");

        TextView matches = findViewById(R.id.games_text);

        //SET TITLES
        playerTitle.setText(player.playerName);
        teamTitle.setText(teamDao.getTeamName(player.teamId));
        gamesText.setText(Integer.toString(gameDao.loadAllGamesFromPlayer(player.playerId).length));
        experienceText.setText(player.experienceLevel);

        Button trackButton = findViewById(R.id.track_button);
        Button analyseButton = findViewById(R.id.analyse_button);
        Button drillsButton = findViewById(R.id.drills_button);
        Button backButton = findViewById(R.id.back_button);
        Button deleteButton = findViewById(R.id.delete_button);

//        ArrayAdapter<String> teamAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, teams);
//        changeTeam.setAdapter(teamAdapter);
//
//        int teamID = oldPlayer.getTeam().equals("Team 1")
//                ? 0
//                : oldPlayer.getTeam().equals("Team 2")
//                ? 1
//                : oldPlayer.getTeam().equals("Team 3")
//                ? 2
//                : 3;
//        changeTeam.setSelection(teamID);
//
//        changeTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view,
//                                       int position, long id) {
//                Object item = adapterView.getItemAtPosition(position);
//                String newTeam = (String) item;
//                oldPlayer.changeTeam(newTeam);
//
//                teamTitle.setText(newTeam);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayerPage.this, TrackPage.class);
                i.putExtra("player", player);
                startActivity(i);
            }
        });

        analyseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(gamesText.getText().toString()) == 0) {
                    Toast.makeText(PlayerPage.this, "Go to Track to record your first game!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(PlayerPage.this, AnalysePage.class);
                    i.putExtra("player", player);
                    startActivity(i);
                }
            }
        });

        drillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayerPage.this, DrillsPage.class);
                i.putExtra("player", player);
                startActivity(i);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayerPage.this, PlayerSelectPage.class);
                i.putExtra("player", player);
                i.putExtra("delete", false);
                startActivity(i);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayerPage.this, PlayerSelectPage.class);
                boolean check = false;
                Team team = teamDao.getTeam(player.teamId);
                if (playerDao.loadAllPlayerNamesFromTeam(player.teamId).length == 1) {
                    check = true;
                }
                playerDao.delete(player);
                if (check) {
                    teamDao.delete(team);
                }
                startActivity(i);
            }
        });
    }
}