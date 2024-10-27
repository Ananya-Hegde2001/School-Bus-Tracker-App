package com.example.bustracker1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class editstudentdetails extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://bustracker1-de711-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("Students");
    FirebaseAuth mAuth;
    EditText pname, sname, phoneno, rollno, busno;
    Button donebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstudentdetails);

        pname = (EditText) findViewById(R.id.etparentsnameedit);
        sname = (EditText) findViewById(R.id.etstudentnameedit);
        phoneno = (EditText) findViewById(R.id.etphonenoedit);
        rollno = (EditText) findViewById(R.id.etrollnoedit);
        busno = (EditText) findViewById(R.id.etbusnoedit);
        donebtn = (Button) findViewById(R.id.btndoneedit);

        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done();
            }
        });
    }

    private void done() {
        String pnametxt = pname.getText().toString();
        String snametxt = sname.getText().toString();
        String phonenotxt = phoneno.getText().toString();
        String rollnotxt = rollno.getText().toString();
        String busnotxt = busno.getText().toString();


        if (pnametxt.isEmpty()) {
            Toast.makeText(editstudentdetails.this, "Please Enter Parents Name", Toast.LENGTH_SHORT).show();
        } else if (snametxt.isEmpty()) {
            Toast.makeText(editstudentdetails.this, "Please Enter Students Name", Toast.LENGTH_SHORT).show();
        } else if (phonenotxt.isEmpty()) {
            Toast.makeText(editstudentdetails.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
        } else if (rollnotxt.isEmpty()) {
            Toast.makeText(editstudentdetails.this, "Please Enter Roll no ", Toast.LENGTH_SHORT).show();
        } else if (busnotxt.isEmpty()) {
            Toast.makeText(editstudentdetails.this, "Please Enter Bus no", Toast.LENGTH_SHORT).show();
        } else {

            mAuth = FirebaseAuth.getInstance();
            String userid = mAuth.getUid();
            String emailtxt = mAuth.getCurrentUser().getEmail();

            getdata studentdata = new getdata(pnametxt, snametxt, phonenotxt, rollnotxt, busnotxt, emailtxt);

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dchild:snapshot.getChildren()){
                        if(dchild.child("roll").getValue().toString().equals(rollnotxt)){

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}