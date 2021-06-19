package com.example.javaflightgearandroidapp.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActiveClientModel {
    Client fgClient;
    BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<>();

    public ActiveClientModel() {
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
}
