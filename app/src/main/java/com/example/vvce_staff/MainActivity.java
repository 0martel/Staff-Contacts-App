package com.example.vvce_staff;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText emailEt,passEt;
    RadioGroup usersRg;
    Button loginBtn,registerBtn,signInBtn;
    private String emailId,password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEt = findViewById(R.id.email_et);
        passEt =  findViewById(R.id.password_et);

        usersRg =  findViewById(R.id.user_rg);

        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);
        signInBtn = findViewById(R.id.sign_in_btn);

        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailEt.setVisibility(View.VISIBLE);
                passEt.setVisibility(View.VISIBLE);
                usersRg.setVisibility(View.VISIBLE);
                signInBtn.setVisibility(View.VISIBLE);

                registerBtn.setVisibility(View.GONE);
                loginBtn.setVisibility(View.GONE);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(emailId, password);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    emailId = emailEt.getText().toString();
                    password = passEt.getText().toString();
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(MainActivity.this,RegistrationActivity.class);
                            startActivity(i);
                        } else{
                            Toast.makeText(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
