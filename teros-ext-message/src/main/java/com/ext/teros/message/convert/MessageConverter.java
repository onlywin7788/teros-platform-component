package com.ext.teros.message.convert;

import com.ext.teros.message.common.parser.JsonParser;
import com.ext.teros.message.common.parser.XmlParser;
import com.ext.teros.message.model.message.*;
import com.ext.teros.message.object.MessageObject;
import com.ext.teros.message.set.MessageSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class MessageConverter {

    // contents to object
    public final int XML_TO_OBJECT = 10001;
    public final int JSON_TO_OBJECT = 10002;
    public final int CSV_TO_OBJECT = 10003;
    public final int FIXED_TO_OBJECT = 10004;

    // object to contents
    public final int OBJECT_TO_XML = 20001;
    public final int OBJECT_TO_JSON = 20002;
    public final int OBJECT_TO_CSV = 20003;
    public final int OBJECT_TO_FIXED = 20004;

    public MessageObject convert(int convertType, MessageSet messageSet, byte[] sourceContents) throws Exception {

        MessageObject messageObject = null;

        if (convertType == XML_TO_OBJECT) {
            messageObject = convertXmlToObject(messageSet, sourceContents);
        }
        if (convertType == JSON_TO_OBJECT) {
            messageObject = convertJsonToObject(messageSet, sourceContents);
        }

        return messageObject;
    }

    public MessageObject convertXmlToObject(MessageSet messageSet, byte[] sourceContents) throws Exception {

        String contents = new String(sourceContents);
        MessageObject messageObject = new MessageObject();

        XmlParser xmlParser = new XmlParser();
        xmlParser.loadString(contents);

        // load MessageSet

        // load Contents
        String rootPath = messageSet.getOptionMap().get("root_path");
        Node rootNode = xmlParser.getNode(rootPath);

        // table
        ArrayList<Node> tableList = xmlParser.getNodeChildListFromNode(rootNode);
        ArrayList<Table> tableSetList = messageSet.getTableList();

        for (int tableIdx = 0; tableIdx < tableList.size(); tableIdx++) {
            Node tableNode = tableList.get(tableIdx);
            String tableNodeName = tableNode.getNodeName();

            // table
            for (int tableSetIdx = 0; tableSetIdx < tableSetList.size(); tableSetIdx++) {
                Table tableSet = tableSetList.get(tableSetIdx);
                String tableSetName = tableSet.getOptionMap().get("name");

                if (tableNodeName.equals(tableSetName) == true) {

                    Table tableObject = new Table();
                    tableObject.setOptionMap(tableSet.getOptionMap());

                    // record
                    ArrayList<Node> recordList = xmlParser.getNodeChildListFromNode(tableNode);
                    ArrayList<Record> recordSetList = tableSet.getRecordList();

                    for (int recordIdx = 0; recordIdx < recordList.size(); recordIdx++) {
                        Node recordNode = recordList.get(recordIdx);
                        String recordNodeName = recordNode.getNodeName();

                        for (int recordSetIdx = 0; recordSetIdx < recordSetList.size(); recordSetIdx++) {
                            Record recordSet = recordSetList.get(recordSetIdx);
                            String recordSetName = recordSet.getOptionMap().get("name");

                            if (recordNodeName.equals(recordSetName) == true) {

                                Record recordObject = new Record();
                                recordObject.setOptionMap(recordSet.getOptionMap());

                                // field
                                ArrayList<Node> fieldList = xmlParser.getNodeChildListFromNode(recordNode);
                                ArrayList<Field> fieldSetList = recordSet.getFieldList();

                                for (int fieldIdx = 0; fieldIdx < fieldList.size(); fieldIdx++) {
                                    Node fieldNode = fieldList.get(fieldIdx);
                                    String fieldNodeName = fieldNode.getNodeName();

                                    for (int fieldSetIdx = 0; fieldSetIdx < fieldSetList.size(); fieldSetIdx++) {
                                        Field fieldSet = fieldSetList.get(fieldSetIdx);
                                        String fieldSetName = fieldSet.getOptionMap().get("name");

                                        if (fieldNodeName.equals(fieldSetName) == true) {

                                            Field fieldObject = new Field();
                                            fieldObject.setValue(xmlParser.getNodeTextFromNode(fieldNode).getBytes());
                                            fieldObject.setOptionMap(fieldSet.getOptionMap());

                                            recordObject.getFieldList().add(fieldObject);
                                        }
                                    }
                                }

                                tableObject.getRecordList().add(recordObject);
                            }
                        }
                    }
                    messageObject.getTableList().add(tableObject);
                }
            }
        }

        return messageObject;
    }


    public MessageObject convertJsonToObject(MessageSet messageSet, byte[] sourceContents) throws Exception {

        String contents = new String(sourceContents);
        MessageObject messageObject = new MessageObject();

        JsonParser jsonParser = new JsonParser();
        jsonParser.loadString(contents);

        // load Contents
        String rootPath = messageSet.getOptionMap().get("root_path");
        ArrayList<Table> tableSetList = messageSet.getTableList();

        // table
        for (int tableSetIdx = 0; tableSetIdx < tableSetList.size(); tableSetIdx++) {

            Table tableSet = tableSetList.get(tableSetIdx);
            String tablePath = rootPath + "/" + tableSet.getOptionMap().get("name");

            JsonElement tableElement = jsonParser.getJsonElementFromPath(tablePath);
            if (tableElement == null)
                continue;

            Table tableObject = new Table();
            tableObject.setOptionMap(tableSet.getOptionMap());

            // record
            ArrayList<Record> recordSetList = tableSet.getRecordList();
            for (int recordSetIdx = 0; recordSetIdx < recordSetList.size(); recordSetIdx++) {

                Record recordSet = recordSetList.get(recordSetIdx);
                String recordSetName = recordSet.getOptionMap().get("name");
                String recordPath = tablePath + "/" + recordSetName;

                JsonElement recordElement = jsonParser.getJsonElementFromPath(tablePath);
                if (recordElement == null)
                    continue;

                Record recordObject = new Record();
                recordObject.setOptionMap(recordSet.getOptionMap());
                JsonObject recordJsonObject = recordElement.getAsJsonObject();

                // field
                JsonArray fieldJsonArray = recordJsonObject.getAsJsonArray(recordSetName);
                ArrayList<Field> fieldSetList = recordSet.getFieldList();

                for (int fieldIdx = 0; fieldIdx < fieldJsonArray.size(); fieldIdx++) {
                    JsonObject fieldJsonObject = fieldJsonArray.get(fieldIdx).getAsJsonObject();

                    for (int fieldSetIdx = 0; fieldSetIdx < fieldSetList.size(); fieldSetIdx++) {
                        Field fieldSet = fieldSetList.get(fieldSetIdx);
                        String fieldSetName = fieldSet.getOptionMap().get("name");

                        JsonElement fieldElement = fieldJsonObject.get(fieldSetName);
                        if (fieldElement == null)
                            continue;

                        Field fieldObject = new Field();
                        fieldObject.setValue(fieldElement.getAsString().getBytes());
                        fieldObject.setOptionMap(fieldSet.getOptionMap());

                        recordObject.getFieldList().add(fieldObject);

                    }
                }
                tableObject.getRecordList().add(recordObject);
            }
            messageObject.getTableList().add(tableObject);
        }
        return messageObject;
    }
}