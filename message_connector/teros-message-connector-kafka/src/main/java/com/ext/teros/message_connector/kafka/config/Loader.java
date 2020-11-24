package com.ext.teros.message_connector.kafka.config;

import com.ext.teros.message_connector.kafka.common.parser.JsonParser;
import com.ext.teros.message_connector.kafka.common.parser.XmlParser;
import com.ext.teros.message_connector.kafka.config.model.Connection;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Setter
@Getter
public class Loader {

    private Connection connection;
    private JsonParser jsonParser;
    private XmlParser xmlParser;

    public Loader() {
        connection = new Connection();
        jsonParser = new JsonParser();
        xmlParser = new XmlParser();
    }

    public void loadConfig(String configPath) throws Exception {

        String nodeComponentPath = "/node/properties/param";

        try {
            xmlParser.loadFile(configPath);
            NodeList nodeList = xmlParser.getNodeList(nodeComponentPath);
            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);
                String paramName = xmlParser.getNodeAttrFromNode(node, "name");
                String paramValue = xmlParser.getNodeAttrFromNode(node, "value");

                if (paramName.equals("connection.host") == true) {
                    connection.setHost(paramValue);
                }
                if (paramName.equals("connection.port") == true) {
                    connection.setPort(paramValue);
                }
                if (paramName.equals("connection.topic") == true) {
                    connection.setTopic(paramValue);
                }
            }

        } catch (Exception e) {
            throw e;
        }
    }
}
