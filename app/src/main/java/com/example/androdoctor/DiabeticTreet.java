package com.example.androdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.androdoctor.databinding.ActivityDiabeticTreetBinding;

public class DiabeticTreet extends AppCompatActivity {

    ActivityDiabeticTreetBinding activityDiabeticTreetBinding;

    int data;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDiabeticTreetBinding= ActivityDiabeticTreetBinding.inflate(getLayoutInflater());
        View view=activityDiabeticTreetBinding.getRoot();
        setContentView(view);

        //Toast.makeText(this, ""+getIntent().getStringExtra("data"), Toast.LENGTH_SHORT).show();

        data= Integer.parseInt(getIntent().getStringExtra("data"));
        if(data==0){
           result="No Diabetic";
            activityDiabeticTreetBinding.dres.setText("No Diabetic");
        }
        else {
            result="Diabetic";
            activityDiabeticTreetBinding.dres.setText("Diabetic");
        }


        activityDiabeticTreetBinding.diab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Diabet.class));
            }
        });

        activityDiabeticTreetBinding.pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Prediabetics.class));
            }
        });

    }
}