package com.ext.teros.message.set;

import com.ext.teros.message.common.file.CommonFile;
import com.ext.teros.message.convert.MessageConverter;
import com.ext.teros.message.loader.MessageSetLoader;
import com.ext.teros.message.model.message.*;
import com.ext.teros.message.object.MessageObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessageConvertorTest {

    String dataXmlPath
            = "e:/teros_home/data.xml";

    String dataJsonPath
            = "e:/teros_home/data.json";

    String messageSetFilePath
            = "e:/teros_home/config/data-service/interface/REST_TO_KAFKA/message-set/common-message-set.xml";

    @Test
    public void test() throws Exception {


        MessageSetLoader messageSetLoader = new MessageSetLoader();
        MessageConverter messageConverter = new MessageConverter();
        CommonFile commonFile = new CommonFile();

        MessageSet messageSet = null;
        MessageObject messageObject = null;
        String contents = "";
        messageSet = messageSetLoader.getMessageSet(messageSetFilePath);
        int testCase_toObject = 0;
        int testCase_fromObject = 0;


        // CASE 1 : XML_TO_OBJECT
        // CASE 2 : JSON_TO_OBJECT
        testCase_toObject = 1;

        // CASE 1 : OBJECT_TO_XML
        // CASE 2 : OBJECT_TO_JSON
        testCase_fromObject = 1;


        if (testCase_toObject == 1) {
            contents = commonFile.readFile(dataXmlPath);
            messageObject = messageConverter.convert(messageConverter.XML_TO_OBJECT, messageSet, contents.getBytes());
        }
        if (testCase_toObject == 2) {
            contents = commonFile.readFile(dataJsonPath);
            messageObject = messageConverter.convert(messageConverter.JSON_TO_OBJECT, messageSet, contents.getBytes());
        }

        messageObjectDump(messageObject);
    }

    public void messageObjectDump(MessageObject messageObject) throws Exception {

        ConcurrentHashMap<String, String> optionMap = null;
        Set set = null;
        Iterator iterator = null;
        String mapDumpString = null;


        // table
        ArrayList<Table> tableList = messageObject.getTableList();
        for (int tableIdx = 0; tableIdx < tableList.size(); tableIdx++) {
            Table table = tableList.get(tableIdx);

            optionMap = table.getOptionMap();

            mapDumpString = optionMapDump(optionMap);
            System.out.println("#TABLE => " + mapDumpString);

            // record
            ArrayList<Record> recordList = table.getRecordList();

            for (int recordIdx = 0; recordIdx < recordList.size(); recordIdx++) {
                Record record = recordList.get(recordIdx);

                optionMap = record.getOptionMap();
                mapDumpString = optionMapDump(optionMap);
                System.out.println("\t#RECORD => " + mapDumpString);


                // field
                ArrayList<Field> fieldList = record.getFieldList();
                for (int fieldIdx = 0; fieldIdx < recordList.size(); fieldIdx++) {
                    Field field = fieldList.get(fieldIdx);

                    optionMap = field.getOptionMap();

                    mapDumpString = optionMapDump(optionMap);
                    System.out.println("\t\t#FIELD => " + mapDumpString + "[value:" + new String(field.getValue()) + "]");

                }
            }
        }
    }

    public String optionMapDump(ConcurrentHashMap<String, String> optionMap) {

        String returnString = "";

        Set set = optionMap.keySet();
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = optionMap.get(key);

            returnString += String.format("[%s:%s]", key, value);
        }
        return returnString;
    }
}
