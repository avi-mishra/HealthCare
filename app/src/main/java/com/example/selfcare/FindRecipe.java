package com.example.selfcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FindRecipe extends AppCompatActivity {
RecipeAdapter recipeAdapter;
ArrayList<Recipe> recipeArrayList;
    ProgressBar pgbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipe);
        EditText ingredients=findViewById(R.id.ingredients);
        AutoCompleteTextView diet=findViewById(R.id.diet);
        AutoCompleteTextView cuisineType=findViewById(R.id.cuisineType);
        AutoCompleteTextView mealType=findViewById(R.id.mealType);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Button find=findViewById(R.id.find);
        pgbar=findViewById(R.id.pgbar);



        String[] dietArray=getResources().getStringArray(R.array.diet);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,dietArray);
        diet.setAdapter(adapter);

        String[] cuisineTypeArray=getResources().getStringArray(R.array.cuisineType);
        ArrayAdapter<String> adapter1=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,cuisineTypeArray);
        cuisineType.setAdapter(adapter1);


        String[] mealTypeArray=getResources().getStringArray(R.array.mealType);
        ArrayAdapter<String> adapter2=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,mealTypeArray);
        mealType.setAdapter(adapter2);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeArrayList=new ArrayList<>();
        recipeAdapter=new RecipeAdapter(this,recipeArrayList);
        recyclerView.setAdapter(recipeAdapter);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeArrayList.clear();
                pgbar.setVisibility(View.VISIBLE);
                if(ingredients.getText().toString().length()>0)
                getRecipeList(ingredients.getText().toString(),diet.getText().toString(),cuisineType.getText().toString(),mealType.getText().toString());
                else {
                    Toast.makeText(FindRecipe.this, "Please add an ingredient", Toast.LENGTH_SHORT).show();
                    pgbar.setVisibility(View.INVISIBLE);

                }
            }
        });

    }
 public  void getRecipeList(String ing,String die,String cuisine,String meal){
     String url = "https://api.edamam.com/api/recipes/v2?type=public";
//     https://api.edamam.com/api/recipes/v2?type=public&q=chicken%20&app_id=d59a5430&app_key=fb2660bbef28f1095bee3333041e5d73&diet=high-protein&cuisineType=Asian&mealType=Breakfast&imageSize=REGULAR
// &q=chicken%20&app_id=d59a5430&app_key=fb2660bbef28f1095bee3333041e5d73&diet=high-protein&cuisineType=Asian&mealType=Breakfast&imageSize=REGULAR
     final RequestQueue requestQueue = Volley.newRequestQueue(this);
url=url+"&q="+ing;
url=url+"%20&app_id=d59a5430&app_key=fb2660bbef28f1095bee3333041e5d73";
if(!die.equals(""))
    url=url+"&qdiet="+die;
if(!cuisine.equals(""))
    url=url+"&cuisineType="+cuisine;
if(!meal.equals(""))
    url=url+"&mealType="+meal;
url=url+"&imageSize=REGULAR";

//url="https://api.edamam.com/api/recipes/v2?type=public&q=chicken%20&app_id=d59a5430&app_key=fb2660bbef28f1095bee3333041e5d73&diet=high-protein&cuisineType=Asian&mealType=Breakfast&imageSize=REGULAR";


        JsonObjectRequest
             jsonObjectRequest
             = new JsonObjectRequest(
             Request.Method.GET,
             url,
             null,
             new Response.Listener() {
                 @Override
                 public void onResponse(Object response) {
                     JSONObject jo = (JSONObject) response;
                     try {
                         JSONArray ja = jo.getJSONArray("hits");
                         Log.i("bitch",""+jo);

                         JSONObject jo2= (JSONObject) ja.get(0);

                         JSONObject jo3=jo2.getJSONObject("recipe");
                         String image=jo3.getString("image");
                         JSONArray ja2=jo3.getJSONArray("ingredientLines");
                         String ingredient=ja2.getString(0);
                         for(int i=1;i<ja2.length();i++)
                         {
                             ingredient=ingredient+"\n"+ja2.getString(i);
                         }
                         String label=jo3.getString("label");
                         Recipe recipe=new Recipe(image,label,ingredient);
                         recipeArrayList.add(recipe);
                         recipeAdapter.notifyDataSetChanged();
                         pgbar.setVisibility(View.INVISIBLE);

                         Log.i("bitch1",""+image);
                         Log.i("bitch2",""+ingredient);

                     } catch (JSONException e) {
                         e.printStackTrace();
                     }

                 }
             },
             new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error)
                 {
                 }
             });
     requestQueue.add(jsonObjectRequest);


    }


}