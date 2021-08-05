package com.example.orbital_layoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.orbital_layoutfrontend.db.Player;

public class AllDrillsPage extends AppCompatActivity {

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_drills_page);

        Button back = findViewById(R.id.back_button2);
        Button dumpSwing= findViewById(R.id.dumpSwing);
        Button giveAndGo = findViewById(R.id.giveAndGo);
        Button athleticism = findViewById(R.id.athleticism);
        Button cutting = findViewById(R.id.cutting);
        Button catching = findViewById(R.id.catching);
        Button breakside = findViewById(R.id.breakside);
        Button backhand = findViewById(R.id.backhand);
        Button forehand = findViewById(R.id.forehand);
        Button quickRelease= findViewById(R.id.quickRelease);

        player = (Player) getIntent().getSerializableExtra("player");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllDrillsPage.this, DrillsPage.class);
                i.putExtra("player", player);
                startActivity(i);
            }
        });

        dumpSwing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllDrillsPage.this, IndivDrillPage.class);
                i.putExtra("drill", "dumpSwing");
                i.putExtra("player", player);
                startActivity(i);
            }
        });
        giveAndGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllDrillsPage.this, IndivDrillPage.class);
                i.putExtra("drill", "giveAndGo");
                i.putExtra("player", player);
                startActivity(i);
            }
        });
        athleticism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllDrillsPage.this, IndivDrillPage.class);
                i.putExtra("drill", "athleticism");
                i.putExtra("player", player);
                startActivity(i);
            }
        });
        cutting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllDrillsPage.this, IndivDrillPage.class);
                i.putExtra("drill", "cutting");
                i.putExtra("player", player);
                startActivity(i);
            }
        });
        catching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllDrillsPage.this, IndivDrillPage.class);
                i.putExtra("drill", "catching");
                i.putExtra("player", player);
                startActivity(i);
            }
        });
        breakside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllDrillsPage.this, IndivDrillPage.class);
                i.putExtra("drill", "breakside");
                i.putExtra("player", player);
                startActivity(i);
            }
        });
        backhand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllDrillsPage.this, IndivDrillPage.class);
                i.putExtra("drill", "backhand");
                i.putExtra("player", player);
                startActivity(i);
            }
        });
        forehand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllDrillsPage.this, IndivDrillPage.class);
                i.putExtra("drill", "forehand");
                i.putExtra("player", player);
                startActivity(i);
            }
        });
        quickRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllDrillsPage.this, IndivDrillPage.class);
                i.putExtra("drill", "quickRelease");
                i.putExtra("player", player);
                startActivity(i);
            }
        });
    }
}