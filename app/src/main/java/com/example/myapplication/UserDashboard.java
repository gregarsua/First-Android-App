package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDashboard extends AppCompatActivity {

    private FirebaseUser user;
    private String userID;
    Button button_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        button_logout = (Button) findViewById(R.id.button_logout);
        DatabaseReference reference;

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            //LOGOUT BUTTON
            public void onClick(View v) {
                Intent logout = new Intent(getApplicationContext(),Activity.class);
                startActivity(logout);
            }
        });

        //GET USER CREDENTIALS FROM DATABASE
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView extractFullname = (TextView) findViewById(R.id.dash_full);
        final TextView extractEmail = (TextView) findViewById(R.id.textView5);
        final TextView extractUsername = (TextView) findViewById(R.id.dash_user);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null ){

                    String fullname = userProfile.getFullName();
                    String email = userProfile.getEmail();
                    String username = userProfile.getUsername();

                    extractFullname.setText(fullname);
                    extractEmail.setText(email);
                    extractUsername.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG).show();
                Intent goDash = new Intent(getApplicationContext(), UserDashboard.class);
                startActivity(goDash);
            }
        });

    }
}