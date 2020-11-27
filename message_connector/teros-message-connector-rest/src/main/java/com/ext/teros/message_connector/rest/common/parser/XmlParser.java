package com.ext.teros.message_connector.rest.common.parser;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

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

    public void loadString(String contents) throws Exception {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new ByteArrayInputStream(contents.getBytes()));

        } catch (Exception e) {
            throw e;
        }
    }

    public void createXmlDocument() throws Exception {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
            document.setXmlStandalone(true);

        } catch (Exception e) {
            throw e;
        }
    }

    public String getDocumentString() throws Exception {

        StringWriter stringWriter = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    public Element createElement(Element acquireElement, String nodeName, String nodeText) {

        Element newElement = null;

        try {
            newElement = document.createElement(nodeName);
            if (nodeText != null) {
                if (nodeText.length() > 0)
                    newElement.appendChild(document.createTextNode(nodeText));
            }

            if (acquireElement == null)
                document.appendChild(newElement);
            else
                acquireElement.appendChild(newElement);

        } catch (Exception e) {
            throw e;
        }

        return newElement;
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

    public ArrayList<Node> getNodeList(String path) throws Exception {
        NodeList nodeList = null;
        ArrayList<Node> arrayNodeList = new ArrayList<Node>();
        try {
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression xPathExpr = xpath.compile(path);
            nodeList = (NodeList) xPathExpr.evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.TEXT_NODE)
                    continue;
                arrayNodeList.add(node);
            }

        } catch (Exception e) {
            throw e;
        }
        return arrayNodeList;
    }

    public ArrayList<Node> getNodeChildListFromNode(Node acquireNode) throws Exception {
        NodeList childNodeList = null;
        ArrayList<Node> arrayNodeList = new ArrayList<Node>();

        try {
            childNodeList = acquireNode.getChildNodes();

            // record list
            for (int i = 0; i < childNodeList.getLength(); i++) {

                Node node = childNodeList.item(i);
                if (node.getNodeType() == Node.TEXT_NODE)
                    continue;

                arrayNodeList.add(node);
            }
        } catch (Exception e) {
            throw e;
        }
        return arrayNodeList;
    }

    public String getNodeText(String path) throws Exception {

        String returnString = "";
        try {
            returnString = getNode(path).getTextContent();
        } catch (Exception e) {
            throw e;
        }

        if (returnString == null)
            returnString = "";

        return returnString;
    }

    public ConcurrentHashMap<String, String> getNodeAttributes(String path) throws Exception {

        String returnString = "";
        ConcurrentHashMap<String, String> attrMap = new ConcurrentHashMap<String, String>();

        try {
            Node node = getNode(path);
            NamedNodeMap namedNodeMap = node.getAttributes();

            for (int i = 0; i < namedNodeMap.getLength(); i++) {
                Attr attr = (Attr) namedNodeMap.item(i);
                String attrName = attr.getNodeName();
                String attrValue = attr.getNodeValue();

                attrMap.put(attrName, attrValue);
            }

        } catch (Exception e) {
            throw e;
        }
        return attrMap;
    }

    public ConcurrentHashMap<String, String> getNodeAttributesFromNode(Node node) throws Exception {

        String returnString = "";
        ConcurrentHashMap<String, String> attrMap = new ConcurrentHashMap<String, String>();

        try {
            NamedNodeMap namedNodeMap = node.getAttributes();

            for (int i = 0; i < namedNodeMap.getLength(); i++) {
                Attr attr = (Attr) namedNodeMap.item(i);
                String attrName = attr.getNodeName();
                String attrValue = attr.getNodeValue();

                attrMap.put(attrName, attrValue);
            }

        } catch (Exception e) {
            throw e;
        }
        return attrMap;
    }

    public String getNodeAttr(String path, String attrName) throws Exception {

        String returnString = "";
        try {
            Node node = getNode(path + "/@" + attrName);
            returnString = node.getNodeValue();

        } catch (Exception e) {
            throw e;
        }

        if (returnString == null)
            returnString = "";

        return returnString;
    }

    public String getNodeTextFromNode(Node node) throws Exception {

        String returnString = "";
        try {
            returnString = node.getTextContent();
        } catch (Exception e) {
            throw e;
        }

        if (returnString == null)
            returnString = "";

        return returnString;
    }

    public String getNodeAttrFromNode(Node node, String attrName) throws Exception {

        String returnString = "";
        try {
            Node attr = node.getAttributes().getNamedItem(attrName);
            if (attr != null) {
                returnString = attr.getNodeValue();
            }

        } catch (Exception e) {
            throw e;
        }

        if (returnString == null)
            returnString = "";

        return returnString;
    }
}
