package com.ext.teros.message_connector.kafka;

import com.ext.teros.message_connector.kafka.config.Loader;
import com.ext.teros.message_connector.kafka.config.model.Connection;
import org.junit.jupiter.api.Test;

public class KafkaDataSendTest {

    Connection connection = new Connection();
    Executor executor = new Executor();

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
    public void test1() {

        try {

            executor.loadConfig(configString);
            executor.initialize();
            executor.connect();
            executor.open();;
            executor.setData("hahaha");
            executor.output();
            executor.close();
            executor.disconnect();
            executor.uninitialize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
