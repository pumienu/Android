package com.example.test_giua_ki.Cart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_giua_ki.Cart.CartActivity;
import com.example.test_giua_ki.Cart.Model.Cart;
import com.example.test_giua_ki.Dress.Model.Dress;
import com.example.test_giua_ki.R;

import java.util.ArrayList;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context mContext;
//    private ArrayList<Dress> mDresses;

    public CartAdapter(Context context) {
        this.mContext = context;
//        this.mDresses = dresses;
//        , ArrayList<Dress> dresses
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = Cart.getInstance();
        Map<Integer, Integer> cartList = cart.getCartList(); // Get cartList from Cart
        final Integer pid = cart.getDressByOrder(position).getId();
        final Dress p = Cart.getDressRepository().getDress(pid);

        String sProductName = p.getName();
        Integer amount = cartList.get(pid); // Use cartList obtained from Cart
        holder.txtPhoneName.setText(sProductName);
        holder.txtPrice.setText(String.valueOf(p.getPrice()));
        holder.idIVSSImage.setImageURI(p.getImage());
        holder.edAmount.setText(String.valueOf(amount));
        holder.tvLineTotal.setText(String.valueOf(cart.getLinePrice(p)));

        holder.tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addCart(p);
                Integer amount = cartList.get(pid);
                holder.edAmount.setText(String.valueOf(amount));
                holder.tvLineTotal.setText(String.valueOf(cart.getLinePrice(p)));
                ((CartActivity)mContext).updateData();
            }
        });
        holder.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.removeCart(p);
                Integer amount = cartList.get(pid);
                holder.edAmount.setText(String.valueOf(amount));
                holder.tvLineTotal.setText(String.valueOf(cart.getLinePrice(p)));
                ((CartActivity)mContext).updateData();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Cart.getInstance().getCartList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtPhoneName;
        public TextView txtPrice;
        public TextView tvLineTotal;
        public EditText edAmount;
        public TextView tvPlus;
        public TextView tvMinus;
        public LinearLayoutCompat relativeLayout;
        public ImageView idIVSSImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtPhoneName = itemView.findViewById(R.id.idTVName);
            this.txtPrice = itemView.findViewById(R.id.idTVPrice);
            this.idIVSSImage = itemView.findViewById(R.id.idIVSSImage);
            this.relativeLayout = itemView.findViewById(R.id.llLayout);
            this.edAmount = itemView.findViewById(R.id.amount);
            this.tvLineTotal = itemView.findViewById(R.id.tvLineTotal);
            this.tvPlus = itemView.findViewById(R.id.tvplus);
            this.tvMinus = itemView.findViewById(R.id.tvminus);
        }
    }
}
