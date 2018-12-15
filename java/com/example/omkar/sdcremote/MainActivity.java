package com.example.omkar.sdcremote;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    ToggleButton ACC1, ACC2, ACC3, ACC_OFF;
    ToggleButton BR1, BR2, BR3, BR_OFF;
    Button SteerLeft, SteerRight;
    TextView DegreeMeter;
    Button Connect;
    TextView IPtext, PortText;

    private int AccStatus =0;
    private int BrStatus = 0;
    private int SteerDegree=0;
    public static String IP_ADDRESS=null;
    public static int PORT = 0;
    private String FINAL_MESSAGE = null;
    private int TempA=1, TEmpB=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ACC1 = findViewById(R.id.Accelerator1);
        ACC2 = findViewById(R.id.Accelerator2);
        ACC3 = findViewById(R.id.Accelerator3);
        ACC_OFF = findViewById(R.id.AcceleratorOff);
        BR1 = findViewById(R.id.Brake1);
        BR2 = findViewById(R.id.Brake2);
        BR3 = findViewById(R.id.Brake3);
        BR_OFF = findViewById(R.id.BrakeOff);
        SteerLeft = findViewById(R.id.SteerLeft);
        SteerRight = findViewById(R.id.SteerRight);
        DegreeMeter = findViewById(R.id.DegreeMeter);
        Connect = findViewById(R.id.ConnectButton);
        IPtext = findViewById(R.id.IPText);
        PortText = findViewById(R.id.Porttext);

        ACC1.setOnCheckedChangeListener(ACCchangeChecker);
        ACC2.setOnCheckedChangeListener(ACCchangeChecker);
        ACC3.setOnCheckedChangeListener(ACCchangeChecker);
        ACC_OFF.setOnCheckedChangeListener(ACCchangeChecker);
        BR1.setOnCheckedChangeListener(BRchangeChecker);
        BR2.setOnCheckedChangeListener(BRchangeChecker);
        BR3.setOnCheckedChangeListener(BRchangeChecker);
        BR_OFF.setOnCheckedChangeListener(BRchangeChecker);

        SteerLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SteerDegree != -15){
                    SteerDegree-=1;
                }
                DegreeMeter.setText(Integer.toString(SteerDegree));
                FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                new SendData().execute(FINAL_MESSAGE);
                Log.w("My APP ",FINAL_MESSAGE);

            }
        });

        SteerRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SteerDegree != 15){
                    SteerDegree+=1;
                }
                DegreeMeter.setText(Integer.toString(SteerDegree));
                FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                new SendData().execute(FINAL_MESSAGE);
                Log.w("My APP ",FINAL_MESSAGE);

            }
        });

        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IP_ADDRESS=IPtext.getText().toString();
                if (PortText.getText().toString() != null) {
                    PORT=Integer.parseInt(PortText.getText().toString());
                }
                new SendData().execute("");
            }
        });


    }

    CompoundButton.OnCheckedChangeListener ACCchangeChecker = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == ACC1) {
                    ACC2.setChecked(false);
                    ACC3.setChecked(false);
                    ACC_OFF.setChecked(false);
                    AccStatus=1;
                    FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                    new SendData().execute(FINAL_MESSAGE);
                    Log.w("My APP ",FINAL_MESSAGE);
                }
                if (buttonView == ACC2) {
                    ACC1.setChecked(false);
                    ACC3.setChecked(false);
                    ACC_OFF.setChecked(false);
                    AccStatus=2;
                    FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                    new SendData().execute(FINAL_MESSAGE);
                    Log.w("My APP ",FINAL_MESSAGE);
                }
                if (buttonView == ACC3) {
                    ACC2.setChecked(false);
                    ACC1.setChecked(false);
                    ACC_OFF.setChecked(false);
                    AccStatus=3;
                    FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                    new SendData().execute(FINAL_MESSAGE);
                    Log.w("My APP ",FINAL_MESSAGE);
                }

                if (buttonView == ACC_OFF) {
                    ACC2.setChecked(false);
                    ACC1.setChecked(false);
                    ACC3.setChecked(false);
                    AccStatus=0;
                    FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                    new SendData().execute(FINAL_MESSAGE);
                    Log.w("My APP ",FINAL_MESSAGE);
                }
            }/*else{
                AccStatus=0;
                FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                new SendData().execute(FINAL_MESSAGE);
                Log.w("My APP ",FINAL_MESSAGE);
            }*/
        }


    };

    CompoundButton.OnCheckedChangeListener BRchangeChecker = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == BR1) {
                    BR2.setChecked(false);
                    BR3.setChecked(false);
                    BR_OFF.setChecked(false);
                    BrStatus=1;
                    FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                    new SendData().execute(FINAL_MESSAGE);
                    Log.w("My APP ",FINAL_MESSAGE);
                }
                if (buttonView == BR2) {
                    BR1.setChecked(false);
                    BR3.setChecked(false);
                    BR_OFF.setChecked(false);
                    BrStatus=2;
                    FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                    new SendData().execute(FINAL_MESSAGE);
                    Log.w("My APP ",FINAL_MESSAGE);
                }
                if (buttonView == BR3) {
                    BR2.setChecked(false);
                    BR1.setChecked(false);
                    BR_OFF.setChecked(false);
                    BrStatus=3;
                    FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                    new SendData().execute(FINAL_MESSAGE);
                    Log.w("My APP ",FINAL_MESSAGE);
                }

                if (buttonView == BR_OFF) {
                    BR2.setChecked(false);
                    BR1.setChecked(false);
                    BR3.setChecked(false);
                    BrStatus=0;
                    FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                    new SendData().execute(FINAL_MESSAGE);
                    Log.w("My APP ",FINAL_MESSAGE);
                }

            }/*else{
                BrStatus=0;
                FINAL_MESSAGE = Integer.toString(AccStatus)+","+Integer.toString(BrStatus)+","+Integer.toString(SteerDegree);
                new SendData().execute(FINAL_MESSAGE);
                Log.w("My APP ",FINAL_MESSAGE);
            }*/
        }


    };

    public static class SendData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                if(IP_ADDRESS!=null && PORT!=0){
                    Socket socket = new Socket(IP_ADDRESS,PORT);
                    DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                    DOS.writeUTF(strings[0]);
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.w("SOCKET ERROR::",e.toString());
            }
            return "Executed";
        }
    }

}
