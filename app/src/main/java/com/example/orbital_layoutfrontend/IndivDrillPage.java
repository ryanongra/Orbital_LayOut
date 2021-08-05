package com.example.orbital_layoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.orbital_layoutfrontend.db.Player;

public class IndivDrillPage extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indiv_drill_page);

        player = (Player) getIntent().getSerializableExtra("player");
        WebView drillVideo = findViewById(R.id.webView);
        TextView drillDescription = findViewById(R.id.textView);
        Button back = findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IndivDrillPage.this, AllDrillsPage.class);
                i.putExtra("player", player);
                startActivity(i);
            }
        });


        int currentDrill;

        String drill = (String) getIntent().getSerializableExtra("drill");
        switch (drill) {
            case "giveAndGo":
                currentDrill = 0;
                break;
            case "dumpSwing":
                currentDrill = 1;
                break;
            case "athleticism":
                currentDrill = 2;
                break;
            case "cutting":
                currentDrill = 3;
                break;
            case "catching":
                currentDrill = 4;
                break;
            case "breakside":
                currentDrill = 5;
                break;
            case "backhand":
                currentDrill = 6;
                break;
            case "forehand":
                currentDrill = 7;
                break;
            case "quickRelease":
                currentDrill = 8;
                break;
            default:
                currentDrill = 0;

        }

        drillVideo.getSettings().setJavaScriptEnabled(true);
        drillVideo.setWebViewClient(new WebViewClient());
        drillVideo.loadUrl(drillVideos[currentDrill]);

        drillDescription.setText(drillDescriptions[currentDrill]);
    }
}