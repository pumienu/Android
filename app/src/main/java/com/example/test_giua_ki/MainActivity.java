package com.example.test_giua_ki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_giua_ki.Cart.CartActivity;
import com.example.test_giua_ki.Cart.Model.Cart;
import com.example.test_giua_ki.databinding.ActivityMainBinding;
import com.example.test_giua_ki.Dress.Repository.DressRepository;
import com.example.test_giua_ki.Dress.Adapter.DressAdapter;
import com.example.test_giua_ki.Dress.Model.Dress;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    DressRepository dressRepository;
    RecyclerView rvDress;
    FloatingActionButton btnlogout;

    DBHelper dbHelper;
    private Cart cart = new Cart();

    private TextView textCartItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        initData();
        loadProductsFromDatabase();

        rvDress = binding.rvdress;

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rvDress.setLayoutManager(mLayoutManager);

        DressAdapter rvAdapter  = new DressAdapter(this, this.dressRepository.getDressList());
        rvDress.setAdapter(rvAdapter);

        btnlogout = findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
    private void initData() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isDataInitialized = prefs.getBoolean("DataInitialized", false);

        if (!isDataInitialized) {
            DBHelper dbHelper = new DBHelper(this);
            for (int i = 0; i < 100; i++) {
                String name = "Dress Name " + i;
                int resID = getResId("vay" + i % 5 , R.drawable.class);
                Uri imgUri = getUri(resID);
                float price = Float.parseFloat(String.format("%.2f", new Random().nextFloat() * 100));
                String desc = "Desc " + i;
                dbHelper.insertDress(i, name, imgUri.toString(), price, desc);
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("DataInitialized", true);
            editor.apply();
        }
    }


    private void loadProductsFromDatabase() {
        DBHelper dbHelper = new DBHelper(this); // Context needed
        ArrayList<Dress> alProduct = dbHelper.getAllDressesList();
        this.dressRepository = new DressRepository(alProduct);
    }

    public Uri getUri (int resId){
        return Uri.parse("android.resource://"  + this.getPackageName().toString() + "/" + resId);
    }
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnu_cart){
            Log.d("this","cart show here");
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}