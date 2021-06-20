package com.example.javaflightgearandroidapp.Model;

public interface IClient {
    void connect(String IPAddress, int port);
    void disconnect();
    void write(String command, int value);
}
