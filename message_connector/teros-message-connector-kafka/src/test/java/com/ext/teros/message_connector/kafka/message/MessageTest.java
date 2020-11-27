package com.ext.teros.message_connector.kafka.message;

import com.ext.teros.message.common.file.CommonFile;
import com.ext.teros.message.converter.MessageConverter;
import com.ext.teros.message.loader.MessageSetLoader;
import com.ext.teros.message.object.MessageObject;
import com.ext.teros.message.set.MessageSet;
import com.ext.teros.message_connector.kafka.Executor;
import org.junit.jupiter.api.Test;

public class MessageTest {

    String configPath = "E:/teros_home/config/data-service/interface/REST_TO_KAFKA/flow/output-connector.xml";
    String dataXmlPath
            = "e:/teros_home/data.xml";
    String dataJsonPath
            = "e:/teros_home/data.json";

    String messageSetFilePath
            = "e:/teros_home/config/data-service/interface/REST_TO_KAFKA/flow/message-set/common-message-set.xml";

    @Test
    public void test() throws Exception {

        MessageObject messageObject = getMessageObject();

        Executor executor = new Executor();
        executor.loadConfig(configPath);
        executor.initialize();

        executor.setOutputData(messageObject);

        executor.preOutput();
        executor.output();
        executor.postInput();
    }

    public MessageObject getMessageObject() throws Exception {

        MessageSetLoader messageSetLoader = new MessageSetLoader();
        MessageConverter messageConverter = new MessageConverter();
        CommonFile commonFile = new CommonFile();

        MessageSet messageSet = null;
        MessageObject messageObject = null;
        String contents = "";
        byte[] returnByte = null;
        messageSet = messageSetLoader.getMessageSet(messageSetFilePath);
        int testCase_toObject = 0;
        int testCase_fromObject = 0;


        // CASE 1 : XML_TO_OBJECT
        // CASE 2 : JSON_TO_OBJECT
        testCase_toObject = 2;

        // CASE 1 : OBJECT_TO_XML
        // CASE 2 : OBJECT_TO_JSON
        testCase_fromObject = 2;


        if (testCase_toObject == 1) {
            contents = commonFile.readFile(dataXmlPath);
            messageObject = messageConverter.convertToObject(messageConverter.XML_TO_OBJECT, messageSet, contents.getBytes());
        }
        if (testCase_toObject == 2) {
            contents = commonFile.readFile(dataJsonPath);
            messageObject = messageConverter.convertToObject(messageConverter.JSON_TO_OBJECT, messageSet, contents.getBytes());
        }

        return messageObject;
    }
}
