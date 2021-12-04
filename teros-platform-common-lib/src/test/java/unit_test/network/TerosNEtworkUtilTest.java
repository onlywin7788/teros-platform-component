package unit_test.network;
import com.teros.ext.common.network.TerosNetworkUtil;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.Socket;

public class TerosNEtworkUtilTest {

    TerosNetworkUtil terosNetworkUtil = new TerosNetworkUtil();

    @Test
    public void hostAliveTest() {

        String ip = "10.10.19.102";
        int port = 443;

        boolean ret = terosNetworkUtil.isHostAlive(ip, port);
        System.out.println(ret);
    }
}
