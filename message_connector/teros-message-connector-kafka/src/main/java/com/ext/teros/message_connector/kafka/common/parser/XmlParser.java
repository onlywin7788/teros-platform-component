package com.ext.teros.message_connector.kafka.common.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.*;

public class XmlParser {

    private Document document;

    public void loadFile(String path) throws Exception {

        InputStream inputStream = null;
        Reader reader = null;
        InputSource is = null;

        try {
            // file load and encoding utf-8
            File file = new File(path);
            inputStream = new FileInputStream(file);
            reader = new InputStreamReader(inputStream, "UTF-8");
            is = new InputSource(reader);
            is.setEncoding("UTF-8");

            // xml load
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(is);
        } catch (Exception e) {
            throw e;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    public Node getNode(String path) throws Exception {
        Node node = null;
        try {
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression xPathExpr = xpath.compile(path);

            NodeList nodeList = (NodeList) xPathExpr.evaluate(document, XPathConstants.NODESET);
            if (nodeList.getLength() > 0) {
                node = nodeList.item(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return node;
    }

    public NodeList getNodeList(String path) throws Exception {
        NodeList nodeList = null;
        try {
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression xPathExpr = xpath.compile(path);

            nodeList = (NodeList) xPathExpr.evaluate(document, XPathConstants.NODESET);

        } catch (Exception e) {
            throw e;
        }
        return nodeList;
    }

    public String getNodeText(String path) throws Exception {

        String returnString = "";
        try {
            returnString = getNode(path).getTextContent();
        } catch (Exception e) {
            throw e;
        }
        return returnString;
    }

    public String getNodeAttr(String path, String attrName) throws Exception {

        String returnString = "";
        try {
            Node node = getNode(path + "/@" + attrName);
            returnString = node.getNodeValue();

        } catch (Exception e) {
            throw e;
        }
        return returnString;
    }

    public String getNodeTextFromNode(Node node) throws Exception {

        String returnString = "";
        try {
            returnString = node.getTextContent();
        } catch (Exception e) {
            throw e;
        }
        return returnString;
    }

    public String getNodeAttrFromNode(Node node, String attrName) throws Exception {

        String returnString = "";
        try {
            //returnString = node.getAttributes().getNamedItem(attrName).getNodeValue();

            Node attr = node.getAttributes().getNamedItem(attrName);
            if (attr != null) {
                returnString = attr.getNodeValue();
            }

        } catch (Exception e) {
            throw e;
        }
        return returnString;
    }
}
