package com.example.test_giua_ki.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_giua_ki.MainActivity;
import com.example.test_giua_ki.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartPaymentActivity extends AppCompatActivity {
    Button btnPay;
    TextView textViewTotalAmount;
    FloatingActionButton btnRe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_payment);

        // Initialize views
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);

        // Get total amount from intent
        float totalAmount = getIntent().getFloatExtra("totalAmount", 0.0f);

        // Display total amount
        textViewTotalAmount.setText(getString(R.string.total_amount, totalAmount));

        btnPay = findViewById(R.id.buttonPayNow);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartPaymentActivity.this, CartActivity.class);
                startActivity(intent);
                Toast.makeText(CartPaymentActivity.this, "Payment processed successfully", Toast.LENGTH_LONG).show();
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
