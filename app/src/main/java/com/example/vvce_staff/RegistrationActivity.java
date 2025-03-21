package com.example.vvce_staff;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.vvce_staff.networkUtils.DepartmentsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity {
    EditText fullNameEt,emailEt,passwordEt,confirmPasswordEt;
    EditText facultyIdEt, facultyDesignationEt, usnEt;
    RadioButton studentRb,facultyRb;
    Button registerBtn;
    private String fullName,email,usn,facultyId,facultyDesignation;
    private String password,confirmPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fullNameEt = findViewById(R.id.full_name_et);
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        confirmPasswordEt = findViewById(R.id.confirm_password_et);

        facultyIdEt = findViewById(R.id.faculty_id_et);
        facultyDesignationEt = findViewById(R.id.designation_et);

        usnEt = findViewById(R.id.usn_et);

        studentRb = findViewById(R.id.student_rb);
        facultyRb = findViewById(R.id.faculty_rb);

        registerBtn = findViewById(R.id.register_btn);

        mAuth = FirebaseAuth.getInstance();

        facultyRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facultyDesignationEt.setVisibility(View.VISIBLE);
                facultyIdEt.setVisibility(View.VISIBLE);

                usnEt.setVisibility(View.GONE);
            }
        });

        studentRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usnEt.setVisibility(View.VISIBLE);

                facultyDesignationEt.setVisibility(View.GONE);
                facultyIdEt.setVisibility(View.GONE);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    fullName = fullNameEt.getText().toString();
                    email = emailEt.getText().toString();

                    facultyId = facultyIdEt.getText().toString();
                    facultyDesignation = facultyDesignationEt.getText().toString();

                    usn = usnEt.getText().toString();

                    password = passwordEt.getText().toString();
                    confirmPassword = confirmPasswordEt.getText().toString();


                } catch (Exception e){
                    Toast.makeText(RegistrationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                /*fullName empty check
                *if fullName is empty toast message "enter name"
                */
            if(TextUtils.isEmpty(fullName)){
                Toast.makeText(RegistrationActivity.this, "enter name", Toast.LENGTH_SHORT).show();
            }else if(!TextUtils.isEmpty(fullName)){

               /*email empty check
                *if email is empty toast message "enter email"
                *if email is not valid toast message "enter valid email"
                */
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegistrationActivity.this, "enter email", Toast.LENGTH_SHORT).show();
                }else if(!TextUtils.isEmpty(email)){
                    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            Toast.makeText(RegistrationActivity.this, "enter valid email", Toast.LENGTH_SHORT).show();
                        }else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){


                 /*
                 check for faculty radiobutton
                  */
                        if(!(facultyRb).isChecked()) {

                        /*usn empty check
                         *if usn is  empty toast message"enter usn"
                         */
                        if(TextUtils.isEmpty(usn)){
                            Toast.makeText(RegistrationActivity.this, "enter usn", Toast.LENGTH_SHORT).show();
                        }else if(!TextUtils.isEmpty(usn)){

                            /*
                             *compare password and confirmPassword
                             * if password entered is same intent RegistrationActivity.java
                             */
                            if(TextUtils.isEmpty(password)){
                                Toast.makeText(RegistrationActivity.this, "enter password", Toast.LENGTH_SHORT).show();
                            }else if(!TextUtils.isEmpty(password)){
                            if(password.equals(confirmPassword)){
                                createAccount(email, password, fullName);
                                Intent intent = new Intent(RegistrationActivity.this, DepartmentsActivity.class);
                                startActivity(intent);
                            }
                            if(!password.equals(confirmPassword)){
                                Toast.makeText(RegistrationActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                            }
                            }

                        }
                        /*
                        if facultyRb is checked

                         */
                            }else if(facultyRb.isChecked()){

                            /*
                            check for facultyId
                            if facultyId is empty toast message "enter Id"
                             */
                            if(TextUtils.isEmpty(facultyId)){
                                Toast.makeText(RegistrationActivity.this, "enter Id", Toast.LENGTH_SHORT).show();
                            }else if(!TextUtils.isEmpty(facultyId)){
                                /*
                                check for facultyDesignation
                                if facultyDesignation is empty toast message "enter destination"
                                 */

                                if(TextUtils.isEmpty(facultyDesignation)){
                                    Toast.makeText(RegistrationActivity.this, "enter destination", Toast.LENGTH_SHORT).show();
                                }else if(!TextUtils.isEmpty(facultyDesignation)) {
                                    /*
                                     *compare password and confirmPassword
                                     * if password entered is same intent RegistrationActivity.java
                                     */
                                    if (TextUtils.isEmpty(password)) {
                                        Toast.makeText(RegistrationActivity.this, "enter password", Toast.LENGTH_SHORT).show();
                                    } else if (!TextUtils.isEmpty(password)) {
                                        if (password.equals(confirmPassword)) {
                                            createAccount(email, password, fullName);
                                            Intent intent = new Intent(RegistrationActivity.this, DepartmentsActivity.class);
                                            startActivity(intent);
                                        }
                                        if (!password.equals(confirmPassword)) {
                                            Toast.makeText(RegistrationActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        }
                        }


                }
            }



            }
        });
    }

    private void createAccount(String email, String password, final String name){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).
                                    build();
                            user.updateProfile(profileUpdate);
                        } else{
                            Toast.makeText(RegistrationActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

