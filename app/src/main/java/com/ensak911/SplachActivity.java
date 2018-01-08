package com.ensak911;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplachActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splach);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplachActivity.this,ConnectionScreen.class);
                SplachActivity.this.startActivity(mainIntent);
                SplachActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
