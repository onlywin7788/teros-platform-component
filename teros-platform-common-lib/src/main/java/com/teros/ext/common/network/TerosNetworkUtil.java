package com.teros.ext.common.network;
import java.net.InetAddress;
import java.net.Socket;

public class TerosNetworkUtil {

    public boolean isHostAlive(String ip, int port)
    {
        boolean result = false;
        try {
            (new Socket(ip, port)).close();
            result = true;
        }
        catch(Exception e) {
        }
        return result;
    }
}
