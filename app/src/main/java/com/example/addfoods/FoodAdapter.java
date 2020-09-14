package com.example.addfoods;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Food> foodList;

    public FoodAdapter(Context context, ArrayList<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardfood, parent, false);
        return new ViewHolder(v);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView name;
        public TextView price;
        public TextView desc;
        private ImageView img;


        ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.menu_name);
            price = v.findViewById(R.id.menu_harga);
            desc = v.findViewById(R.id.desc);
            img = itemView.findViewById(R.id.img_food);
        }
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Food food = foodList.get(position);
        holder.name.setText(food.getName());
        holder.price.setText("Rp. "+food.getPrice());
        holder.desc.setText(food.getDesc());
        Picasso.get().load(food.getImg()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }


}
