package com.example.test_giua_ki.Dress.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_giua_ki.Cart.CartActivity;
import com.example.test_giua_ki.Cart.Model.Cart;
import com.example.test_giua_ki.Dress.Model.Dress;
import com.example.test_giua_ki.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DressAdapter extends RecyclerView.Adapter<DressAdapter.ViewHolder> {

    private ArrayList<Dress> dresses ;
    public Cart cart = new Cart();

    private Context mContext;

    public DressAdapter(Context context, ArrayList<Dress> dresses) {
        this.mContext = context;
        this.dresses = dresses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_dress, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  Dress p = dresses.get(position);
        String sDressName = p.getName();
        holder.txtDressName.setText(sDressName);
        holder.txtPrice.setText(""+p.getPrice());
        holder.idIVSSImage.setImageURI(p.getImage());
        holder.txtDesc.setText(p.getDesc());


        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonClick(view, p);
            }
        });

    }

    private void addButtonClick(View view, Dress p) {
        cart.addCart(p);
        showSnackbar(view, mContext.getString(R.string.add_dress) + p.getName(), Snackbar.LENGTH_SHORT);
    }

    @Override
    public int getItemCount() {
        return dresses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtDressName;
        public TextView txtPrice;
        public TextView txtDesc;
        public ConstraintLayout relativeLayout;

        public ImageView ivAdd;
        public ImageView idIVSSImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtDressName = (TextView)itemView.findViewById(R.id.idTVName);
            this.txtPrice = (TextView)itemView.findViewById(R.id.idTVPrice);
            this.ivAdd = (ImageView)itemView.findViewById(R.id.ivAdd);
            this.txtDesc = (TextView)itemView.findViewById(R.id.idTVDesc);
            this.idIVSSImage = (ImageView)itemView.findViewById(R.id.idIVSSImage);
            this.relativeLayout = (ConstraintLayout) itemView.findViewById(R.id.relativelayout);

        }
    }

    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).setAction("Cart", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CartActivity.class);
                mContext.startActivity(intent);
            }
        }).show();

    }

}
