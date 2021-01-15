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
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText fullname;
    EditText username;
    EditText password;
    EditText email;
    Button button_submit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        mAuth = FirebaseAuth.getInstance();

        username = (EditText) findViewById(R.id.username2);
        fullname = (EditText) findViewById(R.id.fullName);
        password = (EditText) findViewById(R.id.password2);
        email= (EditText) findViewById(R.id.email);
        button_submit = (Button) findViewById(R.id.button_submit);

        button_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addUser();
            }

            private void addUser() {
                String user_fullname = fullname.getText().toString().trim();
                String user_username = username.getText().toString().trim();
                String user_password = password.getText().toString().trim();
                String user_email = email.getText().toString().trim();

                //VALIDATE USER INPUT
                if(user_fullname.isEmpty()){
                    fullname.setError("Field is required");
                    fullname.requestFocus();
                    return;
                }
                if(user_email.isEmpty()){
                    email.setError("Field is required");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
                    email.setError("Please input valid email address");
                    email.requestFocus();
                    return;
                }
                if(user_username.isEmpty()){
                    username.setError("Field is required");
                    username.requestFocus();
                    return;
                }
                if(user_password.isEmpty()){
                    password.setError("Field is required");
                    password.requestFocus();
                    return;
                }
                if(user_password.length() < 6){
                    password.setError("Password requires 6 or more characters");
                    password.requestFocus();
                    return;
                }

                //READY TO PUT ON FIREBASE DATABASE AND AUTHENTICATION
                mAuth.createUserWithEmailAndPassword(user_email,user_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user = new User(user_email, user_fullname, user_password, user_username);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(getApplicationContext(),"Success!",Toast.LENGTH_LONG).show();
                                                        Intent loginPage = new Intent(getApplicationContext(), Activity.class);
                                                        startActivity(loginPage);
                                                    }else{
                                                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                }else{
                                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                }
                            }
                        });



//BRACKET FOR ADD_USER FUNCTION
            }
        });
    }
}
