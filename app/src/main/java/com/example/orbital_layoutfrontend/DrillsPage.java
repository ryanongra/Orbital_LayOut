package com.example.orbital_layoutfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orbital_layoutfrontend.db.AppDatabase;
import com.example.orbital_layoutfrontend.db.GameDao;
import com.example.orbital_layoutfrontend.db.Player;
import com.example.orbital_layoutfrontend.db.PlayerDao;
import com.example.orbital_layoutfrontend.db.TeamDao;

import java.util.ArrayList;

public class DrillsPage extends AppCompatActivity {

    Player player;

    private String[] drillVideos = new String[]{
            "https://www.youtube.com/watch_popup?v=25XZBaQJxDo&t=141s",
            "https://www.youtube.com/watch_popup?v=mgYpw4_2rVw",
            "https://www.youtube.com/watch_popup?v=gU_mvp2YXFA&list=PLz_albxGFnvX_s4nXF8wnuOvP607LUpLU&index=1&ab_channel=RISEUPUltimate",
            "https://www.youtube.com/watch_popup?v=CqpPfVZ1HeI&list=PLz_albxGFnvX_s4nXF8wnuOvP607LUpLU&index=2&ab_channel=RISEUPUltimate",
            "https://www.youtube.com/watch_popup?v=bB-zFGj3XGM&list=PLz_albxGFnvX_s4nXF8wnuOvP607LUpLU&index=3&ab_channel=RISEUPUltimate",
            "https://www.youtube.com/watch_popup?v=IK9Qzg8P4rs&list=PLz_albxGFnvX_s4nXF8wnuOvP607LUpLU&index=4&ab_channel=RISEUPUltimate",
            "https://www.youtube.com/watch_popup?v=oN1bzPCKkGE&list=PLz_albxGFnvX_s4nXF8wnuOvP607LUpLU&index=6&ab_channel=RISEUPUltimate",
            "https://www.youtube.com/watch_popup?v=nHsSHJBEcRw&list=PLz_albxGFnvX_s4nXF8wnuOvP607LUpLU&index=7&ab_channel=RISEUPUltimate",
            "https://www.youtube.com/watch_popup?v=lrg5sKsfL6k&list=PLz_albxGFnvX_s4nXF8wnuOvP607LUpLU&index=8&ab_channel=RISEUPUltimate"};

    private String[] drillDescriptions = new String[]{"Give and Go \n \n " +
            "This drill primarily trains cutting, but required moderate mastery of" +
            "passing and catching. \n \n" +
            "Recommended experience level: Intermediate",
            "The Dump Swing \n \n " +
                    "This drill primarily trains cutting, but required moderate mastery of" +
                    "passing and catching. \n \n" +
                    "Recommended experience level: Advanced",
            "Athleticism Drills \n \n" +
                    "These off the disc drills train agility and general athleticism. \n \n" +
                    "Recommended experience level: All experience levels",
            "Cutting drills \n \n" +
                    "These cutting drills train various cuts and are useful for beginners and intermediate" +
                    "players alike. \n \n" +
                    "Recommended experience level: Beginner, Intermediate",
            "Catching \n \n" +
                    "Basic catching drills for beginners to learn how to catch a disc in various situations \n \n" +
                    "Recommended experience level: Beginner",
            "Break side throws \n \n" +
                    "Basic ideas for how to throw to successfully execute a break side throw. \n \n" +
                    "Recommended experience level: Intermediate",
            "Throwing a Backhand \n \n" +
                    "How to throw a backhand \n \n" +
                    "Recommended experience level: Beginner",
            "Throwing a Forehand \n \n" +
                    "How to throw a forehand \n \n" +
                    "Recommended experience level: Beginner",
            "Quick release backhand break \n \n" +
                    "How to execute a quick release backhand break side throw \n \n" +
                    "Recommended experience level: Intermediate"};

    private int[] experienceLevel = new int[]{2, 3, 0, 1, 1, 2, 1, 1, 2};

    private int currentDrill = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drills_page);

        AppDatabase db = AppDatabase.getDbInstance(DrillsPage.this);
        TeamDao teamDao = db.teamDao();
        PlayerDao playerDao = db.playerDao();
        GameDao gameDao = db.gameDao();

        player = (Player) getIntent().getSerializableExtra("player");
        int playerExperience;
        if (player.experienceLevel.equals("Beginner")) {
            playerExperience = 1;
        } else if (player.experienceLevel.equals("Intermediate")) {
            playerExperience = 2;
        } else {
            playerExperience = 3;
        }

        Button allDrills = findViewById(R.id.allDrills_button);

        Button backButton = findViewById(R.id.back_button);
        Button nextButton = findViewById(R.id.next_button);
        Button prevButton = findViewById(R.id.prev_button);

        WebView drillVideo = findViewById(R.id.webView);
        TextView drillDescription = findViewById(R.id.textView);

        allDrills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DrillsPage.this, AllDrillsPage.class);
                i.putExtra("player", player);
                startActivity(i);
            }
        });

        ArrayList<String> curatedLinks = new ArrayList<>();
        ArrayList<String> curatedDescriptions = new ArrayList<>();
        for (int i = 0; i < experienceLevel.length; i++) {
            if (experienceLevel[i] == playerExperience || experienceLevel[i] == 0) {
                curatedLinks.add(drillVideos[i]);
                curatedDescriptions.add(drillDescriptions[i]);
            }
        }

        drillVideo.getSettings().setJavaScriptEnabled(true);
        drillVideo.setWebViewClient(new WebViewClient());
        drillVideo.loadUrl(curatedLinks.get(currentDrill));

        drillDescription.setText(curatedDescriptions.get(currentDrill));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DrillsPage.this, PlayerPage.class);
                i.putExtra("player", player);
                startActivity(i);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDrill == curatedDescriptions.size() - 1) {
                    Toast.makeText(DrillsPage.this, "Already on last drill", Toast.LENGTH_SHORT).show();
                } else {
                    currentDrill++;
                    drillVideo.loadUrl(curatedLinks.get(currentDrill));
                    drillDescription.setText(curatedDescriptions.get(currentDrill));
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDrill == 0) {
                    Toast.makeText(DrillsPage.this, "Already on first drill", Toast.LENGTH_SHORT).show();
                } else {
                    currentDrill--;
                    drillVideo.loadUrl(curatedLinks.get(currentDrill));
                    drillDescription.setText(curatedDescriptions.get(currentDrill));
                }
            }
        });
    }
}