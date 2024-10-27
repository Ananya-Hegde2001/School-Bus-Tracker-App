package com.example.bustracker1;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
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

public class adminstudentpage extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText dbview,removeemail;
    Button addbtn,removebtn,donebtn;


    FirebaseDatabase database = FirebaseDatabase.getInstance("https://bustracker1-de711-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("Students");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminstudentpage);

         dbview = (EditText) findViewById(R.id.studentdbview);
         removeemail =(EditText) findViewById(R.id.studentremoveemailet);
         removebtn = (Button) findViewById(R.id.studentremovebtn);
         donebtn =(Button) findViewById(R.id.studentremovedonebtn);

         removeemail.setVisibility(View.INVISIBLE);
         donebtn.setVisibility(View.INVISIBLE);
         dbview.getText().clear();

         myRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot childSnapshot: snapshot.getChildren()) {

                     String email = childSnapshot.child("email").getValue().toString();
                     String parent = childSnapshot.child("parent").getValue().toString();
                     String phoneno = childSnapshot.child("pno").getValue().toString();
                     String roll =  childSnapshot.child("roll").getValue().toString();
                     String sname = childSnapshot.child("student").getValue().toString();
                     dbview.append("Student:" + "\n" + "Parent Name:" + parent + "\n" +  "Student Name:" + sname +
                             "\n" +  "Roll no:" + roll + "\n"   + "Email:" + email + "\n" +"Phone no:" + phoneno + "\n" );


                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

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
                 String emailtxt = removeemail.getText().toString();
                 if(emailtxt.isEmpty()){
                     Toast.makeText(adminstudentpage.this,"Please enter roll no",
                             Toast.LENGTH_SHORT).show();
                 }else {
                     myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             for (DataSnapshot childsnapshot:snapshot.getChildren()){
                                 if(childsnapshot.child("roll").getValue().toString().equals(emailtxt)){
                                     childsnapshot.getRef().removeValue();
                                     finish();
                                     startActivity(getIntent());
                                 }
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