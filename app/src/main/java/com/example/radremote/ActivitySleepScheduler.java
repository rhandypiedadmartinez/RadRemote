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
    //ConsumerIrManager irblaster;
    LinearLayout layoutListHours;
    LinearLayout layoutListMins;
    ScrollView scrollViewHours;
    ScrollView scrollViewMins;
    ImageView btnHome;
    TextView tvTest;
    TextView tvTest2;
    boolean isScrolling;
    GestureDetector gestureDetector;
    ListView listView;
    ListView listView2;

    Runnable scrollerTask;

    int count;
    int x1;
    int x2;
    int x3;
    int x4;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleepscheduler);
        try {

            isScrolling = false;

            tvTest = findViewById(R.id.tvScrollStats);
            tvTest2 = findViewById(R.id.tvScrollStats2);

            scrollViewHours = findViewById(R.id.scrollview_hours);
            scrollViewMins = findViewById(R.id.scrollview_mins);
            listView = findViewById(R.id.listViewHours);
            listView2 = findViewById(R.id.listViewMins);

            gestureDetector = new GestureDetector(this, new MyGestureListener());
            scrollViewHours.setOnTouchListener(touchlistener);

            //irblaster = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);

            layoutListHours = findViewById(R.id.layout_hours);
            layoutListMins = findViewById(R.id.layout_mins);


            //  preloadHours();
            btnHome = findViewById(R.id.btn_home);
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(new Intent(ActivitySleepScheduler.this, ActivityHome.class));

                        //listView.smoothScrollToPosition(20);
                    } catch (Exception e) {
                        Toast.makeText(ActivitySleepScheduler.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                }
            });

/*
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                scrollViewHours.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                        float y = scrollViewHours.getScrollY();
                        int newy = (int) (y - (y % 180));
                        scrollViewMins.smoothScrollTo(0,(int) y);
                        //scrollViewMins.smoothScrollTo(0,newy);
                        //tvTest.setText(String.valueOf(i1) + " " + String.valueOf(newy) );
                        //scrollViewHours.smoothScrollTo(0, newy);

                    }
                });
            }
            */


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
                        float y = scrollViewHours.getScrollY();
                        int newy = (int) (y - (y % 180));
                        scrollViewMins.smoothScrollTo(0,newy);

                        *//*if (tvTest.getText().equals("MOVE")){
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
                        tvTest.setText("---");*//*
                    }
                    return false;
                }
            });
*/

        } catch (Exception e) {
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }

        String[] array2 = new String[26];
        array2[0] = "--";

        for (int i = 1; i < 25; i++) {
            array2[i] = StringUtils.leftPad(String.valueOf(i - 1), 2, "0");
        }
        ;

        array2[25] = "--";

        ArrayAdapter<String> arr2 = new ArrayAdapter<String>(this, R.layout.row_add_timevalues, R.id.tv_row_timevalues, array2);
        listView2.setAdapter(arr2);


        String[] array = new String[62];
        array[0] = "--";
        array[61] = "--";
        for (int i = 1; i < 61; i++) {
            array[i] = StringUtils.leftPad(String.valueOf(i - 1), 2, "0");
        }
        ;

