package com.example.test_giua_ki.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_giua_ki.Cart.Adapter.CartAdapter;
import com.example.test_giua_ki.Cart.Model.Cart;
import com.example.test_giua_ki.DBHelper;
import com.example.test_giua_ki.Dress.Model.Dress;
import com.example.test_giua_ki.MainActivity;
import com.example.test_giua_ki.R;
import com.example.test_giua_ki.databinding.ActivityCartBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvDress;
    private TextView tvTotal;
    Button btnSave, btnPay;
    FloatingActionButton btnRe;
    private Cart cart = new Cart();
    private ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tvTotal = binding.tvTotal;

        rvDress = binding.rvdress;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rvDress.setLayoutManager(mLayoutManager);

        CartAdapter rvAdapter = new CartAdapter(this);
        rvDress.setAdapter(rvAdapter);
        tvTotal.setText("Total: $" + this.cart.getTotalPrice());

        btnSave = findViewById(R.id.btnSaveCart);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper dbHelper = new DBHelper(CartActivity.this);
                    int cart_id = (int)dbHelper.createCart(1);
                    ArrayList<Dress> dresses = cart.getDressesFromCart();
                    for (Dress p : dresses) {
                        dbHelper.insertDressesToCart(cart_id, p.getId());
                    }
                    Toast.makeText(CartActivity.this, "Save cart successfully", Toast.LENGTH_LONG).show();
                }
            });


        btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.getDressesFromCart().size() > 0) {
                    // Start CartPaymentActivity và truyền totalAmount
                    Intent intent = new Intent(CartActivity.this, CartPaymentActivity.class);
                    intent.putExtra("totalAmount", cart.getTotalPrice());
                    startActivity(intent);
                } else {
                    Toast.makeText(CartActivity.this, "The cart is empty. Nothing to pay.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRe = findViewById(R.id.btnRe);
        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateData() {
        tvTotal.setText("Total: $" + this.cart.getTotalPrice());
    }
}
