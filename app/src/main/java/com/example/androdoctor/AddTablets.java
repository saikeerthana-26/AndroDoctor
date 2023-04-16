package com.example.androdoctor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddTablets extends AppCompatActivity {

    EditText tname,qty,time;
    Button button;
    ListView listView;
    ArrayList list=new ArrayList();
    ArrayAdapter adapter;
    ArrayList idd=new ArrayList();

    private static final String URL="https://wizzie.online/Diabetic/addtablets.php";
    private static final String URL1="https://wizzie.online/Diabetic/gettablets.php";
    private static final String URL2="https://wizzie.online/Diabetic/deltab.php";

    Calendar mcurrentTime = Calendar.getInstance();
    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
    int minute = mcurrentTime.get(Calendar.MINUTE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tablets);


        listView=findViewById(R.id.med);

        tname=findViewById(R.id.tname);
        qty=findViewById(R.id.tqty);
        time=findViewById(R.id.ttim);
        button=findViewById(R.id.btnadd);

      //  getab(MainActivity.m);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddTablets.this);
                builder.setMessage("Do you want to Delete Tablets ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                delTab(idd.get(position).toString().trim());


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Alert Dialog Example");
                alert.show();
            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddTablets.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tname.getText().toString().trim().isEmpty()){
                    tname.setError("Enter Tablet");
                }
                else if(qty.getText().toString().trim().isEmpty()){
                    tname.setError("Enter Quantity");
                }
                else if(time.getText().toString().trim().isEmpty()){
                    time.setError("Time");
                }
                else{
                    final ProgressDialog pdd = new ProgressDialog(AddTablets.this);
                    pdd.setMessage("Please Wait, While We Add");
                    pdd.setCancelable(false);
                    pdd.show();
                    // Toast.makeText(Signup.this, ""+etmobile.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    pdd.dismiss();
                                    System.out.println(response);
                                    JSONObject object = null;
                                    try {
                                        object = new JSONObject(response);
                                        if (object.getString("result").equalsIgnoreCase("you are registered successfully")) {
                                            Toast.makeText(AddTablets.this, "Tablet Added Success", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override

                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("nm", tname.getText().toString().trim());
                            params.put("qt", qty.getText().toString().trim());
                            params.put("tm", time.getText().toString().trim());
                           // params.put("mob", MainActivity.m);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                }
            }
        });

    }

    private void delTab(String trim) {
        final ProgressDialog pdd = new ProgressDialog(AddTablets.this);
        pdd.setMessage("Please Wait, While We Add");
        pdd.setCancelable(false);
        pdd.show();
        // Toast.makeText(Signup.this, ""+etmobile.getText().toString().trim(), Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pdd.dismiss();
                        System.out.println(response);
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                            if (object.getString("result").equalsIgnoreCase("you are registered successfully")) {
                                Toast.makeText(AddTablets.this, "Tablet Deleted Success", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override

                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", trim.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void getab(String m) {
      //  Toast.makeText(this, ""+m, Toast.LENGTH_SHORT).show();

        final ProgressDialog pPd = new ProgressDialog(AddTablets.this);
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
                                Toast.makeText(AddTablets.this, "Non Data Found", Toast.LENGTH_LONG).show();
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
               // params.put("id", m.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }
}