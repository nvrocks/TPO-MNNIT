package com.example.mohit.tpomnnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.mohit.tpomnnit.R.id.username;

public class TpoSignup extends AppCompatActivity {
    private Button register;
    private EditText year,password,regnum,name;
    private TextView registered;
    private DatabaseReference mDatabase;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo_signup);
        mDatabase=FirebaseDatabase.getInstance().getReference("tpouser");
        userId     = mDatabase.push().getKey();
        register   = (Button)findViewById(R.id.register);
        regnum     = (EditText)findViewById(R.id.regnum);
        name       = (EditText)findViewById(username);
        year       = (EditText)findViewById(R.id.year);
        password   = (EditText)findViewById(R.id.password);
        registered = (TextView)findViewById(R.id.registered);
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(TpoSignup.this,TpoLogin.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                }
                else
                    createUser(name.getText().toString().trim(), year.getText().toString().trim(), regnum.getText().toString().trim(), password.getText().toString().trim(),userId);

                finish();
                Intent i = new Intent(TpoSignup.this,TpoLogin.class);
                startActivity(i);
            }
        });
    }
    private void createUser(String name, String year,String regnum,String password,String userid) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userid)) {
//            userId = mDatabase.push().getKey();
        }
        Users user = new Users(name, year,regnum,password,userid);
        addUserChangeListener(user);
    }
    private void addUserChangeListener(final Users user) {
        // User data change listener
        mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user1 = user;

                // Check for null
                if (user1 == null) {
                    return;
                }
                final String userId = mDatabase.push().getKey();
                mDatabase.child(userId).setValue(user1);
              //  Log.e(TAG, "User data is changed!" + user1.name + ", " + user1.year);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               // Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }
}