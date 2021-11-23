package unit_test.network;
import com.teros.ext.common.network.TerosNetworkUtil;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;

public class TerosNEtworkUtilTest {

    TerosNetworkUtil terosNetworkUtil = new TerosNetworkUtil();

    @Test
    public void pingTest() {

        String ipv4 = "10.10.19.102";

        boolean ret = terosNetworkUtil.isHostAliveByIPV4(ipv4);
        System.out.println(ret);
    }
}