//        array2[0] = "--";
        //      array2[26] = "--";

        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, R.layout.row_add_timevalues, R.id.tv_row_timevalues, array);
        listView.setAdapter(arr);


        scrollViewHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollViewMins.scrollTo(0, 500);
                Toast.makeText(ActivitySleepScheduler.this, "YES", Toast.LENGTH_SHORT).show();

            }
        });

        //scrollerTask.run();
        //tvTest.setText("0");
        try {
            listView.setOnScrollListener(abs);
            listView2.setOnScrollListener(abs2);


        } catch (Exception e) {
            Toast.makeText(ActivitySleepScheduler.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }


    }


    AbsListView.OnScrollListener abs = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int scrollstate) {
            int ci = 0;
            switch (scrollstate) {
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    //if (tvTest.getText().equals("fling")) {
                    //}
                    //tvTest.setText("idle");
                    try {

                        //                     if (tvTest.getText().equals("adjust"))	{
                        //                     	tvTest.setText("");
                        //                     }
                        //       if (tvTest.getText().equals("scroll") || tvTest.getText().equals("fling")){

                        // Toast.makeText(ActivitySleepScheduler.this, String.valueOf(tv), Toast.LENGTH_SHORT).show();
                        x1 = listView.getFirstVisiblePosition();
                        x2 = listView.getLastVisiblePosition();
                        listView.setOnScrollListener(null);


                        if (listView.getChildAt(0).getTop() < -90) {
                            x1 = x1 + 1;
                        }

                        //listView.setSelection(x1);

                        if (tvTest.getText().equals("scroll")) {
                            listView.setSelection(x1);

                        } else if (tvTest.getText().equals("fling")) {
                            listView.setSelection(x1);
                        }


                        tvTest.setText(String.valueOf(String.valueOf(x1)));


                        listView.setOnScrollListener(abs);


                        //Toast.makeText(ActivitySleepScheduler.this, "XXXX", Toast.LENGTH_SHORT).show();

                        //     }
                         /*
                        int i = Integer.parseInt(String.valueOf(tvTest.getText()));
                        i++;
                        	listView.smoothScrollToPosition(i);	
                        
                        tvTest.setText(String.valueOf(i));
                        */
                    } catch (Exception e) {
                        tvTest.setText(String.valueOf(e));
                    }
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:

                    //listView.scrollTo(0,0);
                    if (!tvTest.getText().equals("adjust"))
                        ci = listView.getChildAt(0).getBottom();


                    tvTest.setText("fling");
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:


                    if (!tvTest.getText().equals("adjust"))

                        ci = listView.getChildAt(0).getBottom();

                    tvTest.setText("scroll");
                    break;
            }
        }


        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            listView.getChildAt(0);
            //if (tvTest.getText().equals("idle")){
            //	tvTest.setText("YES");
            //}
            //if (tvTest.getText().equals("idle")){
            //tvTest.setText(String.valueOf(i) + " " +String.valueOf(i1) + " " + String.valueOf(i2));
            count = i1;
            //}

            try {
                if (listView.getChildAt(0).getTop() < -90) {
                    //  TextView tv = listView.getChildAt(0).findViewById(R.id.tv_row_timevalues);
                    //  tv.setTextColor(getResources().getColor(R.color.gray));

                    TextView tv2 = listView.getChildAt(1).findViewById(R.id.tv_row_timevalues);
                    tv2.setTextColor(getResources().getColor(R.color.gray));

                    TextView tv3 = listView.getChildAt(2).findViewById(R.id.tv_row_timevalues);
                    tv3.setTextColor(getResources().getColor(R.color.purple_500));


                } else {


                    //TextView tv = listView.getChildAt(0).findViewById(R.id.tv_row_timevalues);
                    //  tv.setTextColor(getResources().getColor(R.color.gray));

                    TextView tv2 = listView.getChildAt(1).findViewById(R.id.tv_row_timevalues);
                    tv2.setTextColor(getResources().getColor(R.color.purple_500));

                    TextView tv3 = listView.getChildAt(2).findViewById(R.id.tv_row_timevalues);
                    tv3.setTextColor(getResources().getColor(R.color.gray));


                }
            } catch (Exception e) {

            }
        }
    };


    AbsListView.OnScrollListener abs2 = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int scrollstate) {
            int ci = 0;
            switch (scrollstate) {
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    //if (tvTest.getText().equals("fling")) {
                    //}
                    //tvTest.setText("idle");
                    try {

                        //                     if (tvTest.getText().equals("adjust"))	{
                        //                     	tvTest.setText("");
                        //                     }
                        //       if (tvTest.getText().equals("scroll") || tvTest.getText().equals("fling")){
                        x3 = listView2.getFirstVisiblePosition();
                        x2 = listView2.getLastVisiblePosition();
                        listView2.setOnScrollListener(null);

                        if (listView2.getChildAt(0).getTop() < -90) {
                            x3 = x3 + 1;
                        }

                        //listView2.setSelection(x3);

                        if (tvTest2.getText().equals("scroll")) {
                            listView2.setSelection(x3);

                        } else if (tvTest2.getText().equals("fling")) {
                            listView2.setSelection(x3);
                        }

                        listView2.setOnScrollListener(abs2);

                        tvTest2.setText(String.valueOf(String.valueOf(x3)));

                        // Toast.makeText(ActivitySleepScheduler.this, "XXXX", Toast.LENGTH_SHORT).show();

                        //     }
                         /*
                        int i = Integer.parseInt(String.valueOf(tvTest.getText()));
                        i++;
                        	listView.smoothScrollToPosition(i);	
                        
                        tvTest2.setText(String.valueOf(i));
                        */
                    } catch (Exception e) {
                        tvTest2.setText(String.valueOf(e));
                    }
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:

                    //listView2.scrollTo(0,0);
                    if (!tvTest2.getText().equals("adjust"))
                        ci = listView2.getChildAt(0).getBottom();

                    tvTest2.setText("fling");
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    if (!tvTest2.getText().equals("adjust"))

                        ci = listView2.getChildAt(0).getBottom();

                    tvTest2.setText("scroll");
                    break;
            }
        }


        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            //if (tvTest.getText().equals("idle")){
            //	tvTest.setText("YES");
            //}
            //if (tvTest.getText().equals("idle")){
            //tvTest.setText(String.valueOf(i) + " " +String.valueOf(i1) + " " + String.valueOf(i2));
            count = i1;
            try {
                if (listView2.getChildAt(0).getTop() < -90) {
                    //  TextView tv = listView2.getChildAt(0).findViewById(R.id.tv_row_timevalues);
                    //  tv.setTextColor(getResources().getColor(R.color.gray));

                    TextView tv2 = listView2.getChildAt(1).findViewById(R.id.tv_row_timevalues);
                    tv2.setTextColor(getResources().getColor(R.color.gray));

                    TextView tv3 = listView2.getChildAt(2).findViewById(R.id.tv_row_timevalues);
                    tv3.setTextColor(getResources().getColor(R.color.purple_500));


                } else {


                    //TextView tv = listView2.getChildAt(0).findViewById(R.id.tv_row_timevalues);
                    //  tv.setTextColor(getResources().getColor(R.color.gray));

                    TextView tv2 = listView2.getChildAt(1).findViewById(R.id.tv_row_timevalues);
                    tv2.setTextColor(getResources().getColor(R.color.purple_500));

                    TextView tv3 = listView2.getChildAt(2).findViewById(R.id.tv_row_timevalues);
                    tv3.setTextColor(getResources().getColor(R.color.gray));


                }
            } catch (Exception e) {

            }
            //}
        }
    };

    View.OnTouchListener touchlistener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            gestureDetector.onTouchEvent(motionEvent);

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                tvTest.setText("UP");
            }

            if (tvTest.getText().equals("UP")) {
                float y = scrollViewHours.getScrollY();
                int newy = (int) (y - (y % 180));
                scrollViewMins.smoothScrollTo(0, newy);
            }
            return false;
        }
    };

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            //Toast.makeText(ActivitySleepScheduler.this, "onScroll", Toast.LENGTH_SHORT).show();
            tvTest.setText("onScroll");
            return false;
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            //Toast.makeText(ActivitySleepScheduler.this, "onFling", Toast.LENGTH_SHORT).show();
            tvTest.setText("onFling");
            return false;
        }
    }

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
