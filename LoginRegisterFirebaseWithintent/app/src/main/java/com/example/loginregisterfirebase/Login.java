package com.example.loginregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    public static final String fname = "fullname";
    public static final String mail = "fullname";
    public static final String pwd = "fullname";

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learnoset-18786-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String phoneTxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();

                if(phoneTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(Login.this,"Please enter your mobile or password",Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if mobile/phone already exists in DB
                            if(snapshot.hasChild(phoneTxt)){

                                //mobile is in exist in firebase DB
                                //now get pass of the user from firebase DB and match it with user entered pass

                                final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);
                                final String email = snapshot.child(phoneTxt).child("email").getValue(String.class);
                                final String fullname = snapshot.child(phoneTxt).child("fullname").getValue(String.class);

                                //Authentication area
                                if(getPassword.equals(passwordTxt)){
                                    Toast.makeText(Login.this,"Login Successfully 🤩", Toast.LENGTH_SHORT).show();

                                    //Open MainActivity on success
                                    Intent home = new Intent(Login.this, MainActivity.class);
                                    home.putExtra("fullname", fullname);
                                    home.putExtra("email", email);
                                    home.putExtra("pwd", getPassword);
                                    startActivity(home);
                                    //startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(Login.this,"Wrong Password 🎃", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Login.this,"Wrong Mobile Number 💥", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Open Register Activity
                startActivity(new Intent(Login.this,Register.class));

            }
        });
    }
}