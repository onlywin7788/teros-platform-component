package com.ext.teros.message_connector.rest;

import com.ext.teros.message_connector.spec.MessageConnectorSpec;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class ApplicationTests {

    Executor executor = null;
    MessageConnectorSpec connector = null;

    public ApplicationTests() {
        executor = new Executor();
        connector = (MessageConnectorSpec) executor;
    }

    @Test
    void dataTest() throws Exception {

        String inputData = "testMessage";
        connector.input(inputData);

        // output
        String outputData = connector.output();
        System.out.println(outputData);
    }

}
