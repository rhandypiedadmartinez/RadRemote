package com.example.radremote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutList;
    Button btnAdd;
    List<String> teamList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        layoutList = findViewById(R.id.layout_list);
        btnAdd = findViewById(R.id.btn_add_ctrl);
        btnAdd.setOnClickListener(this);
        teamList.add("IR CODE");
        teamList.add("00FF 00FF");
        teamList.add("01FE 00FF");
    }

    @Override
    public void onClick(View view){
        addView();
    }

    private void addView() {
        View controlView = getLayoutInflater().inflate(R.layout.row_add_control, null, false);

        EditText etControl = (EditText) controlView.findViewById(R.id.btn_edit_ctrl);
        AppCompatSpinner spinnerTeam = (AppCompatSpinner) controlView.findViewById(R.id.spinner);
        ImageView imgClose = (ImageView) controlView.findViewById(R.id.img_remove);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, teamList);
        spinnerTeam.setAdapter(arrayAdapter);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(controlView);
            }
        });

        layoutList.addView(controlView);
    }

    private void removeView(View view){
        layoutList.removeView(view);
    }
}