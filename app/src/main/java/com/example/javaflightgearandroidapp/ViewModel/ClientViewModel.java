package com.example.javaflightgearandroidapp.ViewModel;

import com.example.javaflightgearandroidapp.Model.ActiveClientModel;

import java.io.Serializable;

public class ClientViewModel implements Serializable {
    private ActiveClientModel activeClientModel;

    //Using given active client model, client view model transfer data from view to model
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
