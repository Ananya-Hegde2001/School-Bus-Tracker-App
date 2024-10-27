package com.example.bustracker1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button getloc,getmsg,signoutbtn;
    TextView putmsg;

    FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://bustracker1-de711-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        getloc = (Button) findViewById(R.id.getlocbtn);
        getmsg = (Button) findViewById(R.id.getmsgbtn);
        signoutbtn = (Button) findViewById(R.id.signoutbtn);
        putmsg = (TextView) findViewById(R.id.tvmsg);

        getloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(MainActivity.this,drivermap.class);
                startActivity(intent);
            }
        });

        getmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        String busno = dataSnapshot.child("Students").child(uid).child("busno").getValue().toString();


                        String msgtxt = dataSnapshot.child("Bus no").child(busno).child("message").getValue().toString();

                        if (msgtxt.isEmpty()) {
                            Toast.makeText(MainActivity.this, "No message from driver",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            putmsg.setText(msgtxt);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(MainActivity.this, "Error while fetching data from database",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            });
        signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this,login.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Signed out",
                        Toast.LENGTH_SHORT).show();
            }
        });
        }
    }