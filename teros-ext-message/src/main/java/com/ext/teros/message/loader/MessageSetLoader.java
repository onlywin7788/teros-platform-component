package com.ext.teros.message.loader;

import com.ext.teros.message.common.parser.XmlParser;
import com.ext.teros.message.model.message.Field;
import com.ext.teros.message.model.message.Record;
import com.ext.teros.message.model.message.Table;
import com.ext.teros.message.set.MessageSet;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class MessageSetLoader {


    public MessageSet getMessageSet(String filePath) throws Exception {

        MessageSet messageSet = new MessageSet();
        messageSet = parseMessageSet(messageSet, filePath);

        return messageSet;
    }

    public MessageSet parseMessageSet(MessageSet messageSet, String filePath) throws Exception {

        ConcurrentHashMap<String, String> attrMap = null;

        XmlParser xmlParser = new XmlParser();
        xmlParser.loadFile(filePath);

        Node node = xmlParser.getNode("/message");
        attrMap = xmlParser.getNodeAttributesFromNode(node);
        messageSet.setOptionMap(attrMap);

        messageSet = parseTableSchema(messageSet, xmlParser);

        return messageSet;
    }

    public MessageSet parseTableSchema(MessageSet messageSet, XmlParser xmlParser) throws Exception {

        ConcurrentHashMap<String, String> tableAttrMap;
        ConcurrentHashMap<String, String> recordAttrMap;
        ConcurrentHashMap<String, String> fieldAttrMap;

        Node node = xmlParser.getNode("/message");
        ArrayList<Node> tableList = xmlParser.getNodeChildListFromNode(node);

        // table list
        for (int tableIdx = 0; tableIdx < tableList.size(); tableIdx++) {

            Table table = new Table();

            Node tableNode = tableList.get(tableIdx);
            tableAttrMap = xmlParser.getNodeAttributesFromNode(tableNode);
            table.setOptionMap(tableAttrMap);

            ArrayList<Node> recordList = xmlParser.getNodeChildListFromNode(tableNode);

            // record list
            for (int recordIdx = 0; recordIdx < recordList.size(); recordIdx++) {

                Record record = new Record();

                Node recordNode = recordList.get(recordIdx);
                recordAttrMap = xmlParser.getNodeAttributesFromNode(recordNode);
                record.setOptionMap(recordAttrMap);

                ArrayList<Node> fieldList = xmlParser.getNodeChildListFromNode(recordNode);

                // field list
                for (int fieldIdx = 0; fieldIdx < fieldList.size(); fieldIdx++) {

                    Field field = new Field();

                    Node fieldNode = fieldList.get(fieldIdx);
                    fieldAttrMap = xmlParser.getNodeAttributesFromNode(fieldNode);
                    field.setOptionMap(fieldAttrMap);

                    record.getFieldList().add(field);
                }
                table.getRecordList().add(record);
            }
            messageSet.getTableList().add(table);
        }
        return messageSet;
    }
}
