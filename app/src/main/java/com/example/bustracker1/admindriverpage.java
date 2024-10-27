package com.example.bustracker1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bustracker1.databinding.ActivityDrivermapBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class admindriverpage extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText dbview,removeemail;
    Button addbtn,removebtn,donebtn;



    FirebaseDatabase database = FirebaseDatabase.getInstance("https://bustracker1-de711-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("Bus no");
    DatabaseReference db =database.getReference("Drivers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindriverpage);

        dbview = (EditText) findViewById(R.id.databaseview);

        addbtn = (Button) findViewById(R.id.driveraddbtn);
        removebtn = (Button) findViewById(R.id.driveremovebtn);
        donebtn = (Button) findViewById(R.id.removedonebtn);
        removeemail = (EditText) findViewById(R.id.removeemailet);



        removeemail.setVisibility(View.INVISIBLE);
        donebtn.setVisibility(View.INVISIBLE);
        dbview.getText().clear();

       myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {

                   String Busno = childSnapshot.getKey().toString();
                   String nametxt = childSnapshot.child("name").getValue().toString();
                   String emailtxt = childSnapshot.child("email").getValue().toString();
                   String pswdtxt = childSnapshot.child("password").getValue().toString();

                   if(childSnapshot.hasChild("Location")) {
                       String lattxt = childSnapshot.child("Location").child("latitude").getValue().toString();
                       String longtxt = childSnapshot.child("Location").child("longitude").getValue().toString();
                       dbview.append("Busno:" + Busno + ":\n" + "Name:" + nametxt + "\n" +  "Email:" + emailtxt +
                               "\n" +  "Password:" + pswdtxt + "\n"   + "Latitude:" + lattxt + "\n" +"Longitude:" + longtxt + "\n" );
                   }else{
                       String lattxt ="";
                       String longtxt ="";
                       dbview.append("Busno:" + Busno + ":\n" + "Name:" + nametxt + "\n" +  "Email:" + emailtxt +
                               "\n" +  "Password:" + pswdtxt + "\n"   + "Latitude:" + lattxt + "\n" +"Longitude:" + longtxt + "\n" );
                   }

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admindriverpage.this,Admin_add_driver.class);
                startActivity(intent);

            }
        });
        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeemail.setVisibility(View.VISIBLE);
                donebtn.setVisibility(View.VISIBLE);

            }
        });
        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String busnotxt = removeemail.getText().toString();
                if(busnotxt.isEmpty()){
                    Toast.makeText(admindriverpage.this,"Please enter email",
                            Toast.LENGTH_SHORT).show();
                }else {
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                                if(childSnapshot.getValue().toString().equals(busnotxt)){
                                    String busno =childSnapshot.getValue().toString();
                                    childSnapshot.getRef().removeValue();
                                    myRef.child(busno).removeValue();
                                }
                                finish();
                                startActivity(getIntent());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

}



