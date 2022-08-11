package com.example.radremote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    TextView tvTest;
    boolean isScrolling;
    GestureDetector gestureDetector;
    ListView listView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleepscheduler);
        try {


            isScrolling = false;

            tvTest = findViewById(R.id.tvScrollStats);

            scrollViewHours = findViewById(R.id.scrollview_hours);
            scrollViewMins = findViewById(R.id.scrollview_mins);
            listView = findViewById(R.id.listView);
            /*

            gestureDetector = new GestureDetector(this, new MyGestureListener());
            scrollViewHours.setOnTouchListener(touchlistener);
            */

            irblaster = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);

            layoutListHours = findViewById(R.id.layout_hours);
            layoutListMins = findViewById(R.id.layout_mins);

            preloadHours();
            btnHome = findViewById(R.id.btn_home);
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(new Intent(ActivitySleepScheduler.this, ActivityHome.class));
                    } catch (Exception e) {
                        Toast.makeText(ActivitySleepScheduler.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                scrollViewHours.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                        float y = scrollViewHours.getScrollY();
                        int newy = (int) (y - (y % 180));
                        //tvTest.setText(String.valueOf(i1) + " " + String.valueOf(newy) );
                        //scrollViewHours.smoothScrollTo(0, newy);

                    }
                });

            }
//            scrollViewHours.setContextClickable(this, new GestureDetector.SimpleOnGestureListener());
 /*           scrollViewHours.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        tvTest.setText("DOWN");
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                        //Toast.makeText(ActivitySleepScheduler.this, "UP", Toast.LENGTH_SHORT).show();
                        //if (tvTest.getText()=="DOWN"){
                            tvTest.setText("MOVE");
                          //  scrollViewHours.smoothScrollTo(0,0);
                        //}
                    } else {
                        if (tvTest.getText().equals("MOVE")){
                            Toast.makeText(ActivitySleepScheduler.this, "DEEP", Toast.LENGTH_SHORT).show();
                            float y = scrollViewHours.getScrollY();
                            int newy = (int) (y - (y % 180));

                            scrollViewHours.setScrollY(newy);
                        }

                        if (tvTest.getText().equals("HOVERMOVE")){
                            Toast.makeText(ActivitySleepScheduler.this, "DEEP", Toast.LENGTH_SHORT).show();
                            scrollViewHours.setScrollY(0);
                        }
                        //scrollViewHours.setScrollY(0);
                        tvTest.setText("---");
                    }
                    return false;
                }
            });*/


        } catch (Exception e) {
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }

        String[] array = new String[60];
        for (int i=0; i<60; i++){
            array[i] = StringUtils.leftPad(String.valueOf(i),2,"0");
        };
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, array);
        listView.setAdapter(arr);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollstate) {
                switch (scrollstate) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        tvTest.setText("idle");
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        tvTest.setText("fling");
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        tvTest.setText("touch scroll");
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

    }

/*

    View.OnTouchListener touchlistener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (gestureDetector.onTouchEvent(motionEvent) == false) {

            } else {
                float y = scrollViewHours.getScrollY();
                int newy = (int) (y - (y % 180));
                scrollViewHours.smoothScrollTo(0, newy);
            }
            ;
            return false;
        }
    };
*/
/*
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            //Toast.makeText(ActivitySleepScheduler.this, "onScroll", Toast.LENGTH_SHORT).show();
            tvTest.setText("onScroll");
            return false;
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            Toast.makeText(ActivitySleepScheduler.this, "onFling", Toast.LENGTH_SHORT).show();
            tvTest.setText("onFling");
            return false;
        }
    }*/

    private void preloadHours() {
        addView("--", layoutListHours);
        for (int i = 0; i < 24; i++) {
            addView(StringUtils.leftPad(String.valueOf(i), 2, '0'), layoutListHours);
        }
        addView("--", layoutListHours);

        addView("--", layoutListMins);
        for (int i = 0; i < 60; i++) {
            addView(StringUtils.leftPad(String.valueOf(i), 2, '0'), layoutListMins);
        }
        addView("--", layoutListMins);

    }

    private void addView(String number, LinearLayout layout) {
        View controlView = getLayoutInflater().inflate(R.layout.row_add_timevalues, null, false);

        TextView tvHours = (TextView) controlView.findViewById(R.id.tv_row_timevalues);
        tvHours.setText(number);

        layout.addView(controlView);
    }
}
