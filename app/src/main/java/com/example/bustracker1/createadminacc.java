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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class createadminacc extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailet, pswdet, repswdet;
    private Button createaccbtn;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://bustracker1-de711-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createadminacc);

        emailet = (EditText) findViewById(R.id.admincaccemailet);
        pswdet = (EditText) findViewById(R.id.admincaccpswdet);
        repswdet = (EditText) findViewById(R.id.admincaccpswdconfirmet);
        createaccbtn = (Button) findViewById(R.id.adminbtncreateacc);

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
            Toast.makeText(createadminacc.this, "Please enter email",
                    Toast.LENGTH_SHORT).show();
        } else if (pswdtxt.isEmpty()) {
            Toast.makeText(createadminacc.this, "Please enter password",
                    Toast.LENGTH_SHORT).show();
        } else if (repswdtxt.isEmpty()) {
            Toast.makeText(createadminacc.this, "Please reenter your password",
                    Toast.LENGTH_SHORT).show();
        }else if(!pswdtxt.equals(repswdtxt)){
            Toast.makeText(createadminacc.this, "You have entered incorrect confirmation password",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild("Admin")) {
                        Toast.makeText(createadminacc.this, "Admin already exists",
                                Toast.LENGTH_SHORT).show();
                    }
                        else{
                            mAuth = FirebaseAuth.getInstance();
                            mAuth.createUserWithEmailAndPassword(emailtxt,pswdtxt).
                                    addOnCompleteListener(createadminacc.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        myRef.child("Admin").setValue(mAuth.getCurrentUser().getEmail());
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(createadminacc.this, "Account Successfully Created",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(createadminacc.this,admin.class);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(createadminacc.this, "Account Creation failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });

                        }
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}