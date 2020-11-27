package com.ext.teros.message.common;

import com.ext.teros.message.common.parser.XmlParser;
import com.ext.teros.message.model.message.Record;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

public class JsonParserTest {

    @Test
    public void jsonTest() throws Exception {

        Gson gson = new Gson();
        JsonObject recordObject = new JsonObject();
        JsonObject fieldObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        fieldObject.addProperty("ID", "A001");
        fieldObject.addProperty("NAME", "KIM");
        fieldObject.addProperty("AGE", "30");
        jsonArray.add(fieldObject);

        recordObject.add("RECORD", jsonArray);


        fieldObject = new JsonObject();
        jsonArray = new JsonArray();
        fieldObject.addProperty("ID", "A002");
        fieldObject.addProperty("NAME", "KIM");
        fieldObject.addProperty("AGE", "30");
        jsonArray.add(fieldObject);


        recordObject.add("RECORD", jsonArray);


        JsonObject tableObject = new JsonObject();
        tableObject.add("TABLE", recordObject);


        fieldObject = new JsonObject();
        jsonArray = new JsonArray();

        JsonObject rootObject = new JsonObject();
        rootObject.add("DATA", null);


        String jsonOutput = gson.toJson(tableObject);

        System.out.println(jsonOutput);


    }

}
