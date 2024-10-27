package com.example.bustracker1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class createacc extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailet, pswdet, repswdet;
    private Button createaccbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createacc);

        emailet = (EditText) findViewById(R.id.caccemailet);
        pswdet = (EditText) findViewById(R.id.caccpswdet);
        repswdet = (EditText) findViewById(R.id.caccpswdconfirmet);
        createaccbtn = (Button) findViewById(R.id.btncreateacc);

        createaccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createaccount();
            }
        });
    }

    private void createaccount() {

        String emailtxt = emailet.getText().toString();
        String pswdtxt = pswdet.getText().toString();
        String repswdtxt = repswdet.getText().toString();

        if (emailtxt.isEmpty()) {
            Toast.makeText(createacc.this, "Please enter email",
                    Toast.LENGTH_SHORT).show();
        } else if (pswdtxt.isEmpty()) {
            Toast.makeText(createacc.this, "Please enter password",
                    Toast.LENGTH_SHORT).show();
        } else if (repswdtxt.isEmpty()) {
            Toast.makeText(createacc.this, "Please reenter your password",
                    Toast.LENGTH_SHORT).show();
        }else if(!pswdtxt.equals(repswdtxt)){
            Toast.makeText(createacc.this, "You have entered incorrect confirmation password",
                    Toast.LENGTH_SHORT).show();
        }else{
            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(emailtxt,pswdtxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(createacc.this, "Account Successfully Created",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(createacc.this,setuppage.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(createacc.this, "Account Creation failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                    // ...
                }
            });
        }
        }

    }
