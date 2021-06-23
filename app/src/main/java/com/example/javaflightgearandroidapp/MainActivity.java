package com.example.javaflightgearandroidapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaflightgearandroidapp.Model.ActiveClientModel;
import com.example.javaflightgearandroidapp.Model.Client;
import com.example.javaflightgearandroidapp.Model.IClient;
import com.example.javaflightgearandroidapp.ViewModel.ClientViewModel;
import com.jackandphantom.joystickview.JoyStickView;

public class MainActivity extends AppCompatActivity {
    private IClient cm = new Client();
    private ActiveClientModel acm = new ActiveClientModel(cm);
    private ClientViewModel cvm = new ClientViewModel(acm);

    TextView connectionStatusTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create connection status TextView
        connectionStatusTV = findViewById(R.id.connectionStatusTextView);


        JoyStickView joyStickView = findViewById(R.id.Joystick1);
        joyStickView.setOnMoveListener(new JoyStickView.OnMoveListener() {
            @Override
            public void onMove(double angle, float strength) {
                strength = strength / 100;
                float dy = (float) (strength * (Math.cos(Math.toRadians(angle))));
                float dx = (float) (strength * (Math.sin(Math.toRadians(angle))));
                System.out.println("x:"+dx +", y:"+dy);
                cvm.send("Aileron", dy);
                cvm.send("Elevator", dx);
            }
        });



//        int period = 1000;  //todo invoke disconnect or add status button?
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                connectionStatusTV.setText(cm.getConnectionStatus());//todo maybe run connect?
//            }
//        }, 0, period);

        //Create throttle SeekBar
        SeekBar throttleSeekBar = findViewById(R.id.throttleSeekBar);
        throttleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String command = "Throttle";
                float progress = (float) i / 100;
                cvm.send(command, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Create rudder SeekBar
        SeekBar rudderSeekBar = findViewById(R.id.rudderSeekBar);
        rudderSeekBar.setProgress(50);
        rudderSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String command = "Rudder";
                float progress = (float) (i - 50) / 100;
                cvm.send(command, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //Function connect to FlightGear server when button connect is clicked
    public void connectToServer(View view) {
        EditText ipEditText = findViewById(R.id.IPAddress);
        String ipVal = ipEditText.getText().toString().trim();

        EditText portEditText = findViewById(R.id.port);
        String portVal = portEditText.getText().toString().trim();
        int intPortVal = Integer.parseInt(portVal);

        final Handler handler = new Handler();
        final int delay = 1000; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                System.out.println("myHandler: here!");
                cvm.connect(ipVal, intPortVal);
                //connectionStatusTV.setText(cm.getConnectionStatus());

                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    @Override
    protected void onDestroy() {
        cvm.disconnect();
        super.onDestroy();
    }

}