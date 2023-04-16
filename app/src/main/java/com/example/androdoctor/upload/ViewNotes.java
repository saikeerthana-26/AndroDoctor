package com.example.androdoctor.upload;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androdoctor.MainActivity;
import com.example.androdoctor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewNotes extends AppCompatActivity {


    RecyclerView listView;
    ProgressDialog progressDialog;
    ArrayList link=new ArrayList();
    ArrayList title=new ArrayList();
    NPdfAdapter pdfAdapter;
    private static final String URL="https://wizzie.online/Diabetic/getdoc.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        listView=findViewById(R.id.rec);
        progressDialog = new ProgressDialog(this);

        getData();

    }
    private void getData() {

        final ProgressDialog pPd = new ProgressDialog(ViewNotes.this);
        pPd.setMessage("Loading");
        pPd.setCancelable(false);
        pPd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                        pPd.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0) {
                                Toast.makeText(ViewNotes.this, "No Data Found", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        link.add(jsonObject.getString("url").toString().trim());
                                        title.add(jsonObject.getString("title").toString().trim());

                                    }
                                    // Toast.makeText(ViewManual.this, ""+link, Toast.LENGTH_SHORT).show();

                                    pdfAdapter = new NPdfAdapter(ViewNotes.this,link,title);
                                    listView.setLayoutManager(new LinearLayoutManager(ViewNotes.this, LinearLayoutManager.VERTICAL,false));
                                    listView.setAdapter(pdfAdapter);
                                    listView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                                            DividerItemDecoration.HORIZONTAL));

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
                params.put("i", MainActivity.uid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }
}