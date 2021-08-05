package com.example.orbital_layoutfrontend;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orbital_layoutfrontend.db.AppDatabase;
import com.example.orbital_layoutfrontend.db.Game;
import com.example.orbital_layoutfrontend.db.GameDao;
import com.example.orbital_layoutfrontend.db.Player;
import com.example.orbital_layoutfrontend.db.PlayerDao;
import com.example.orbital_layoutfrontend.db.TeamDao;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class AnalysePage extends AppCompatActivity {

    AppDatabase db = AppDatabase.getDbInstance(AnalysePage.this);
    TeamDao teamDao = db.teamDao();
    PlayerDao playerDao = db.playerDao();
    GameDao gameDao = db.gameDao();

    LineChart lineChart;
    LineData lineData;
    DataSet lineDataSet;
    ArrayList<Entry> lineEntries;

    public final String[] aspects = {"Catch rate",
            "Cut rate",
            "Pass completion rate"};

    private Player player;

    private int noOfGames = 1;

    private String range = "allTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse_page);



        player = (Player) getIntent().getSerializableExtra("player");

        TextView aspectName = findViewById(R.id.aspect_name_title);
        TextView valueTitle = findViewById(R.id.value_title);

        Spinner aspectSpinner = findViewById(R.id.aspect_spinner);
        ArrayAdapter<String> aspectAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, aspects);
        aspectSpinner.setAdapter(aspectAdapter);

        noOfGames = gameDao.loadAllGamesFromPlayer(player.playerId).length;

        aspectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                String selectedAspect = (String) item;
                aspectName.setText(selectedAspect);
                valueTitle.setText(updateValue(selectedAspect));
                updateGraph(aspectName.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button allTime = findViewById(R.id.all_time_button);
        Button lastFifty = findViewById(R.id.last_fifty_button);
        Button lastTen = findViewById(R.id.last_ten_button);

        allTime.setBackgroundColor(Color.BLACK);
        lastFifty.setBackgroundColor(Color.GRAY);
        lastTen.setBackgroundColor(Color.GRAY);

        allTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allTime.setBackgroundColor(Color.BLACK);
                lastFifty.setBackgroundColor(Color.GRAY);
                lastTen.setBackgroundColor(Color.GRAY);

                noOfGames = gameDao.loadAllGamesFromPlayer(player.playerId).length;

                range = "allTime";
                updateGraph((String) aspectSpinner.getSelectedItem());
            }
        });

        lastFifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allTime.setBackgroundColor(Color.GRAY);
                lastFifty.setBackgroundColor(Color.BLACK);
                lastTen.setBackgroundColor(Color.GRAY);

                noOfGames = 50;

                range = "lastFifty";
                updateGraph((String) aspectSpinner.getSelectedItem());
            }
        });

        lastTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allTime.setBackgroundColor(Color.GRAY);
                lastFifty.setBackgroundColor(Color.GRAY);
                lastTen.setBackgroundColor(Color.BLACK);

                noOfGames = 10;

                range = "lastTen";
                updateGraph((String) aspectSpinner.getSelectedItem());
            }
        });

        Button backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnalysePage.this, PlayerPage.class);
                i.putExtra("player", player);
                startActivity(i);
            }
        });

        lineChart = findViewById(R.id.graph);

        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setViewPortOffsets(10, 0, 10, 0);
        Legend l = lineChart.getLegend();
        l.setEnabled(false);
        lineChart.getAxisLeft().setEnabled(true);
        lineChart.getAxisLeft().setSpaceTop(40);
        lineChart.getAxisLeft().setSpaceBottom(40);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);
        //lineChart.animateX(2500);

        updateGraph((String) aspectSpinner.getSelectedItem());
    }

    private String updateValue(String aspect) {
        return getStatText(aspect, noOfGames);
    }

    private String getStatText(String aspect, int noOfGames) {
        Game[] gameHistory = gameDao.loadAllGamesFromPlayer(player.playerId);

        int totalGames = gameHistory.length;

        if (noOfGames > totalGames) {
            noOfGames = totalGames;
        }

        if (gameHistory == null) {
            return ("Go to the track page to record your first game!");
        } else if (gameHistory.length == 0) {
            return ("Go to the track page to record your first game!");
        } else {

            System.out.println("Num games: " + noOfGames);

            switch (aspect) {
                case ("Catch rate"):
                    return (Analyser.catchRate(gameHistory, noOfGames) + "%");
                case ("Cut rate"):
                    return (Analyser.cutRate(gameHistory, noOfGames) + "%");
                case ("Pass completion rate"):
                    return (Analyser.passRate(gameHistory, noOfGames) + "%");
                default:
                    return "Aspect invalid";

            }
        }
    }

    private void getEntries(String aspect){

        lineEntries = new ArrayList<>();
        Game[] gameHistory = gameDao.loadAllGamesFromPlayer(player.playerId);
        int totalGames = gameHistory.length;

        int numGames = range.equals("allTime")
                ? totalGames
                : range.equals("lastFifty")
                ? totalGames > 50 ? 50 : totalGames
                : totalGames > 10 ? 10 : totalGames;

        float xvalue = 0f;

        for (int i = totalGames - numGames; i < totalGames; i++) {
            if (aspect.equals("Catch rate")) {
                lineEntries.add(new Entry(xvalue, Analyser.gameCatchRate(gameHistory[i])));
            }
            if (aspect.equals("Cut rate")) {
                lineEntries.add(new Entry(xvalue, Analyser.gameCutRate(gameHistory[i])));
            }
            if (aspect.equals("Pass completion rate")) {
                lineEntries.add(new Entry(xvalue, Analyser.gamePassRate(gameHistory[i])));
            }
            xvalue++;
        }
    }

    private void updateGraph(String aspect) {
        getEntries(aspect);

        lineDataSet = new LineDataSet(lineEntries, "Data Set");

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add((ILineDataSet) lineDataSet);

        lineData = new LineData(dataSets);

        lineChart.setData(lineData);

        lineChart.invalidate();

        lineDataSet.setColor(Color.YELLOW);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(16f);
    }
}