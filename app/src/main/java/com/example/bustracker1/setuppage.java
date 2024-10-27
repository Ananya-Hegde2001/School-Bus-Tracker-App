package com.example.bustracker1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class setuppage extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://bustracker1-de711-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("Students");
    FirebaseAuth mAuth;
    EditText pname,sname,phoneno,rollno,busno;
    Button donebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setuppage);


        pname = (EditText) findViewById(R.id.etparentsname);
        sname = (EditText) findViewById(R.id.etstudentname);
        phoneno = (EditText) findViewById(R.id.etphoneno);
        rollno = (EditText) findViewById(R.id.etrollno);
        busno = (EditText) findViewById(R.id.etbusno);
        donebtn = (Button) findViewById(R.id.btndone);

        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done();
            }
        });
    }
    private void done(){
        String pnametxt = pname.getText().toString();
        String snametxt = sname.getText().toString();
        String phonenotxt = phoneno.getText().toString();
        String rollnotxt = rollno.getText().toString();
        String busnotxt = busno.getText().toString();


        if (pnametxt.isEmpty()){
            Toast.makeText(setuppage.this,"Please Enter Parents Name",Toast.LENGTH_SHORT).show();
        }else if(snametxt.isEmpty()){
            Toast.makeText(setuppage.this,"Please Enter Students Name",Toast.LENGTH_SHORT).show();
        }else if(phonenotxt.isEmpty()){
            Toast.makeText(setuppage.this,"Please Enter Phone Number",Toast.LENGTH_SHORT).show();
        }else if(rollnotxt.isEmpty()){
            Toast.makeText(setuppage.this,"Please Enter Roll no ",Toast.LENGTH_SHORT).show();
        }else if(busnotxt.isEmpty()){
            Toast.makeText(setuppage.this,"Please Enter Bus no",Toast.LENGTH_SHORT).show();
        }else {

            mAuth = FirebaseAuth.getInstance();
            String userid = mAuth.getUid();
            String emailtxt = mAuth.getCurrentUser().getEmail();

            getdata studentdata = new getdata(pnametxt, snametxt, phonenotxt, rollnotxt, busnotxt,emailtxt);

            myRef.child(userid).setValue(studentdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(setuppage.this, "Login Successfull",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(setuppage.this, MainActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(setuppage.this, "failure",
                            Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}