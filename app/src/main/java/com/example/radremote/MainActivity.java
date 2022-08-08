package com.example.radremote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import android.hardware.ConsumerIrManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected static Button btnDecrypt;
    protected static EditText etNotepad;
    protected static EditText etMessage;
    protected static List listmessage;
    protected static ConsumerIrManager irblaster;
    protected static int irCodesIndex;

    @Override
    protected void onStart() {
        super.onStart();
        try {
            irblaster = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);
            btnDecrypt = (Button) findViewById(R.id.btnDecrypt);
            etNotepad = (EditText) findViewById(R.id.etNotepad);
            etMessage = (EditText) findViewById(R.id.etMessage);
            listmessage = new ArrayList<String>();
            RemoteButton rmt = new RemoteButton("00FE", "50AF");
            btnDecrypt.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {
                    try {
                        etMessage.setText(rmt.strRawIrPattern);
                    } catch (Exception e){
                        etMessage.setText(rmt.strRawIrPattern);
                    }
                }
            });

        } catch (Exception e){
            etMessage.setText(String.valueOf(e));
        }
    }
}

/*
* String data[] = {
                       "0000 0158 00AC " + // Lead In Burst

                       //Device Address
                       // 0x00
                       "0015 0015 0015 0015 0015 0015 0015 0015 " +
                       "0015 0015 0015 0015 0015 0015 0015 0015 " +
                        //b11111110 or 0xFE
                        "0015 0040 0015 0040 0015 0040 0015 0040 " +
                        "0015 0040 0015 0040 0015 0040 0015 0015 " +

                       // Command Code
                       // b01011111 or 0x50
                        "0015 0015 0015 0040 0015 0015 0015 0040 " +
                        "0015 0015 0015 0015 0015 0015 0015 0015 " +

                       // Complement of Command Code
                       // b10101111 or 0xAF
                       "0015 0040 0015 0015 0015 0040 0015 0015 " +
                       "0015 0040 0015 0040 0015 0040 0015 0040 " +

                        // Lead Out
                        "0015 38A4"
                    };

                    String strFreq = String.valueOf(irblaster.hasIrEmitter());
                    int freq = 38222;
                    data[irCodesIndex] = "0x" + data[irCodesIndex];
                    data[irCodesIndex] = data[irCodesIndex].replace(" ", " 0x");
                    List listData = Arrays.asList(data[irCodesIndex].split(" "));

                    int[] arrValues = new int[listData.size()-1];

                    String egg = "INDEX: " + irCodesIndex + "\n";
                    int i = 0;
                    for (Object strHex : listData) {
                        int valueInt = Integer.decode(String.valueOf(strHex)) * 1000000 / freq;
                        if (valueInt==0){
                            continue;
                        }
                        egg += " " + valueInt;
                        arrValues[i++] = valueInt;
                    }

                    try {
                        irblaster.transmit(freq, arrValues);
                        etNotepad.setText("IR Transmitted");
                    } catch (Exception e) {
                        etNotepad.setText(String.valueOf(e));

                    }
                    if (irCodesIndex == data.length-1) {
                        irCodesIndex = 0;
                    } else {
                        irCodesIndex++;
                    }
                    etMessage.setText(egg);
*
* */