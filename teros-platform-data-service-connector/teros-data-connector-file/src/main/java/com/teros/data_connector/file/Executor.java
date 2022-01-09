package com.teros.data_connector.file;

import com.teros.data_connector.file.common.config.NodeProperties;
import com.teros.data_connector.file.config.NodeConfig;
import com.teros.data_connector.file.information.ProgramInformation;
import com.teros.ext.common.parser.XmlParser;
import com.teros.ext.message_connector.spec.MessageConnectorSpec;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class Executor implements MessageConnectorSpec {

    XmlParser xmlParser = null;
    NodeProperties nodeProperties = null;
    NodeConfig nodeConfig = null;

    byte[] rawMessage = null;
    int messageFormatFlag = 0;

    //extra
    private ProgramInformation programInformation;

    public Executor() {
        programInformation = new ProgramInformation();

        xmlParser = new XmlParser();
        nodeProperties = new NodeProperties();
        nodeConfig = new NodeConfig();
    }


    @Override
    public void loadConfig(String configPath) throws Exception {
        String tempValue = "";

        // load properties
        xmlParser.loadFile(configPath);

        // connection
        tempValue = nodeProperties.getProperty(xmlParser, "file.read.path");
        nodeConfig.setFileReadPath(tempValue);

        tempValue = nodeProperties.getProperty(xmlParser, "file.read.rename");
        nodeConfig.setFileReadRename(tempValue);

        tempValue = nodeProperties.getProperty(xmlParser, "file.read.timeout");
        nodeConfig.setFileReadTimeout(tempValue);

        tempValue = nodeProperties.getProperty(xmlParser, "message.format");
        nodeConfig.setMessageFormat(tempValue);
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

        try {
            String filePath = nodeConfig.getFileReadPath();
            String fileReName = nodeConfig.getFileReadRename();

            int fileReadTimeout = Integer.parseInt(nodeConfig.getFileReadTimeout());

            while(true) {

                File f = new File(filePath);
                if (f.exists()) {

                    String readString = Files.readString(Paths.get(filePath));
                    this.setInputData(readString.getBytes(StandardCharsets.UTF_8));
                    Files.move(Paths.get(filePath), Paths.get(fileReName), StandardCopyOption.ATOMIC_MOVE);

                    break;
                }

                Thread.sleep(fileReadTimeout);
            }
        }
        catch(Exception ex)
        {
            throw ex;
        }
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
    public void setInputData(Object object) throws Exception {
        this.rawMessage = (byte[]) object;
    }

    @Override
    public void setOutputData(Object object) throws Exception {
        this.rawMessage = (byte[]) object;
    }

    @Override
    public Object getInputData() throws Exception {
        return this.rawMessage;
    }

    @Override
    public Object getOutputData() throws Exception {
        return this.rawMessage;
    }

    @Override
    public String getMessageFormat() throws Exception {
        return nodeConfig.getMessageFormat();
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
