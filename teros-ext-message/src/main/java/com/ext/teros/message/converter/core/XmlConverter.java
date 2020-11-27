package com.ext.teros.message.converter.core;

import com.ext.teros.message.common.parser.XmlParser;
import com.ext.teros.message.model.message.Field;
import com.ext.teros.message.model.message.Record;
import com.ext.teros.message.model.message.Table;
import com.ext.teros.message.object.MessageObject;
import com.ext.teros.message.set.MessageSet;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class XmlConverter {

    public MessageObject convertXmlToObject(MessageSet messageSet, byte[] sourceContents) throws Exception {

        MessageObject messageObject = new MessageObject();
        XmlParser xmlParser = new XmlParser();

        try {

            messageObject.setOptionMap(messageSet.getOptionMap());

            String contents = new String(sourceContents);
            xmlParser.loadString(contents);

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
        } catch (Exception e) {
            throw e;
        }

        return messageObject;
    }

    public byte[] convertObjectToXml(MessageObject messageObject) throws Exception {

        XmlParser xmlParser = new XmlParser();
        byte[] returnByte = null;

        Element rootElement = null;
        Element tableElement = null;
        Element recordElement = null;
        Element acquireElement = null;

        try {

            xmlParser.createXmlDocument();

            String rootPath = messageObject.getOptionMap().get("root_path");
            StringTokenizer rootPathToken = new StringTokenizer(rootPath, "/");

            int tokenIdx = 0;
            while (rootPathToken.hasMoreTokens()) {
                String token = rootPathToken.nextToken();
                if (token == null)
                    continue;
                if (token.length() == 0)
                    continue;

                if (tokenIdx > 0)
                    acquireElement = rootElement;

                rootElement = xmlParser.createElement(acquireElement, token, "");
                tokenIdx++;
            }

            // table
            ArrayList<Table> tableList = messageObject.getTableList();
            for (int tableIdx = 0; tableIdx < tableList.size(); tableIdx++) {

                Table table = tableList.get(tableIdx);
                String tableName = table.getOptionMap().get("name");
                tableElement = xmlParser.createElement(rootElement, tableName, "");

                // record
                ArrayList<Record> recordList = table.getRecordList();
                for (int recordIdx = 0; recordIdx < recordList.size(); recordIdx++) {
                    Record record = recordList.get(recordIdx);
                    String recordName = record.getOptionMap().get("name");
                    recordElement = xmlParser.createElement(tableElement, recordName, "");

                    // field
                    ArrayList<Field> fieldList = record.getFieldList();
                    for (int fieldIdx = 0; fieldIdx < fieldList.size(); fieldIdx++) {
                        Field field = fieldList.get(fieldIdx);
                        String fieldName = field.getOptionMap().get("name");
                        String fieldValue = new String(field.getValue());

                        xmlParser.createElement(recordElement, fieldName, fieldValue);
                    }
                }
            }

            String output = xmlParser.getDocumentString();
            returnByte = output.getBytes();

        } catch (Exception e) {
            throw e;
        }

        return returnByte;
    }
}
