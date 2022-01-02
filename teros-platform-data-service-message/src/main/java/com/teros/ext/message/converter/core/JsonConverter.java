package com.teros.ext.message.converter.core;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teros.ext.common.parser.JsonParser;
import com.teros.ext.message.model.message.Field;
import com.teros.ext.message.model.message.Record;
import com.teros.ext.message.model.message.Table;
import com.teros.ext.message.object.MessageObject;
import com.teros.ext.message.set.MessageSet;


import java.util.ArrayList;
import java.util.StringTokenizer;

public class JsonConverter {

    public MessageObject convertJsonToObject(MessageSet messageSet, byte[] sourceContents) throws Exception {

        MessageObject messageObject = new MessageObject();
        JsonParser jsonParser = new JsonParser();

        try {

            messageObject.setOptionMap(messageSet.getOptionMap());

            String contents = new String(sourceContents);
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

                    //Record recordObject = new Record();
                    //recordObject.setOptionMap(recordSet.getOptionMap());
                    JsonObject recordJsonObject = recordElement.getAsJsonObject();

                    // field
                    JsonArray fieldJsonArray = recordJsonObject.getAsJsonArray(recordSetName);
                    ArrayList<Field> fieldSetList = recordSet.getFieldList();

                    for (int fieldIdx = 0; fieldIdx < fieldJsonArray.size(); fieldIdx++) {

                        Record recordObject = new Record();
                        recordObject.setOptionMap(recordSet.getOptionMap());

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

                        tableObject.getRecordList().add(recordObject);
                    }

                }
                messageObject.getTableList().add(tableObject);
            }
        } catch (Exception e) {
            throw e;
        }

        return messageObject;
    }

    public byte[] convertObjectToJson(MessageObject messageObject) throws Exception {

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        byte[] returnByte = null;

        try {
            String rootPath = messageObject.getOptionMap().get("root_path");
            StringTokenizer rootPathToken = new StringTokenizer(rootPath, "/");
            ArrayList<String> rootTokenList = new ArrayList<String>();
            JsonObject jsonRootObject = null;

            while (rootPathToken.hasMoreTokens()) {
                String token = rootPathToken.nextToken();
                if (token == null)
                    continue;
                if (token.length() == 0)
                    continue;
                rootTokenList.add(token);
            }

            // table
            JsonObject tableJsonObject = new JsonObject();
            ArrayList<Table> tableList = messageObject.getTableList();
            for (int tableIdx = 0; tableIdx < tableList.size(); tableIdx++) {

                Table table = tableList.get(tableIdx);
                String tableName = table.getOptionMap().get("name");

                // record
                JsonObject recordJsonObject = new JsonObject();
                ArrayList<Record> recordList = table.getRecordList();
                JsonArray jsonFieldArray = new JsonArray();
                String saveRecordName = "";
                for (int recordIdx = 0; recordIdx < recordList.size(); recordIdx++) {

                    Record record = recordList.get(recordIdx);
                    String recordName = record.getOptionMap().get("name");
                    saveRecordName = recordName;

                    // field
                    ArrayList<Field> fieldList = record.getFieldList();
                    JsonObject recordFieldObject = new JsonObject();
                    for (int fieldIdx = 0; fieldIdx < fieldList.size(); fieldIdx++) {

                        Field field = fieldList.get(fieldIdx);
                        String fieldName = field.getOptionMap().get("name");
                        String fieldValue = new String(field.getValue());

                        recordFieldObject.addProperty(fieldName, fieldValue);
                    }
                    jsonFieldArray = jsonParser.addJsonArray(jsonFieldArray, recordFieldObject);
                }
                recordJsonObject.add(saveRecordName, jsonFieldArray);
                tableJsonObject.add(tableName, recordJsonObject);
            }

            JsonObject childJsonObject = tableJsonObject;

            if (rootTokenList.size() > 0) {

                for (int i = rootTokenList.size() - 1; i >= 0; i--) {
                    jsonRootObject = new JsonObject();
                    String token = rootTokenList.get(i);
                    childJsonObject = jsonParser.addJsonObject(jsonRootObject, token, childJsonObject);
                }

            } else {
                jsonRootObject = tableJsonObject;
            }

            String jsonOutput = gson.toJson(jsonRootObject);
            returnByte = jsonOutput.getBytes();

        } catch (Exception e) {
            throw e;
        }
        return returnByte;
    }
}
