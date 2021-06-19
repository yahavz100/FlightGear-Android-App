package com.example.javaflightgearandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.javaflightgearandroidapp.Model.ActiveClientModel;
import com.example.javaflightgearandroidapp.ViewModel.ConnectionViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connectToServer(View view) {
        EditText ipEditText = (EditText) findViewById(R.id.IPAddress);
        String ipVal = ipEditText.getText().toString().trim();

        EditText portEditText = (EditText) findViewById(R.id.port);
        String portVal = portEditText.getText().toString().trim();
        Integer intPortVal = Integer.parseInt(portVal);

        ActiveClientModel acm = new ActiveClientModel();
        ConnectionViewModel cvm = new ConnectionViewModel(acm);
        cvm.connect(ipVal, intPortVal);
    }
}