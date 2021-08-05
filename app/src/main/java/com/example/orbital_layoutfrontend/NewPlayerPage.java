package com.example.orbital_layoutfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orbital_layoutfrontend.db.AppDatabase;
import com.example.orbital_layoutfrontend.db.Player;
import com.example.orbital_layoutfrontend.db.PlayerDao;
import com.example.orbital_layoutfrontend.db.Team;
import com.example.orbital_layoutfrontend.db.TeamDao;

import java.util.Arrays;

public class NewPlayerPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player_page);

        AppDatabase db = AppDatabase.getDbInstance(NewPlayerPage.this);
        TeamDao teamDao = db.teamDao();
        PlayerDao playerDao = db.playerDao();
        String[] teamNames = teamDao.loadAllTeamNames();

        Spinner experienceSpinner = findViewById(R.id.experience_spinner);
        String[] teams = new String[]{"Beginner", "Intermediate", "Advanced", "Professional"};   // query database to see which teams exist
        ArrayAdapter<String> experienceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, teams);
        experienceSpinner.setAdapter(experienceAdapter);

        Button createButton = findViewById(R.id.create_button);
        Button backButton = findViewById(R.id.back_button);
        EditText teamText = findViewById(R.id.team_textbox);
        EditText playerText = findViewById(R.id.name_textbox);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teamText.getText().toString().equals("") || playerText.getText().toString().equals("")) {
                    Toast.makeText(NewPlayerPage.this, "Please fill up all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String[] allTeams = teamDao.loadAllTeamNames();
                    if (Arrays.asList(allTeams).contains(teamText.getText().toString())) {
                        Player player = new Player();
                        player.teamId = teamDao.getTeamId(teamText.getText().toString());
                        player.playerName = playerText.getText().toString();
                        playerDao.insertPlayer(player);
                    } else {
                        Team team = new Team();
                        team.teamsName = teamText.getText().toString();
                        teamDao.insertTeam(team);
                        Player player = new Player();
                        player.teamId = teamDao.getTeamId(teamText.getText().toString());
                        player.playerName = playerText.getText().toString();
                        player.experienceLevel = (String) experienceSpinner.getSelectedItem();
                        playerDao.insertPlayer(player);
                    }
                    startActivity(new Intent(NewPlayerPage.this, PlayerSelectPage.class));
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewPlayerPage.this, PlayerSelectPage.class));
            }
        });
    }
}