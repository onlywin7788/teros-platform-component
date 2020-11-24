package com.ext.teros.message_connector.kafka;

import com.ext.teros.message_connector.kafka.config.Loader;
import com.ext.teros.message_connector.kafka.config.model.Connection;
import org.junit.jupiter.api.Test;

public class ConfigLoaderTest {

    Loader loader = new Loader();
    Connection connection = new Connection();

    String configString =
            "{\n" +
                    "  \"connection\": {\n" +
                    "    \"host\": \"10.10.2.102\",\n" +
                    "    \"port\": \"9092\",\n" +
                    "    \"topic\": \"teros-kafka-topic-test-001\"\n" +
                    "  },\n" +
                    "  \"option\": {}\n" +
                    "}";

    @Test
    public void test1()
    {
        try {
            System.out.println("-----------------------");
            System.out.println(configString);
            System.out.println("-----------------------");

            loader.loadConfig(configString);
            connection = loader.getConnection();

            System.out.println(connection.getHost());
            System.out.println(connection.getPort());
            System.out.println(connection.getTopic());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
