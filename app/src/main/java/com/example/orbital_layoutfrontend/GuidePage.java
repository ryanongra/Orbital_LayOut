package com.example.orbital_layoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GuidePage extends AppCompatActivity {

    final String guideMsg = "Welcome to Lay Out, your Ultimate Frisbee training companion! This " +
                            "page will take you through the features of this app and how to use " +
                            "it. \n" +
                            "\n" +
                            "You can TRACK a player during games in the TRACK PAGE by pressing the " +
                            "buttons based on the player's actions during a game. This app was " +
                            "designed to have a teammate or coach on the sideline monitoring and " +
                            "tracking a player who is on the field. Alternatively, players can use " +
                            "the app to analyse videos of their own games after a match" + "\n" +
                            "\n" +
                            "Once you have tracked at least one game, you can ANALYSE that player's " +
                            "performance in the ANALYSE PAGE. This will give insights into a player's " +
                            "strengths and weaknesses, as well as their improvement across games. \n" +
                            "\n" +
                            "Finally, you can find recommended DRILLS in the DRILL PAGE which will " +
                            "help you work on weaknesses. The drills are tailored to your experience " +
                            "level and trains a wide variety of skills." + "\n" +
                            "\n" +
                            "We hope that this app will be able to take your Ultimate Frisbee game " +
                            "to the next level. Now go out there and make some sick plays!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);

        Button backButton = findViewById(R.id.back_button);
        TextView guideText = findViewById(R.id.guide_text);

        guideText.setText(guideMsg);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuidePage.this, MainActivity.class));
            }
        });

    }
}