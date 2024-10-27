package com.example.bustracker1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class driverlogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailet,pswdet,busnoet;
    Button loginbtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://bustracker1-de711-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("Bus no");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverlogin);

        pswdet = (EditText) findViewById(R.id.etpassdriver);
        emailet = (EditText) findViewById(R.id.etemaildriver);
        loginbtn = (Button) findViewById(R.id.btnlogindriver);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {


        String emailtxt = emailet.getText().toString();
        String pswdtxt = pswdet.getText().toString();


        if (emailtxt.isEmpty()) {
            Toast.makeText(driverlogin.this, "Please enter email",
                    Toast.LENGTH_SHORT).show();
        } else if (pswdtxt.isEmpty()) {
            Toast.makeText(driverlogin.this, "Please enter password",
                    Toast.LENGTH_SHORT).show();
        }
       else {

            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(emailtxt, pswdtxt)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                String emailtxt = mAuth.getCurrentUser().getEmail().toString();
                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot childsnapshot : snapshot.getChildren()){
                                            if (childsnapshot.child("email").getValue().toString().equals(emailtxt)){
                                                // Sign in success, update UI with the signed-in user's information
                                                Toast.makeText(driverlogin.this, "Login Successfull",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(driverlogin.this, driverpage.class);
                                                startActivity(intent);
                                            }else {
                                                Toast.makeText(driverlogin.this, "Wrong User.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(driverlogin.this, "Login failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
    }
}
