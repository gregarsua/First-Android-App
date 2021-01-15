package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Activity extends AppCompatActivity {

    EditText editTextTextEmailAddress, password;
    Button button_login2;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.password);
        button_login2 = findViewById(R.id.button_login);
        mAuth = FirebaseAuth.getInstance();

            LoginClick();

        //REGISTER PAGE
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), Register.class);
                startActivity(activity2Intent);
            }
        });
    }

    private void LoginClick() {
        button_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = editTextTextEmailAddress.getText().toString().trim();
                String user_password = password.getText().toString().trim();

                if(user_email.isEmpty()){
                    editTextTextEmailAddress.setError("Field can't be empty");
                    editTextTextEmailAddress.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
                    editTextTextEmailAddress.setError("Please input valid email address");
                    editTextTextEmailAddress.requestFocus();
                    return;
                }
                if(user_password.isEmpty()){
                    password.setError("Field can't be empty");
                    password.requestFocus();
                    return;
                }
                if(user_password.length() < 6){
                    password.setError("Password requires 6 or more characters");
                    password.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()) {
                                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                                Intent login = new Intent(getApplicationContext(), UserDashboard.class);
                                startActivity(login);
                            }else{
                                user.sendEmailVerification();
                                Toast.makeText(Activity.this, "Check email to verify account",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Please check your credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}

