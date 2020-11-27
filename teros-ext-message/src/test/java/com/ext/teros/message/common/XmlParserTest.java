package com.ext.teros.message.common;

import com.ext.teros.message.common.parser.XmlParser;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

public class XmlParserTest {

    @Test
    public void xmlTest() throws Exception{

        XmlParser xmlParser = new XmlParser();
        xmlParser.createXmlDocument();

        Element rootElement = xmlParser.createElement(null, "DATA", "");
        Element tableElement = xmlParser.createElement(rootElement, "TEST_TABLE", "");
        Element recordElement = xmlParser.createElement(tableElement, "RECORD", "");
        xmlParser.createElement(recordElement, "ID", "A001");
        xmlParser.createElement(recordElement, "NAME", "LEE");
        xmlParser.createElement(recordElement, "MESSAGE", "10");

        Element recordElement2 = xmlParser.createElement(tableElement, "RECORD", "");
        xmlParser.createElement(recordElement2, "ID", "A002");
        xmlParser.createElement(recordElement2, "NAME", "KIM");
        xmlParser.createElement(recordElement2, "MESSAGE", "20");


        String output = xmlParser.getDocumentString();
        System.out.println(output);
    }

}
