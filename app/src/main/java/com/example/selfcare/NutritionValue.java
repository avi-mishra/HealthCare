package com.example.selfcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NutritionValue extends AppCompatActivity {
NutritionAdapter adapter;
    ProgressBar pgbar;
ArrayList<Nutrition> nutritionArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_value);
        EditText recipe =findViewById(R.id.recipe);
        Button getData=findViewById(R.id.getData);
        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pgbar=findViewById(R.id.pgbar);
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nutritionArrayList=new ArrayList<>();
        adapter=new NutritionAdapter(this,nutritionArrayList);
        recyclerView.setAdapter(adapter);
        LinearLayout llNutrition=findViewById(R.id.llNutrition);
        llNutrition.setVisibility(View.INVISIBLE);
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgbar.setVisibility(View.VISIBLE);
                nutritionArrayList.clear();
                llNutrition.setVisibility(View.VISIBLE);
                String s= recipe.getText().toString();
                getNutritionData(s);

            }
        });

    }
    public void getNutritionData(String s){

        String[] tokens=s.split(",");
        String url = "https://api.edamam.com/api/nutrition-data?app_id=2b9e0617&app_key=efa2a74523b605be452f838d3015415b&nutrition-type=cooking&ingr=";

for(int i=0;i<tokens.length;i++)
{
    String[] atom=tokens[i].split(" ");
    for(int j=0;j<atom.length;j++)
    url=url+atom[j]+"%20";

    url=url+"%20";

}
url=url+"C";

Log.i("apiString",url);
        String url2 = "https://api.edamam.com/api/nutrition-data?app_id=2b9e0617&app_key=efa2a74523b605be452f838d3015415b&nutrition-type=cooking&ingr=1%20cup%20rice%20%2010%20oz%20chickpeas%20%2020%20oz%20chicken%20C";
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

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
                        Log.i("marylove","c "+jo);
                        JSONObject jo2;
                        try {
                            jo2=jo.getJSONObject("totalNutrients");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            jo2 = null;
                        }
                          JSONArray key=jo2.names();
                            for (int i = 0; i < key.length (); ++i) {
                                String keys = null;
                                try {
                                    Log.i("maryLove",""+key.get(i));
                                    keys = key.getString (i);
                                    JSONObject value = jo2.getJSONObject (keys);
                                    Log.i("maryLove2",""+value);
                                    String label=value.getString("label");
                                    String[] labels=label.split(",");
                                    String quantity=value.getString("quantity") ;
                                    if(quantity.length()>9)
                                        quantity=quantity.substring(0,10);

                                    Nutrition nutrition =new Nutrition(labels[0],quantity,value.getString("unit"));
                                    nutritionArrayList.add(nutrition);
                                } catch (JSONException jsonException) {
                                    jsonException.printStackTrace();

                                }
                            }
                            adapter.notifyDataSetChanged();
                            pgbar.setVisibility(View.GONE);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.i("maryLove",""+error);
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}