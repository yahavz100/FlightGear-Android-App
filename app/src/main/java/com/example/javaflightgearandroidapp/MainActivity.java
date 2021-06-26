package com.example.javaflightgearandroidapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
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
    Handler handler = null;
    final int delay = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        //Create connection status TextView
        connectionStatusTV = findViewById(R.id.connectionStatusTextView);
        connectionStatusTV.setText(cm.getConnectionStatus());

        //Create custom color
        int lightGrey = Color.rgb(90, 136, 203);

        //Paint button in color
        Button connectBtn = findViewById(R.id.connectButton);
        connectBtn.setBackgroundColor(lightGrey);
        connectBtn.setTextColor(Color.BLACK);

        //Paint button in color
        Button discBtn = findViewById(R.id.disconnectButton);
        discBtn.setBackgroundColor(lightGrey);
        discBtn.setTextColor(Color.BLACK);

        //Create joystick and customize it
        JoyStickView joyStickView = findViewById(R.id.Joystick1);
        joyStickView.setInnerCircleColor(Color.RED);
        joyStickView.setOuterCircleColor(Color.GRAY);
        joyStickView.setOuterCircleBorderColor(Color.BLACK);

        //Add a listener on move to joystick, send data to model on movement
        joyStickView.setOnMoveListener(new JoyStickView.OnMoveListener() {
            @Override
            public void onMove(double angle, float strength) {
                strength = strength / 100;
                float dy = (float) (strength * (Math.cos(Math.toRadians(angle))));
                float dx = (float) (strength * (Math.sin(Math.toRadians(angle))));
                cvm.send("Aileron", dy);
                cvm.send("Elevator", dx);
            }
        });

        //Create throttle SeekBar
        SeekBar throttleSeekBar = findViewById(R.id.throttleSeekBar);
        throttleSeekBar.getThumb().setColorFilter(lightGrey, PorterDuff.Mode.MULTIPLY);
        throttleSeekBar.getProgressDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        //Add a listener on move to seekbar, send data to model on movement
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
        rudderSeekBar.getThumb().setColorFilter(lightGrey, PorterDuff.Mode.MULTIPLY);
        rudderSeekBar.getProgressDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        //Add a listener on move to seekbar, send data to model on movement
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
        //Create textView for IP
        EditText ipEditText = findViewById(R.id.IPAddress);
        String ipVal = ipEditText.getText().toString().trim();

        //Create textView for Port
        EditText portEditText = findViewById(R.id.port);
        String portVal = portEditText.getText().toString().trim();
        int intPortVal = Integer.parseInt(portVal);

        handler.removeCallbacksAndMessages(null);
        //Every second connect to server(in order to maintain connection status updated)
        handler.postDelayed(new Runnable() {
            public void run() {
                cvm.connect(ipVal, intPortVal);
                connectionStatusTV.setText(cm.getConnectionStatus());
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    //Function terminate connection to FlightGear server when button is clicked
    public void disconnectFromServer(View view) {

        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            public void run() {
                cvm.disconnect();
                connectionStatusTV.setText(cm.getConnectionStatus());
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