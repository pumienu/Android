package com.example.test_giua_ki.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_giua_ki.MainActivity;
import com.example.test_giua_ki.R;
import com.example.test_giua_ki.RegisterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartPaymentActivity extends AppCompatActivity {
    Button btnPay;
    TextView textViewTotalAmount;
    FloatingActionButton btnRe;

    EditText usname, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_payment);

        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);

        float totalAmount = getIntent().getFloatExtra("totalAmount", 0.0f);

        // Display total amount
        textViewTotalAmount.setText(getString(R.string.total_amount, totalAmount));

        usname = findViewById(R.id.usname);
        email = findViewById(R.id.email);
        btnPay = findViewById(R.id.buttonPayNow);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, mail;
                user = usname.getText().toString();
                mail = email.getText().toString();
                if (user.isEmpty() || mail.isEmpty()) {
                    Toast.makeText(CartPaymentActivity.this, "Please enter required field", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(CartPaymentActivity.this, CartActivity.class);
                    startActivity(intent);
                    Toast.makeText(CartPaymentActivity.this, "Payment processed successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnRe = findViewById(R.id.btnRe);
        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartPaymentActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }
}
