package com.example.kai.verschachtelt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUI();
    }

    private void setUpUI() {
        //Setup the UI-Elements of The MainActivity
        Button launchGameActivity = (Button) findViewById(R.id.showGameActivityButton);

        launchGameActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the button is pressed launch the Game-Activity
                Intent startGame = new Intent(MainActivity.this, GameActivity.class);
                startActivity(startGame);
            }
        });
    }
}
