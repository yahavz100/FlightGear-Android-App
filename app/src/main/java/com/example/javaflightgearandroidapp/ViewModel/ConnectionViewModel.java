package com.example.javaflightgearandroidapp.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.javaflightgearandroidapp.Model.ActiveClientModel;

public class ConnectionViewModel {
    private ActiveClientModel activeClientModel;
    //public MutableLiveData<String> propertyConnectionStatus;

    public ConnectionViewModel(ActiveClientModel activeClientModel) {
        this.activeClientModel = activeClientModel;
    }

    public void connect(String ipAddress, int port) {
        activeClientModel.assembleConnection(ipAddress, port);
    }

    public void disconnect() {
        activeClientModel.assembleDisconnection();
    }
}
