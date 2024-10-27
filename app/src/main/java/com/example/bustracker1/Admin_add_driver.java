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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_add_driver extends AppCompatActivity {

    EditText dname,dbusno,demail,dpswd;
    Button addbtn;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://bustracker1-de711-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_driver);

        mAuth = FirebaseAuth.getInstance();

        dname = (EditText) findViewById(R.id.drivernameet);
        dbusno = (EditText) findViewById(R.id.driverbusno);
        demail = (EditText) findViewById(R.id.driveremailet);
        dpswd = (EditText) findViewById(R.id.driverpswdet);
        addbtn = (Button) findViewById(R.id.adddriverbtn);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dnametxt = dname.getText().toString();
                String dbusnotxt = dbusno.getText().toString();
                String demailtxt = demail.getText().toString();
                String dpswdtxt = dpswd.getText().toString();
                String messagetxt ="";

                getbusdriverdata getdata = new getbusdriverdata(dnametxt,demailtxt,dpswdtxt,messagetxt);


                if(dnametxt.isEmpty()){
                    Toast.makeText(Admin_add_driver.this,"Please Enter Drivers Name",
                            Toast.LENGTH_SHORT).show();
                }else  if(dbusnotxt.isEmpty()) {
                    Toast.makeText(Admin_add_driver.this, "Please Enter Bus No",
                            Toast.LENGTH_SHORT).show();
                }else  if(demailtxt.isEmpty()) {
                    Toast.makeText(Admin_add_driver.this, "Please Enter Email",
                            Toast.LENGTH_SHORT).show();
                }else  if(dpswdtxt.isEmpty()) {
                    Toast.makeText(Admin_add_driver.this, "Please Enter Password",
                            Toast.LENGTH_SHORT).show();
                }else {


                    mAuth.createUserWithEmailAndPassword(demailtxt,dpswdtxt).addOnCompleteListener
                            (Admin_add_driver.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user.
                            if (task.isSuccessful()) {

                                myRef.child("Drivers").child(mAuth.getCurrentUser().getUid()).setValue(dbusnotxt);
                                myRef.child("Bus no").child(dbusnotxt).setValue(getdata);
                                // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(Admin_add_driver.this, "Account Successfully Created",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Admin_add_driver.this, admindriverpage.class);
                                startActivity(intent);
                            }else {
                                  Toast.makeText(Admin_add_driver.this, "Error while creating account",
                                          Toast.LENGTH_SHORT).show();


                            }

                        }
                    });


                }
            }
        });

    }
}