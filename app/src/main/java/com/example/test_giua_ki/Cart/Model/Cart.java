package com.example.test_giua_ki.Cart.Model;

import com.example.test_giua_ki.Dress.Model.Dress;
import com.example.test_giua_ki.Dress.Repository.DressRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int id;
    private int staff_id;
    private static Map<Integer, Integer> cartList = new HashMap<>();
    private static float totalPrice;
    private static DressRepository dressRepository = new DressRepository();
    private static Cart instance;

    public Cart(int id, int staff_id) {
        this.id = id;
        this.staff_id = staff_id;
    }

    public Cart() {
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public static DressRepository getDressRepository() {
        return dressRepository;
    }

    public Map<Integer, Integer> getCartList() {
        return cartList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public static float getTotalPrice() {
        return totalPrice;
    }

    public static void setTotalPrice(float totalPrice) {
        Cart.totalPrice = totalPrice;
    }

    public void addCart(Dress dress) {
        Integer quantity = cartList.getOrDefault(dress.getId(), 0);
        if (quantity >= 10) return;
        cartList.put(dress.getId(), quantity + 1);
        totalPrice += dress.getPrice();
    }

    public Dress getDressByOrder(Integer pos) {
        Object[] keys = cartList.keySet().toArray();
        return dressRepository.getDress(Integer.parseInt(keys[pos].toString()));
    }

    public static float getLinePrice(Dress p) {
        return p.getPrice() * cartList.getOrDefault(p.getId(), 0);
    }

    public static float getLinePrice(Integer pid) {
        Dress p = new DressRepository().getDress(pid);
        return cartList.get(pid) * p.getPrice();
    }

    public void removeCart(Dress p) {
        Integer quantity = cartList.getOrDefault(p.getId(), 0);
        if (quantity <= 0) return;
        cartList.put(p.getId(), quantity - 1);
        totalPrice -= p.getPrice();
    }

    public void setCartItem(Integer dressId, Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (quantity == 0) {
            cartList.remove(dressId);
        } else {
            cartList.put(dressId, quantity);
        }
        recalculateTotalPrice();
    }

    private void recalculateTotalPrice() {
        totalPrice = 0;
        for (Map.Entry<Integer, Integer> entry : cartList.entrySet()) {
            Dress dress = dressRepository.getDress(entry.getKey());
            totalPrice += dress.getPrice() * entry.getValue();
        }
    }

    public ArrayList<Dress> getDressesFromCart() {
        ArrayList<Dress> dresses = new ArrayList<>();
        for (Integer dressId : cartList.keySet()) {
            Dress dress = dressRepository.getDress(dressId);
            if (dress != null) {
                dresses.add(dress);
            }
        }
        return dresses;
    }
    public void clearCart() {
        cartList.clear();
        totalPrice = 0;
    }
}