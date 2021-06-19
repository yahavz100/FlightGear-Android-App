package com.example.javaflightgearandroidapp.Model;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Observable;

public class Client extends Observable implements IClient{
    private String connectionStatus;
    private final Socket accessServer;

    public Client() {
        connectionStatus = "Disconnected";
        accessServer = new Socket();
    }

    /*Function attempt to connect to server according to given IPAddress and Port, in a case
     * of a unsuccessful connection disconnects, else changes connection status
     */
    @Override
    public void connect(String IPAddress, int port) {
        //Check if already connected to a server
        if(connectionStatus.equals("Connected")) {
            disconnect();
        }
        //Attempt to connect to server via socket, defined timeout of 10 seconds
        try {
            int integerIP = Integer.parseInt(IPAddress);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(integerIP);
            int timeout = 10000;
            accessServer.connect(inetSocketAddress, timeout);
            //ipAddress = IPAddress;
            //this.port = port;
            setChanged();
            notifyObservers();
            //In case of Exception, disconnect from server
        } catch (Exception e) {
            disconnect();
        }
        connectionStatus = "Connected";
    }

    /*Function attempt to disconnect from current connected server, if done successfully changes
     * connection status else, do nothing.
     */
    @Override
    public void disconnect() {
        //Check if already not connected to any server
        if (connectionStatus.equals("Disconnected")) {
            return;
        }
        //Attempt to disconnect from server
        try {
            accessServer.shutdownInput();
            accessServer.shutdownOutput();
            accessServer.close();
            //In case of Exception do nothing, default - change connection status
        } catch (Exception ignored) {
        } finally {
            //ipAddress = "0.0.0.0";
            //port = 0;
            connectionStatus = "Disconnected";
        }
    }
}
