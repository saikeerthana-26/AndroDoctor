package com.example.androdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Diabet extends AppCompatActivity {

    private static final String URL1="https://wizzie.online/Diabetic/gettablets.php";
    ListView listView;
    ArrayList list=new ArrayList();
    ArrayAdapter adapter;
    ArrayList idd=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabet);
        listView=findViewById(R.id.med);
        getDa();

    }

    private void getDa() {
        list.clear();
        idd.clear();
        final ProgressDialog pPd = new ProgressDialog(Diabet.this);
        pPd.setMessage("Loading");
        pPd.setCancelable(false);
        pPd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                        pPd.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(Diabet.this, "Non Data Found", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        list.add("Name:     " + jsonObject.getString("name") + "\n\n" + "Quantity:      " + jsonObject.getString("quantity") + "\n\n" + "Time:      " + jsonObject.getString("time") + "\n\n" +"****************************************");
                                        idd.add(jsonObject.getString("id"));

                                    }
                                    adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.sol, list);
                                    listView.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}