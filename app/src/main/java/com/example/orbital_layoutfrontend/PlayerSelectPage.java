package com.example.orbital_layoutfrontend;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orbital_layoutfrontend.db.AppDatabase;
import com.example.orbital_layoutfrontend.db.PlayerDao;
import com.example.orbital_layoutfrontend.db.TeamDao;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerSelectPage extends AppCompatActivity {

    Database myDatabase = new Database(true);

    com.example.orbital_layoutfrontend.db.Player selectedPlayer;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select_page);

        AppDatabase db = AppDatabase.getDbInstance(PlayerSelectPage.this);
        TeamDao teamDao = db.teamDao();
        PlayerDao playerDao = db.playerDao();
        String[] teamNames = teamDao.loadAllTeamNames();

        Spinner teamSelect = findViewById(R.id.team_spinner);
        ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, teamNames);
        teamSelect.setAdapter(teamAdapter);

        Intent fromLastPage = getIntent();
        com.example.orbital_layoutfrontend.db.Player player =
                (com.example.orbital_layoutfrontend.db.Player) fromLastPage.getSerializableExtra("player");
        Boolean delete = (Boolean) fromLastPage.getSerializableExtra("delete");

//        if (delete == null || !delete) {
//            myDatabase.updatePlayer(player);
//        } else {
//            myDatabase.deletePlayer(player);
//        }





//        currPlayers = teams.get(0).getPlayers();

        final String[] selectedTeam = {(String) teamSelect.getSelectedItem()};
        final long[] selectedTeamId = {teamDao.getTeamId(selectedTeam[0])};
        final String[][] playerNames = {playerDao.loadAllPlayerNamesFromTeam(selectedTeamId[0])};

//        selectedPlayer = selectedTeam.getPlayers().get(0);


        ArrayList<String> playerNamesArrayList = new ArrayList<>();

        Spinner playerSelect = findViewById(R.id.player_spinner);
        ArrayAdapter<String> playerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, playerNamesArrayList);
        playerSelect.setAdapter(playerAdapter);

//        playerAdapter.clear();
//        playerAdapter.addAll(selectedTeam.getPlayers());

        teamSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                selectedTeam[0] = (String) item;
                selectedTeamId[0] = teamDao.getTeamId(selectedTeam[0]);
//                playerNames[0] = playerDao.loadAllPlayerNamesFromTeam(selectedTeamId[0]);
                playerAdapter.clear();
                if (playerDao.loadAllPlayerNamesFromTeam(selectedTeamId[0]).length == 0) {
                } else {
                    playerAdapter.addAll(Arrays.asList(playerDao.loadAllPlayerNamesFromTeam(selectedTeamId[0])));
                    selectedPlayer = playerDao.loadPlayer(playerDao.loadAllPlayerNamesFromTeam(selectedTeamId[0])[0]);
                }
//                System.out.println(selectedPlayer.playerName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        playerSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                selectedPlayer = playerDao.loadPlayer((String)item);
//                System.out.println(selectedPlayer.playerName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedPlayer = null;
            }
        });


        Button enterButton = findViewById(R.id.enter_button);
        Button newPlayerButton = findViewById(R.id.new_player_button);
        Button backButton = findViewById(R.id.back_button);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPlayer == null) {
                    Toast.makeText(PlayerSelectPage.this, "Click Create Player to add your first player!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(PlayerSelectPage.this, PlayerPage.class);
                    i.putExtra("player", selectedPlayer);
                    startActivity(i);
                }
            }
        });

        newPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlayerSelectPage.this, NewPlayerPage.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlayerSelectPage.this, MainActivity.class));
            }
        });
    }
}