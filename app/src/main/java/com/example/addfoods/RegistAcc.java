package com.example.addfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class RegistAcc extends AppCompatActivity {

    EditText email, pass, name;
    FirebaseAuth mAuth;

    Button eeq;
    TextView eep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_acc);

        mAuth = FirebaseAuth.getInstance();


        email = findViewById(R.id.input_email);
        pass = findViewById(R.id.input_password);
        name = findViewById(R.id.input_confirm);
        eeq = findViewById(R.id.btn_login);
        eep = findViewById(R.id.textView3);

        eep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistAcc.this, Login.class));

            }
        });


        eeq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
    }

    private void verify() {
        String mEmail = email.getText().toString();
        final String mName = name.getText().toString();
        String mPass = pass.getText().toString();

        if (mEmail.isEmpty()){
            pesan("Email harus diisi");
            return;
        }
        if (mName.isEmpty()){
            pesan("Nama harus diisi");
            return;
        }
        if (mPass.isEmpty()){
            pesan("Password harus diisi");
            return;
        }
        if (mPass.length() < 6){
            pesan("Password minimal 6 karakter");
            return;
        }

        mAuth.createUserWithEmailAndPassword(mEmail, mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();
                UserProfileChangeRequest prof = new UserProfileChangeRequest.Builder()
                        .setDisplayName(mName)
                        .build();
                if (user != null){
                    user.updateProfile(prof).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(RegistAcc.this, MainActivity.class));
                            finish();
                        }
                    });
                }
            }
        });
    }


    private void pesan(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}