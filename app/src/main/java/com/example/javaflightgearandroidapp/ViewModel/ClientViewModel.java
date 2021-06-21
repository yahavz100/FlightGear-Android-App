package com.example.javaflightgearandroidapp.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.javaflightgearandroidapp.Model.ActiveClientModel;

public class ClientViewModel {
    private ActiveClientModel activeClientModel;
    //public MutableLiveData<String> propertyConnectionStatus;

    public ClientViewModel(ActiveClientModel activeClientModel) {
        this.activeClientModel = activeClientModel;
    }

    public void connect(String ipAddress, int port) {
        activeClientModel.assembleConnection(ipAddress, port);
    }

    public void disconnect() {
        activeClientModel.assembleDisconnection();
    }

    public void send(String command, float value) {
        activeClientModel.write(command, value);
    }
}
