package com.ext.teros.message_connector.rest;

import com.ext.teros.message.converter.MessageConverter;
import com.ext.teros.message.loader.MessageSetLoader;
import com.ext.teros.message.object.MessageObject;
import com.ext.teros.message.set.MessageSet;
import com.ext.teros.message_connector.rest.common.config.NodeProperties;
import com.ext.teros.message_connector.rest.common.parser.XmlParser;
import com.ext.teros.message_connector.rest.config.NodeConfig;
import com.ext.teros.message_connector.rest.information.ProgramInformation;
import com.ext.teros.message_connector.spec.MessageConnectorSpec;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Properties;

public class Executor implements MessageConnectorSpec {

    XmlParser xmlParser = null;
    NodeProperties nodeProperties = null;
    NodeConfig nodeConfig = null;

    MessageSetLoader messageSetLoader = null;
    MessageObject messageObject = null;
    MessageConverter messageConverter = null;
    MessageSet messageSet = null;
    String rawMessage = "";


    //extra
    private ProgramInformation programInformation;

    public Executor() {
        programInformation = new ProgramInformation();

        xmlParser = new XmlParser();
        nodeProperties = new NodeProperties();
        nodeConfig = new NodeConfig();

        messageConverter = new MessageConverter();
        messageSetLoader = new MessageSetLoader();
    }

    @Override
    public void loadConfig(String configPath) throws Exception {
        String tempValue = "";

        // load properties
        xmlParser.loadFile(configPath);
        tempValue = nodeProperties.getProperty(xmlParser, "message-set.contents-format");
        nodeConfig.setMessageSetFormat(tempValue);

        tempValue = nodeProperties.getProperty(xmlParser, "message-set.file-name");
        tempValue = FilenameUtils.getFullPathNoEndSeparator(configPath)
                + File.separator + "message-set" + File.separator + tempValue;
        nodeConfig.setMessageSetPath(tempValue);

        // load messageSet
        messageSet = messageSetLoader.getMessageSet(nodeConfig.getMessageSetPath());
    }

    @Override
    public void initialize() throws Exception {

    }

    @Override
    public void connect() throws Exception {

    }

    @Override
    public void open() throws Exception {

    }

    @Override
    public void preInput() throws Exception {

    }

    @Override
    public void input() throws Exception {

        String messageFormat = nodeConfig.getMessageSetFormat();
        int messageFormatFlag = 0;

        if (messageFormat.equals("xml"))
            messageFormatFlag = messageConverter.XML_TO_OBJECT;
        if (messageFormat.equals("json"))
            messageFormatFlag = messageConverter.JSON_TO_OBJECT;
        if (messageFormat.equals("csv"))
            messageFormatFlag = messageConverter.CSV_TO_OBJECT;
        if (messageFormat.equals("fixed"))
            messageFormatFlag = messageConverter.FIXED_TO_OBJECT;

        this.messageObject = messageConverter.convertToObject(messageFormatFlag, this.messageSet
                , this.rawMessage.getBytes());
    }
    @Override
    public void postInput() throws Exception {

    }

    @Override
    public void preOutput() throws Exception {

    }

    @Override
    public void output() throws Exception {
    }

    @Override
    public void postOutput() throws Exception {

    }

    @Override
    public void commit() throws Exception {

    }

    @Override
    public void rollback() throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void disconnect() throws Exception {

    }

    @Override
    public void uninitialize() throws Exception {

    }

    @Override
    public Object getData() throws Exception {
        return this.messageObject;
    }

    @Override
    public void setData(Object object) throws Exception {
        this.rawMessage = (String) object;
    }

    @Override
    public String getConnectorType() throws Exception {
        return programInformation.getType();
    }

    @Override
    public String getConnectorVersion() throws Exception {
        return programInformation.getVersion();
    }
}
