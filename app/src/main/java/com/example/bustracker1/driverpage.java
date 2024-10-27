package com.example.bustracker1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class driverpage extends AppCompatActivity {

     Button getlbtn,msgbtn;
     EditText msget;

     private FirebaseAuth mAuth;

     FirebaseDatabase database = FirebaseDatabase.getInstance("https://bustracker1-de711-default-rtdb.asia-southeast1.firebasedatabase.app/");
     DatabaseReference myRef = database.getReference();

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_driverpage);


         getlbtn = (Button) findViewById(R.id.btnlocation);
         msgbtn = (Button) findViewById(R.id.btnmsg);
         msget = (EditText) findViewById(R.id.etmsg);

         getlbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(driverpage.this, driverlocation.class);
                 startActivity(intent);
             }
         });

         msgbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 mAuth = FirebaseAuth.getInstance();
                 String uid = mAuth.getCurrentUser().getUid();
                 String msgtxt = msget.getText().toString();

                 myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         String busno = Objects.requireNonNull(snapshot.child("Drivers").child(uid).getValue()).toString();

                         myRef.child("Bus No").child(busno).child("Message").setValue(msgtxt);

                         Toast.makeText(driverpage.this,"Message sent",Toast.LENGTH_SHORT).show();


                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {
                         Toast.makeText(driverpage.this,"Message not sent",Toast.LENGTH_SHORT).show();
                     }

                 });
             }
     });
 }
 private void clearmsg(){
     mAuth = FirebaseAuth.getInstance();
     String uid = mAuth.getCurrentUser().getUid();
     String msgtxt = msget.getText().toString();

     myRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             String busno = Objects.requireNonNull(snapshot.child("Drivers").child(uid).getValue()).toString();

             myRef.child("Bus No").child(busno).child("Message").setValue("");




         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
             Toast.makeText(driverpage.this,"Message not sent",Toast.LENGTH_SHORT).show();
         }

     });

 }

}
