package com.example.orbital_layoutfrontend;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orbital_layoutfrontend.db.AppDatabase;
import com.example.orbital_layoutfrontend.db.Game;
import com.example.orbital_layoutfrontend.db.GameDao;
import com.example.orbital_layoutfrontend.db.Player;
import com.example.orbital_layoutfrontend.db.PlayerDao;
import com.example.orbital_layoutfrontend.db.TeamDao;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Stack;


public class TrackPage extends AppCompatActivity {

    private OldPlayer oldPlayer;
    private Stack<String> actionHistory;
    private Stack<String> timeHistory;
    private int seconds = 0;
    boolean matchOnGoing = false;
    TextView timeView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_page);

        AppDatabase db = AppDatabase.getDbInstance(TrackPage.this);
        TeamDao teamDao = db.teamDao();
        PlayerDao playerDao = db.playerDao();
        GameDao gameDao = db.gameDao();

        runTimer();

        TextView dateTitle = findViewById(R.id.date_title);
        timeView = (TextView)findViewById(R.id.time_view);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());
        dateTitle.setText(date);

        Player player = (Player) getIntent().getSerializableExtra("player");
        actionHistory = new Stack<>();
        actionHistory.push("GAMESTART");

        timeHistory = new Stack<>();
        timeHistory.push("00:00:00");

        final int[] yourScore = {0};
        final int[] opponentScore = {0};
        final int[] failedPasses = {0};
        final int[] successfulCuts = {0};
        final int[] drops = {0};

        Button startButton = findViewById(R.id.start_button);
        Button endButton = findViewById(R.id.end_button);
        Button catchButton = findViewById(R.id.catch_button);
        Button passButton = findViewById(R.id.pass_button);
        Button cutButton = findViewById(R.id.cut_button);
        Button turnoverButton = findViewById(R.id.turnover_button);
        Button pointScoredButton = findViewById(R.id.scored_button);
        Button dropButton = findViewById(R.id.drop_button);
        Button successfulPassButton = findViewById(R.id.successfulPass_button);
        Button undoButton = findViewById(R.id.undo_button);

        TextView possessionText = findViewById(R.id.posession_text);
        TextView catchText = findViewById(R.id.catches_text);
        TextView passText = findViewById(R.id.passes_text);
        TextView cutText = findViewById(R.id.cuts_text);
        TextView scoreText = findViewById(R.id.score_text);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchOnGoing = !matchOnGoing;
                if (matchOnGoing) {
                    startButton.setText("Pause");
                    Toast.makeText(TrackPage.this, "Match started!", Toast.LENGTH_SHORT).show();
                } else {
                    startButton.setText("Start");
                    Toast.makeText(TrackPage.this, "Match paused!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionHistory.size() == 1) {
                    Intent i = new Intent(TrackPage.this, PlayerPage.class);
                    i.putExtra("player", player);
                    startActivity(i);
                } else {
                    matchOnGoing = false;
                    Game newGame = new Game();
                    newGame.pass = Integer.parseInt(passText.getText().toString());
                    newGame.successfulPass = Integer.parseInt(passText.getText().toString()) - failedPasses[0];
                    newGame.catches = Integer.parseInt(catchText.getText().toString());
                    newGame.drop = drops[0];
                    newGame.cut = Integer.parseInt(cutText.getText().toString());
                    newGame.turnover = 0;
                    newGame.pointScored = 0;
                    newGame.gameTimeline = actionHistory.toString(); //TODO: CHANGE TEXT TO STRING AND THEN READ IT SOMEHOW
                    newGame.playerId = player.playerId;
                    newGame.gameId = gameDao.loadAllGames().length;
                    newGame.successfulCuts = successfulCuts[0];
                    newGame.teamScore = yourScore[0];
                    newGame.opponentScore = opponentScore[0];
                    newGame.timeHistory = timeHistory.toString(); //TODO: CHANGE TEXT TO STRING AND THEN READ IT SOMEHOW
                    gameDao.insertGame(newGame);
                    Intent i = new Intent(TrackPage.this, MatchSummaryPage.class);
                    i.putExtra("player", player);
                    startActivity(i);
                }
            }
        });

        catchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matchOnGoing) {
                    String toastString = "";
                    Integer catches = Integer.parseInt(catchText.getText().toString()) + 1;
                    catchText.setText(catches.toString());
                    if (actionHistory.peek().equals("cut")) {
                        successfulCuts[0]++;
                        toastString += "Successful Cut! ";
                    }
                    actionHistory.push("catch");
                    timeHistory.push(timeView.getText().toString());
                    toastString += "Made a Catch!";
                    Toast.makeText(TrackPage.this, toastString, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TrackPage.this, "Match has not been started", Toast.LENGTH_SHORT);
                }
            }
        });

        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matchOnGoing) {
                    Integer passes = Integer.parseInt(passText.getText().toString()) + 1;
                    passText.setText(passes.toString());
                    actionHistory.push("pass");
                    timeHistory.push(timeView.getText().toString());
                    Toast.makeText(TrackPage.this, "Attemped a Pass!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TrackPage.this, "Match has not been started", Toast.LENGTH_SHORT);
                }
            }
        });

        cutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matchOnGoing) {
                    Integer cuts = Integer.parseInt(cutText.getText().toString()) + 1;
                    cutText.setText(cuts.toString());
                    actionHistory.push("cut");
                    timeHistory.push(timeView.getText().toString());
                    Toast.makeText(TrackPage.this, "Made a Cut!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TrackPage.this, "Match has not been started", Toast.LENGTH_SHORT);
                }
            }
        });

        dropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matchOnGoing) {
                    drops[0]++;
                    successfulCuts[0]++;
                    actionHistory.push("drop");
                    timeHistory.push(timeView.getText().toString());
                    Toast.makeText(TrackPage.this, "Dropped the Disk!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TrackPage.this, "Match has not been started", Toast.LENGTH_SHORT);
                }
            }
        });

        turnoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matchOnGoing) {
                    String possession = possessionText.getText().toString();
                    String toastString = "";
                    possessionText.setText(possession.equals("Offence") ? "Defence" : "Offence");
                    if (actionHistory.peek().equals("pass")) {
                        failedPasses[0]++;
                        toastString += "Unsuccessful pass! ";
                    }
                    actionHistory.push("turnover");
                    timeHistory.push(timeView.getText().toString());
                    toastString += "Turnover!";
                    Toast.makeText(TrackPage.this, toastString, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TrackPage.this, "Match has not been started", Toast.LENGTH_SHORT);
                }
            }
        });

        successfulPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matchOnGoing) {
                    actionHistory.push("successful pass");
                    timeHistory.push(timeView.getText().toString());
                    Toast.makeText(TrackPage.this, "Successful Pass!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TrackPage.this, "Match has not been started", Toast.LENGTH_SHORT);
                }
            }
        });

        pointScoredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matchOnGoing) {
                    if (possessionText.getText().toString().equals("Offence")) {
                        yourScore[0]++;
                        Toast.makeText(TrackPage.this, "Your Team Scored!", Toast.LENGTH_SHORT).show();
                    } else {
                        opponentScore[0]++;
                        Toast.makeText(TrackPage.this, "Opposing Team Scored!", Toast.LENGTH_SHORT).show();
                    }
                    scoreText.setText(String.format("%d : %d", yourScore[0], opponentScore[0]));
                    actionHistory.push("point scored");
                    timeHistory.push(timeView.getText().toString());
                } else {
                    Toast.makeText(TrackPage.this, "Match has not been started", Toast.LENGTH_SHORT);
                }
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String previousAction = actionHistory.pop();
                timeHistory.pop();
                if (previousAction.equals("cut")) {
                    Integer cuts = Integer.parseInt(cutText.getText().toString()) - 1;
                    cutText.setText(cuts.toString());
                    Toast.makeText(TrackPage.this, "Undo Previous Cut", Toast.LENGTH_SHORT).show();
                } else if (previousAction.equals("catch")) {
                    Integer catches = Integer.parseInt(catchText.getText().toString()) - 1;
                    catchText.setText(catches.toString());
                    Toast.makeText(TrackPage.this, "Undo Previous Catch", Toast.LENGTH_SHORT).show();
                    if (actionHistory.peek().equals("cut")) {
                        successfulCuts[0]--;
                    }
                } else if (previousAction.equals("drop")) {
                    drops[0]--;
                    successfulCuts[0]--;
                    Toast.makeText(TrackPage.this, "Undo Previous Drop", Toast.LENGTH_SHORT).show();
                } else if (previousAction.equals("pass")) {
                    Integer passes = Integer.parseInt(passText.getText().toString()) - 1;
                    passText.setText(passes.toString());
                    Toast.makeText(TrackPage.this, "Undo Previous Pass", Toast.LENGTH_SHORT).show();
                } else if (previousAction.equals("turnover")) {
                    String possession = possessionText.getText().toString();
                    possessionText.setText(possession.equals("Offence") ? "Defence" : "Offence");
                    Toast.makeText(TrackPage.this, "Undo Previous Turnover", Toast.LENGTH_SHORT).show();
                    if (actionHistory.peek().equals("pass")) {
                        failedPasses[0]--;
                    }
                } else if (previousAction.equals("point scored")) {
                    Toast.makeText(TrackPage.this, "Undo Previous Point Scored", Toast.LENGTH_SHORT).show();
                    if (possessionText.getText().toString().equals("Offence")) {
                        yourScore[0]--;
                    } else {
                        opponentScore[0]--;
                    }
                    scoreText.setText(String.format("%d : %d", yourScore[0], opponentScore[0]));
                }
            }
        });
    }

    private void runTimer()
    {
        // Creates a new Handler
        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text.
                timeView.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (matchOnGoing) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }
}