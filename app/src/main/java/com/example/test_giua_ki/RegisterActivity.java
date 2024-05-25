package com.example.test_giua_ki;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    EditText edUsername, edPassword, edName;
    TextView edDate;
    Button btnRegister, btnGoLogin;
    DBHelper dbHelper;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        edDate = (TextView)findViewById(R.id.edDate);
        edName = findViewById(R.id.edName);
        btnRegister = findViewById(R.id.btnRegister);
        btnGoLogin = findViewById(R.id.btnGoLogin);
        dbHelper = new DBHelper(this);
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                edDate.setText(date);
            }
        };
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pwd, created_date, name;
                user = edUsername.getText().toString();
                pwd = edPassword.getText().toString();
                name = edName.getText().toString();
                created_date = edDate.getText().toString();
                if (user.isEmpty() || pwd.isEmpty() || name.isEmpty() || created_date.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter required field", Toast.LENGTH_LONG).show();
                } else {
                    if(dbHelper.checkExists(user)) {
                        Toast.makeText(RegisterActivity.this, "Username already existed", Toast.LENGTH_LONG).show();
                    } else {
                        boolean regSuccess = dbHelper.createUser(user, pwd, name, created_date);
                        if (regSuccess) {
                            Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}