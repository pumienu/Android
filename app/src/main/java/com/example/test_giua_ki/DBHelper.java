package com.example.test_giua_ki;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.example.test_giua_ki.Cart.Model.Cart;
import com.example.test_giua_ki.Dress.Model.Dress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "test_gk.db";

    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, 6);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(username varchar(20), password varchar(20), name nvarchar(20), created_time date)");
        db.execSQL("CREATE TABLE dresses(id INTEGER PRIMARY KEY, name TEXT, imageUri TEXT, price REAL, description TEXT)");
        db.execSQL("CREATE TABLE carts(id INTEGER PRIMARY KEY AUTOINCREMENT, staff_id INTEGER)");
        db.execSQL("CREATE TABLE cart_dress(cart_id INTEGER, dress_id INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS dresses");
        db.execSQL("DROP TABLE IF EXISTS carts");
        db.execSQL("DROP TABLE IF EXISTS cart_dress");
        onCreate(db);
    }

    public boolean createUser(String username, String password, String name, String created_time) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("name", name);
        created_time = convertDateToSQLiteFormat(created_time);
        contentValues.put("created_time", created_time);
        long result = myDB.insert("users", null, contentValues);
        return result != -1;
    }

    public boolean checkExists(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE username = ?", new String[] {username});
        return cursor.getCount() > 0;
    }
    public boolean checkUser(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[] {username, password});
        return cursor.getCount() > 0;
    }

    public String convertDateToSQLiteFormat(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = sdf.parse(dateStr);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean insertDress(int id, String name, String imageUri, float price, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("imageUri", imageUri);
        contentValues.put("price", price);
        contentValues.put("description", desc);
        long result = db.insert("dresses", null, contentValues);
        return result != -1;
    }

    public ArrayList<Dress> getAllDressesList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM dresses", null);

        ArrayList<Dress> dressList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String imageUri = cursor.getString(cursor.getColumnIndex("imageUri"));
                float price = cursor.getFloat(cursor.getColumnIndex("price"));
                String desc = cursor.getString(cursor.getColumnIndex("description"));

                Dress dress = new Dress(id, name);
                dress.setImage(Uri.parse(imageUri));
                dress.setPrice(price);
                dress.setDesc(desc);

                dressList.add(dress);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return dressList;
    }

    public long createCart(int staff_id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("staff_id", staff_id);
        long newCartId = myDB.insert("carts", null, contentValues);
        return newCartId;
    }

    public Cart getFirstCartByStaffId(int staff_id) {
            SQLiteDatabase db = this.getReadableDatabase(); //chi doc du lieu
            Cursor cursor = db.rawQuery("SELECT * FROM carts WHERE staff_id = ? LIMIT 1", new String[]{String.valueOf(staff_id)});

            Cart cart = null;
            // kiem tra xem da co gio hang nao chua
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                cart = new Cart();
                cart.setId(id);
            }
            // create cart if not exists
            if (cart == null) {
                long newCartId = createCart(staff_id);
                cart = new Cart();
                cart.setId((int) newCartId);
            }

            cursor.close();
            // truy van lay thong tin
            Cursor itemCursor = db.rawQuery("SELECT cart_dress.dress_id, COUNT(cart_dress.dress_id) as quantity " +
                "FROM carts " +
                "INNER JOIN cart_dress ON carts.id = cart_dress.cart_id " +
                "WHERE carts.staff_id = ? " +
                "GROUP BY cart_dress.dress_id", new String[]{String.valueOf(staff_id)});

            if (itemCursor.moveToFirst()) {
                do {
                    int dress_id = itemCursor.getInt(itemCursor.getColumnIndex("dress_id"));
                    int quantity = itemCursor.getInt(itemCursor.getColumnIndex("quantity"));
                    cart.setCartItem(dress_id, quantity);
                } while (itemCursor.moveToNext());
            }
            itemCursor.close();


            return cart;
        }


    public boolean insertDressesToCart(int cart_id, int dress_id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cart_id", cart_id);
        contentValues.put("dress_id", dress_id);
        long result = myDB.insert("cart_dress", null, contentValues);
        return result != -1;
    }

    public boolean removeAllCarts() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("DELETE FROM cart_dress");
        int result = myDB.delete("carts", null, null);
        return result > 0;
    }





}
