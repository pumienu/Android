package com.example.test_giua_ki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername, edPwd;
    Button btnLogin, btnGoRegister;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsername = findViewById(R.id.edUsername);
        edPwd = findViewById(R.id.edPwd);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);
        dbHelper = new DBHelper(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pwd;
                user = edUsername.getText().toString();
                pwd = edPwd.getText().toString();
                boolean isLogged = dbHelper.checkUser(user, pwd);
                if (isLogged) {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_LONG).show();
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "User/password is incorrect or account doesnt exist", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}