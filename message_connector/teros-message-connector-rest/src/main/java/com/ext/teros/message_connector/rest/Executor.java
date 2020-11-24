package com.ext.teros.message_connector.rest;

import com.ext.teros.message_connector.rest.information.ProgramInformation;
import com.ext.teros.message_connector.spec.MessageConnectorSpec;

import java.util.Properties;

public class Executor implements MessageConnectorSpec {

    String messageObject = "";

    //extra
    private ProgramInformation programInformation;

    public Executor() {
        programInformation = new ProgramInformation();
    }

    @Override
    public void loadConfig(String s) throws Exception {

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
    public void input() throws Exception {
    }

    @Override
    public void output() throws Exception {
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
    public String getData() throws Exception {
        return messageObject;
    }

    @Override
    public void setData(String s) throws Exception {
        this.messageObject = s;
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
