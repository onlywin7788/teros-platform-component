package com.ext.teros.message_connector.rest.executor;

import com.ext.teros.message.object.MessageObject;
import com.ext.teros.message_connector.rest.Executor;
import com.ext.teros.message_connector.rest.common.file.CommonFile;
import org.junit.jupiter.api.Test;

public class ExecutorTest {

    String configPath = "E:/teros_home/config/data-service/interface/REST_TO_KAFKA/flow/input-connector.xml";
    String dataXmlPath
            = "e:/teros_home/data.xml";
    String dataJsonPath
            = "e:/teros_home/data.json";

    @Test
    public void Test() throws Exception {

        CommonFile commonFile = new CommonFile();
        byte[] readBytes = commonFile.readFile(dataJsonPath);
        String contents = new String(readBytes);


        Executor executor = new Executor();
        executor.loadConfig(configPath);
        executor.setData(contents);
        executor.input();

        MessageObject messageObject = (MessageObject)executor.getData();
    }
}
