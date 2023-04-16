package com.example.androdoctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView register;
    EditText username,password;
    TextView forgot_password;
    Button login;

    private static final String BASE_URL="https://wizzie.online/Diabetic/login.php";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Phone = "phoneKey";
    public static final String id = "idKey";
    public static final String name = "name";
    static String mobile,cust_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Phone)) {

            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            i.putExtra("mob",sharedpreferences.getString(Phone, ""));
            i.putExtra("id",sharedpreferences.getString(id, ""));
            i.putExtra("name",sharedpreferences.getString(name, ""));
            startActivity(i);
            finish();
        }

        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().isEmpty()) {
                    Snackbar.make(Login.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Name", Snackbar.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().trim().isEmpty()) {
                    Snackbar.make(Login.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Password", Snackbar.LENGTH_SHORT).show();
                }
                else if(username.getText().toString().trim().equalsIgnoreCase("Admin")&&(password.getText().toString().trim().equalsIgnoreCase("Admin"))){
                    startActivity(new Intent(getApplicationContext(),AdminHome.class));
                    finish();
                }
                else {
                    function();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Signup.class));
               //finish();
            }
        });

    }

    private void function() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray=new JSONArray(response);
                   // Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                    if(jsonArray.length()==0){
                        Snackbar.make(Login.this.getWindow().getDecorView().findViewById(android.R.id.content), "Invalid Username Or Password ", Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            for (int j=0;j<jsonArray.length();j++){
                                JSONObject jsonObject=jsonArray.getJSONObject(j);

                                mobile=jsonObject.getString("mobile");
                                cust_id=jsonObject.getString("id");
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Phone, mobile.trim());
                                editor.putString(id, cust_id.trim());
                                editor.putString(name, jsonObject.getString("name"));
                                editor.commit();

                                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                i.putExtra("mob",mobile);
                                i.putExtra("id",cust_id);
                                i.putExtra("name",jsonObject.getString("name".trim()));
                                startActivity(i);
                                finish();

                            }

                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Login.this, ""+error.toString(), Toast.LENGTH_LONG).show();

                    }
                })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String, String>();
                params.put("u",username.getText().toString());
                params.put("p",password.getText().toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void init() {
        register=findViewById(R.id.login);
        username=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        forgot_password=findViewById(R.id.forgot_password);
        login=findViewById(R.id.cirLoginButton);
    }
}