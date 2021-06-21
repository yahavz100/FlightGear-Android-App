package com.example.javaflightgearandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.javaflightgearandroidapp.Model.ActiveClientModel;
import com.example.javaflightgearandroidapp.Model.Client;
import com.example.javaflightgearandroidapp.Model.IClient;
import com.example.javaflightgearandroidapp.ViewModel.ClientViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private IClient cm = new Client();
    private ActiveClientModel acm = new ActiveClientModel(cm);
    private ClientViewModel cvm = new ClientViewModel(acm);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create connection status TextView
        TextView connectionStatusTV = (TextView) findViewById(R.id.connectionStatusTextView);
        connectionStatusTV.setText(cm.getConnectionStatus());

        int period = 1000;  //todo invoke disconnect or add status button?
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                connectionStatusTV.setText(cm.getConnectionStatus());//todo maybe run connect?
            }
        }, 0, period);

        //Create throttle SeekBar
        SeekBar throttleSeekBar = (SeekBar) findViewById(R.id.throttleSeekBar);
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
        SeekBar rudderSeekBar = (SeekBar) findViewById(R.id.rudderSeekBar);
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
        EditText ipEditText = (EditText) findViewById(R.id.IPAddress);
        String ipVal = ipEditText.getText().toString().trim();

        EditText portEditText = (EditText) findViewById(R.id.port);
        String portVal = portEditText.getText().toString().trim();
        int intPortVal = Integer.parseInt(portVal);

        cvm.connect(ipVal, intPortVal);
    }

}