package com.example.javaflightgearandroidapp.Model;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client implements IClient{
    private String connectionStatus;
    private Socket accessServer;
    private final Map<String, String> axes;

    public Client() {
        connectionStatus = "Disconnected";
        accessServer = new Socket();
        axes = new HashMap<>();
        axes.put("Aileron", "set /controls/flight/aileron ");
        axes.put("Elevator","set /controls/flight/elevator ");
        axes.put("Rudder", "set /controls/flight/rudder ");
        axes.put("Throttle", "set /controls/engines/current-engine/throttle ");
    }

    /*Function attempt to connect to server according to given IPAddress and Port, in a case
     * of a unsuccessful connection disconnects, changes connection status accordingly
     */
    @Override
    public void connect(String ipAddress, int port) {
        //Check if already connected to a server
        if(getConnectionStatus().equals("Connected")) {
            disconnect();
        }
        //Attempt to connect to server via socket, defined timeout of 10 seconds
        try {
            accessServer = new Socket(ipAddress, port);
            connectionStatus = "Connected";
            //In case of Exception, disconnect from server
        } catch (Exception e) {
            connectionStatus = "Disconnected";
            e.printStackTrace();
            disconnect();
        }
    }

    /*Function attempt to disconnect from current connected server, if done successfully changes
     * connection status else, do nothing.
     */
    @Override
    public void disconnect() {
        //Check if already not connected to any server
        if (getConnectionStatus().equals("Disconnected")) {
            return;
        }
        //Attempt to disconnect from server
        try {
            accessServer.shutdownInput();
            accessServer.shutdownOutput();
            accessServer.close();
            //In case of Exception do nothing, default - change connection status
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionStatus = "Disconnected";
        }
    }

    /*Function write bytes into output stream of connected socket,
     * synchronized for safe writing, catch Exception and do nothing
     */
    @Override
    public void write(String command, float value) {
        PrintWriter outputWriter;
        try {
            outputWriter = new PrintWriter(accessServer.getOutputStream(), true);
            String commandFg = axes.get(command)+ value + "\r\n";
            outputWriter.print(commandFg);
            outputWriter.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

}
