package com.teros.ext.common.network;
import java.net.InetAddress;

public class TerosNetworkUtil {

    public boolean isHostAliveByIPV4(String ipv4)
    {
        boolean ret = false;
        try {
            InetAddress pingcheck = InetAddress.getByName(ipv4);
            ret = pingcheck.isReachable(300);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
