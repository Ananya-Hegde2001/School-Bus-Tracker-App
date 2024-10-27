package com.example.bustracker1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin extends AppCompatActivity {
    Button studentbtn,driverbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        studentbtn = (Button) findViewById(R.id.studentdetailsbtn);
        driverbtn = (Button) findViewById(R.id.driverdetailsbtn);

        studentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin.this,adminstudentpage.class);
                startActivity(intent);
            }
        });
        driverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin.this,admindriverpage.class);
                startActivity(intent);
            }
        });

    }
}