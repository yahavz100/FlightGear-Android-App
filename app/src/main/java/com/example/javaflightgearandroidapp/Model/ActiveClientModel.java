package com.example.javaflightgearandroidapp.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActiveClientModel {
    IClient fgClient;
    BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<>();

    public ActiveClientModel(IClient clientModel) {
        this.fgClient = clientModel;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        dispatchQueue.take().run();
                    } catch (InterruptedException e) {}
                }
            }
        }).start();
    }

    public void assembleConnection(String ipAddr, int port) {
        try {
            dispatchQueue.put(new Runnable() {
                @Override
                public void run() {
                    fgClient.connect(ipAddr, port);
                }
            });
        } catch(Exception e) { }
    }

    public void assembleDisconnection() {
        try {
            dispatchQueue.put(new Runnable() {
                @Override
                public void run() {
                    fgClient.disconnect();
                }
            });
        } catch(Exception e) { }
    }

    public void write(String command, int value) {
        try {
            dispatchQueue.put(new Runnable() {
                @Override
                public void run() {
                    fgClient.write(command, value);
                }
            });
        } catch(Exception e) {}
    }
}
