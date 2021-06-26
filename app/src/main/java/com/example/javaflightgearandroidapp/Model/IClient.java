package com.example.javaflightgearandroidapp.Model;

public interface IClient {
    //Function connect to server using ip and port given
    void connect(String IPAddress, int port);

    //Function disconnect from current server
    void disconnect();

    //Function write value of command into current server connected
    void write(String command, float value);

    //Function returns connection status to server
    public String getConnectionStatus();
}
