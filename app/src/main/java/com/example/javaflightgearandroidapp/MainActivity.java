package com.example.javaflightgearandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.javaflightgearandroidapp.Model.ActiveClientModel;
import com.example.javaflightgearandroidapp.Model.Client;
import com.example.javaflightgearandroidapp.Model.IClient;
import com.example.javaflightgearandroidapp.ViewModel.ConnectionViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connectToServer(View view) {
        IClient cm = new Client();
        ActiveClientModel acm = new ActiveClientModel(cm);
        ConnectionViewModel cvm = new ConnectionViewModel(acm);

        EditText ipEditText = (EditText) findViewById(R.id.IPAddress);
        String ipVal = ipEditText.getText().toString().trim();

        EditText portEditText = (EditText) findViewById(R.id.port);
        String portVal = portEditText.getText().toString().trim();
        int intPortVal = Integer.parseInt(portVal);

        cvm.connect(ipVal, intPortVal);
    }
}