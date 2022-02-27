package com.example.selfcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NutritionAdapter extends RecyclerView.Adapter<NutritionAdapter.ViewHolder> {
    ArrayList<Nutrition> nutritionArrayList;
    Context context;
    public NutritionAdapter(Context context,ArrayList<Nutrition> nutritionArrayList)
    {
        this.context=context;
        this.nutritionArrayList=nutritionArrayList;
    }

    @NonNull
    @Override
    public NutritionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.nutrition_item, parent, false);
        return new NutritionAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NutritionAdapter.ViewHolder holder, int position) {
Nutrition nutrition= nutritionArrayList.get(position);
holder.textView1.setText(nutrition.getLabel());
holder.textView2.setText(nutrition.getQuantity()+nutrition.getUnit());
    }

    @Override
    public int getItemCount() {
        return nutritionArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1,textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.textView1);
            textView2=itemView.findViewById(R.id.textView2);
        }
    }
}
