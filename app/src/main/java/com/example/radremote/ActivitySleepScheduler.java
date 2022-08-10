package com.example.radremote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivitySleepScheduler extends AppCompatActivity {
    ConsumerIrManager irblaster;
    LinearLayout layoutListHours;
    LinearLayout layoutListMins;
    ScrollView scrollViewHours;
    ScrollView scrollViewMins;
    ImageView btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleepscheduler);

        scrollViewHours = findViewById(R.id.scrollview_hours);
        scrollViewMins = findViewById(R.id.scrollview_mins);

        irblaster = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);

        layoutListHours = findViewById(R.id.layout_hours);
        layoutListMins = findViewById(R.id.layout_mins);

        preloadHours();
        /*btnHome = findViewById(R.id.btn_Home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivitySleepScheduler.this, MainActivity.class));
            }
        });*/
        btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(ActivitySleepScheduler.this, ActivityHome.class));
                } catch (Exception e){
                    Toast.makeText(ActivitySleepScheduler.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void preloadHours() {
        addView("--",layoutListHours);
        for (int i = 0; i < 24; i++) {
            addView(StringUtils.leftPad(String.valueOf(i),2,'0'),layoutListHours);
        }
        addView("--",layoutListHours);

        addView("--",layoutListMins);
        for (int i = 0; i < 60; i++) {
            addView(StringUtils.leftPad(String.valueOf(i),2,'0'),layoutListMins);
        }
        addView("--",layoutListMins);

    }

    private void addView(String number, LinearLayout layout) {
        View controlView = getLayoutInflater().inflate(R.layout.row_add_timevalues, null, false);

        TextView tvHours = (TextView) controlView.findViewById(R.id.tv_row_timevalues);
        tvHours.setText(number);

        layout.addView(controlView);
    }
}
