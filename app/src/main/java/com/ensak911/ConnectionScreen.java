package com.ensak911;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ensak911.demandes.activities.ReceivedDemandsListActivity;
import com.ensak911.demandes.activities.SentDemandsListActivity;


public class ConnectionScreen extends AppCompatActivity {

    EditText userEditText, passwordEditText;
    Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref=getSharedPreferences("Ensak911",MODE_PRIVATE);
        if(pref.getBoolean("connected",false))
        {
            if(pref.getString("type","student").equals("student"))
            {
                Intent connexionIntent=new Intent(ConnectionScreen.this, SentDemandsListActivity.class);
                startActivity(connexionIntent);
                finish();
            }else{
                Intent connexionIntent=new Intent(ConnectionScreen.this, ReceivedDemandsListActivity.class);
                startActivity(connexionIntent);
                finish();

            }
        }

        setContentView(R.layout.activity_connection_screen);
        userEditText=(EditText)findViewById(R.id.user_edittext);
        passwordEditText=(EditText)findViewById(R.id.password_edittext);
        connectButton=(Button) findViewById(R.id.connect_button);
        final SharedPreferences preferences=pref;
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userEditText.getText().toString().equals("student"))
                {
                    preferences.edit().putBoolean("connected",true).commit();
                    preferences.edit().putString("type","student").commit();
                    Intent connexionIntent=new Intent(ConnectionScreen.this, SentDemandsListActivity.class);
                    startActivity(connexionIntent);
                    finish();
                }else{
                    preferences.edit().putBoolean("connected",true).commit();
                    preferences.edit().putString("type","direction").commit();
                    Intent connexionIntent=new Intent(ConnectionScreen.this, ReceivedDemandsListActivity.class);
                    startActivity(connexionIntent);
                    finish();
                }
            }
        });


    }
}
