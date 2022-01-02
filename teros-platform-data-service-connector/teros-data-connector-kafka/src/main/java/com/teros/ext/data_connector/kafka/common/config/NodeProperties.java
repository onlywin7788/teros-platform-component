package com.teros.ext.data_connector.kafka.common.config;

import com.teros.ext.common.parser.XmlParser;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class NodeProperties {

    public String getProperty(XmlParser xmlParser, String acquireKey) throws Exception {

        String propertyPath = "/node/properties/param";
        ArrayList<Node> nodeList = xmlParser.getNodeList(propertyPath);
        Node node = null;

        for (int i = 0; i < nodeList.size(); i++) {
            node = nodeList.get(i);
            String nodeKey = xmlParser.getNodeAttrFromNode(node, "key");

            if (nodeKey.equals(acquireKey) == true) {
                break;
            }
        }

        if(node == null)
            return "";
        return xmlParser.getNodeAttrFromNode(node, "value");
    }

}
