package com.example.androdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androdoctor.databinding.ActivityDiabeticBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Diabetic extends AppCompatActivity {

    ActivityDiabeticBinding activityDiabeticBinding;
    static String Pregnancies,glucose,bp,skin,insulin,bmi,pedigree,age;
    private static final String URL="http://13.235.9.81:5000/diabetic";
    static String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDiabeticBinding=ActivityDiabeticBinding.inflate(getLayoutInflater());
        View view = activityDiabeticBinding.getRoot();
        setContentView(view);

        activityDiabeticBinding.predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(activityDiabeticBinding.Pregnancies.getText().toString().isEmpty()){
                    Snackbar.make(Diabetic.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Data", Snackbar.LENGTH_SHORT).show();

                }
               else if(activityDiabeticBinding.glucose.getText().toString().isEmpty()){
                    Snackbar.make(Diabetic.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Data", Snackbar.LENGTH_SHORT).show();

               }
                else if(activityDiabeticBinding.bp.getText().toString().isEmpty()){
                    Snackbar.make(Diabetic.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Data", Snackbar.LENGTH_SHORT).show();

                }
                else if(activityDiabeticBinding.skin.getText().toString().isEmpty()){
                    Snackbar.make(Diabetic.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Data", Snackbar.LENGTH_SHORT).show();

                }
                else if(activityDiabeticBinding.insulin.getText().toString().isEmpty()){
                    Snackbar.make(Diabetic.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Data", Snackbar.LENGTH_SHORT).show();

                }
                else if(activityDiabeticBinding.bmi.getText().toString().isEmpty()){
                    Snackbar.make(Diabetic.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Data", Snackbar.LENGTH_SHORT).show();

                }
                else if(activityDiabeticBinding.pedigree.getText().toString().isEmpty()){
                    Snackbar.make(Diabetic.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Data", Snackbar.LENGTH_SHORT).show();

                }
                else if(activityDiabeticBinding.age.getText().toString().isEmpty()){
                    Snackbar.make(Diabetic.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Data", Snackbar.LENGTH_SHORT).show();

                }
                else {

                    Pregnancies=activityDiabeticBinding.Pregnancies.getText().toString();
                    glucose=activityDiabeticBinding.glucose.getText().toString();
                    bp=activityDiabeticBinding.bp.getText().toString();
                    skin=activityDiabeticBinding.skin.getText().toString();
                    insulin=activityDiabeticBinding.insulin.getText().toString();
                    bmi=activityDiabeticBinding.bmi.getText().toString();
                    age=activityDiabeticBinding.age.getText().toString();
                    pedigree=activityDiabeticBinding.pedigree.getText().toString();


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        data = jsonObject.getString("disease");
                                        //startActivity(new Intent(getApplicationContext(),DiabeticTreet.class));

                                        Intent intent=new Intent(getApplicationContext(),DiabeticTreet.class);
                                        intent.putExtra("data",data);
                                        startActivity(intent);

                                            } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Diabetic.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String,String>();

                            params.put("Pregnancies",Pregnancies);
                            params.put("glucose",glucose);
                            params.put("bp",bp);
                            params.put("skin",skin);
                            params.put("insulin",insulin);
                            params.put("bmi",bmi);
                            params.put("pedigree",pedigree);
                            params.put("age",age);
                            return params;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(Diabetic.this);
                    queue.add(stringRequest);
                }
            }
        });

    }
}