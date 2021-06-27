package com.example.javaflightgearandroidapp.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActiveClientModel {
    private final IClient fgClient;
    private final BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<>();

    //Using Active Model design pattern, implemented by dispatch queue, insert tasks to execute
    //asyc inorder to keep application responding to user
    public ActiveClientModel(IClient clientModel) {
        this.fgClient = clientModel;
        new Thread(() -> {
            while (true) {
                try {
                    dispatchQueue.take().run();
                } catch (InterruptedException e) {}
            }
        }).start();
    }

    //Function insert connect method to queue
    public void assembleConnection(String ipAddr, int port) {
        try {
            dispatchQueue.put(() -> fgClient.connect(ipAddr, port));
        } catch(Exception e) { }
    }

    //Function insert disconnect method to queue
    public void assembleDisconnection() {
        try {
            dispatchQueue.put(fgClient::disconnect);
        } catch(Exception e) { }
    }

    //Function insert write method to queue
    public void write(String command, float value) {
        try {
            dispatchQueue.put(() -> fgClient.write(command, value));
        } catch(Exception e) {}
    }
}
