package com.example.selfcare;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.selfcare.ml.FoodRecModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;


public class FoodNutrition extends Fragment {
    ImageView image;
    Interpreter interpreter;
    TextView tvFoodName, tvFoodDetails;
    Bitmap img;
    LinearLayout linearLayout;
    RecyclerView recyclerView;
    NutritionAdapter adapter;
    ArrayList<Nutrition> nutritionArrayList;
    ProgressBar pgbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_food_nutrition, container, false);
        image = view.findViewById(R.id.img_food);
        tvFoodName = view.findViewById(R.id.tv_food_name);
        linearLayout=view.findViewById(R.id.llNutrition);
        recyclerView=view.findViewById(R.id.recycler_view);
        nutritionArrayList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new NutritionAdapter(getContext(),nutritionArrayList);
        recyclerView.setAdapter(adapter);
        linearLayout.setVisibility(View.INVISIBLE);
        pgbar=view.findViewById(R.id.pgbar);
        pgbar.setVisibility(View.INVISIBLE);
        selectImage();
        return view;
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            mlFunction(selectedImage,2);
            Picasso.get().load(selectedImage).into(image);
        }
    }



    public void mlFunction (Uri uri, int k){
        try {
            if(k==2)
                img= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

            img=Bitmap.createScaledBitmap(img,250,250,true);

            try {
                FoodRecModel model = FoodRecModel.newInstance(getContext());

                // Creates inputs for reference.
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 250, 250, 3}, DataType.FLOAT32);
                TensorImage tensorImage =new TensorImage(DataType.FLOAT32);
                tensorImage.load(img);
                ByteBuffer byteBuffer=tensorImage.getBuffer();
                inputFeature0.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
                FoodRecModel.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                // Releases model resources if no longer used.
                model.close();
                Log.i("output",""+outputFeature0.getBuffer().toString());

                float[]confidence =outputFeature0.getFloatArray();
                int maxPos=0;
                float maxconfidence=0;
                for(int i=0;i<confidence.length;i++){
                    if(confidence[i]>maxconfidence) {
                        maxconfidence=confidence[i];
                        maxPos=i;
                    }
                }
                FoodCategory fc = new FoodCategory();
                tvFoodName.setText(fc.foodClass[maxPos]);
                getNutritionData(fc.foodClass[maxPos]);

            } catch (IOException e) {
                // TODO Handle the exception
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    public void getNutritionData(String s){

        String[] tokens=s.split("_");
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
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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
                        linearLayout.setVisibility(View.VISIBLE);
                        pgbar.setVisibility(View.INVISIBLE);

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