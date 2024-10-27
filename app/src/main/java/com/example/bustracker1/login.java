package com.example.bustracker1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailet, pswdet;
    Button loginbtn, createaccbtn, btndriver;
    ImageView adminlogo;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://bustracker1-de711-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("Students");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailet = (EditText) findViewById(R.id.etemail);
        pswdet = (EditText) findViewById(R.id.etpass);
        loginbtn = (Button) findViewById(R.id.btnlogin);
        createaccbtn = (Button) findViewById(R.id.btncreateacc);
        btndriver = (Button) findViewById(R.id.driverbtn);
        adminlogo = (ImageView) findViewById(R.id.imageView);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        createaccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createacc();
            }
        });
        btndriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                driverpager();
            }
        });
        adminlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminpage();
            }
        });
    }

    private void login() {
        String emailtxt = emailet.getText().toString();
        String pswdtxt = pswdet.getText().toString();

        if (emailtxt.isEmpty()) {
            Toast.makeText(login.this, "Please enter email",
                    Toast.LENGTH_SHORT).show();
        } else if (pswdtxt.isEmpty()) {
            Toast.makeText(login.this, "Please enter password",
                    Toast.LENGTH_SHORT).show();
        } else {

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
                                                Toast.makeText(login.this, "Login Successfull",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(login.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
    }

    private void createacc() {
        Intent intent = new Intent(login.this, createacc.class);
        startActivity(intent);
    }

    private void driverpager() {
        Intent intent = new Intent(login.this, driverlogin.class);
        startActivity(intent);
    }

    private void adminpage() {
        Intent intent = new Intent(login.this, adminlogin.class);
        startActivity(intent);
    }
}