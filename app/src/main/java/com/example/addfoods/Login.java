package com.example.addfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

public class Login extends AppCompatActivity {
    EditText email, pass;
    private FirebaseAuth mAuth;
    String Email,Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(Login.this);

        email = findViewById(R.id.et_email);
        pass = findViewById(R.id.editText_email);


        TextView regist = findViewById(R.id.textView3);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registAkun();
            }
        });
    }

    private void registAkun(){
        startActivity(new Intent(Login.this, RegistAcc.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseInstanceId.getInstance().getToken();
    }

    public void addMenu(View view) {
        String eemail = email.getText().toString();
        final String epass = pass.getText().toString();

        if (TextUtils.isEmpty(eemail)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(epass)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(eemail, epass)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (epass.length() < 6) {
                                pass.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(Login.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            FirebaseInstanceId.getInstance().getToken();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
