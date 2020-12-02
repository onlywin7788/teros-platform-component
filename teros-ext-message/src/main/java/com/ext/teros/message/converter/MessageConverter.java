package com.ext.teros.message.converter;

import com.ext.teros.message.converter.core.JsonConverter;
import com.ext.teros.message.converter.core.XmlConverter;
import com.ext.teros.message.object.MessageObject;
import com.ext.teros.message.set.MessageSet;

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

    XmlConverter xmlConverter = null;
    JsonConverter jsonConverter = null;

    public MessageConverter() {
        xmlConverter = new XmlConverter();
        jsonConverter = new JsonConverter();
    }


    public MessageObject convertToObject(int convertType, MessageSet messageSet, byte[] sourceContents) throws Exception {

        MessageObject messageObject = null;

        // to oboject
        if (convertType == XML_TO_OBJECT) {
            messageObject = xmlConverter.convertXmlToObject(messageSet, sourceContents);
        }
        if (convertType == JSON_TO_OBJECT) {
            messageObject = jsonConverter.convertJsonToObject(messageSet, sourceContents);
        }

        return messageObject;
    }

    public byte[] convertFromObject(int convertType, MessageObject messageObject) throws Exception {

        byte[] returnByte = null;

        // from object
        if (convertType == OBJECT_TO_XML) {
            returnByte = xmlConverter.convertObjectToXml(messageObject);
        }
        if (convertType == OBJECT_TO_JSON) {
            returnByte = jsonConverter.convertObjectToJson(messageObject);
        }

        return returnByte;
    }
}