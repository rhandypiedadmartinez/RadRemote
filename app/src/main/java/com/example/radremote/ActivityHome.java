package com.example.radremote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener {
    ConsumerIrManager irblaster;
    LinearLayout layoutList;
    Button btnAdd;
    ImageView btnOfftimeSchedulerActivity;
    List<String> teamList = new ArrayList<>();
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        scrollView = findViewById(R.id.scrollview);
        irblaster = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);
        layoutList = findViewById(R.id.layout_list);
        btnAdd = findViewById(R.id.btn_add_ctrl);
        btnAdd.setOnClickListener(this);
        btnOfftimeSchedulerActivity = findViewById(R.id.btn_offtime_scheduler);
        btnOfftimeSchedulerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityHome.this, ActivitySleepScheduler.class));
            }
        });
        Remote myRemote = new PensonicRemote();
        preLoadRemote(myRemote);
    }

    private void preLoadRemote(Remote remote) {
        Iterator<RemoteButton> it = remote.getRemoteButtons().iterator();
        while (it.hasNext()) {
            RemoteButton control = it.next();
            addView(control.buttonName, control.deviceAddress, control.commandCode);
        }
        it = null;
        System.gc();
    }

    @Override
    public void onClick(View view) {
        addView("", "", "");
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    private boolean addView(String controlName, String deviceAddress, String commandCode) {
        View controlView = getLayoutInflater().inflate(R.layout.row_add_control, null, false);

        EditText etControl = (EditText) controlView.findViewById(R.id.et_control_name);
        etControl.setText(controlName);

        EditText etDeviceAddr = (EditText) controlView.findViewById(R.id.et_dev_addr);
        etDeviceAddr.setText(deviceAddress);

        EditText etCommandCode = (EditText) controlView.findViewById(R.id.et_cmd_code);
        etCommandCode.setText(commandCode);

        ImageView imgTransmit = (ImageView) controlView.findViewById(R.id.img_transmit);
       
        setOnClick(imgTransmit, controlName,deviceAddress ,commandCode);

        layoutList.addView(controlView);

        return true;
    }


    private void removeView(View view) {
        layoutList.removeView(view);
    }

    public void setOnClick(ImageView btn, String controlName, String deviceAddress, String commandCode){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String str= controlName + deviceAddress + commandCode;
            if (str.equals(""))
                Toast.makeText(ActivityHome.this, "Edit Pensonic class instead/Wrong implementation soryy", Toast.LENGTH_SHORT).show();
            
            
             try {
                RemoteButton control = new RemoteButton(controlName, deviceAddress, commandCode);
                irblaster.transmit(38000, control.rawIrPattern);

                // destruct object
                //control = null;
                System.gc();
                // Toast.makeText(ActivityHome.this, "IR Transmitted", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(ActivityHome.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
            }
            }
        });
    };

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                //RemoteButton control = new RemoteButton(controlName, deviceAddress, commandCode);
               // irblaster.transmit(38000, control.rawIrPattern);

                // destruct object
                //control = null;
                System.gc();
                // Toast.makeText(ActivityHome.this, "IR Transmitted", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(ActivityHome.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
            }
            //removeView(controlView);
        }
    };
}
