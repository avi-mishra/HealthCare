package com.example.selfcare;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    ArrayList<Recipe> recipeArrayList;
    Context context;

    public RecipeAdapter(Context context,ArrayList<Recipe> recipeArrayList){
        this.context=context;
        this.recipeArrayList=recipeArrayList;
    }


    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipeAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
Recipe recipe= recipeArrayList.get(position);
        Picasso.get().load(recipe.getImage()).into(holder.imageView);
        holder.textView.setText(recipe.getRecipe());
        holder.label.setText(recipe.getLabel());
    }
    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,label;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            imageView=itemView.findViewById(R.id.image);
            label=itemView.findViewById(R.id.label);
        }
    }
}
