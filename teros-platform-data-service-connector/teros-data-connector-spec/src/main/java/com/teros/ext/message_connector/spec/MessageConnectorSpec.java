package com.teros.ext.message_connector.spec;

public interface MessageConnectorSpec {

	public void loadConfig(String configPath) throws Exception;
	public void initialize() throws Exception;
	public void connect() throws Exception;
	public void open() throws Exception;
	public void preInput() throws Exception;
	public void input() throws Exception;
	public void postInput() throws Exception;
	public void preOutput() throws Exception;
	public void output() throws Exception;
	public void postOutput() throws Exception;
	public void commit() throws Exception;
	public void rollback() throws Exception;
	public void close() throws Exception;
	public void disconnect() throws Exception;
	public void uninitialize() throws Exception;
	public void setInputData(Object object) throws Exception;
	public void setOutputData(Object object) throws Exception;
	public Object getInputData() throws Exception;
	public Object getOutputData() throws Exception;
	public String getMessageFormat() throws Exception;
	public String getConnectorType() throws Exception;
	public String getConnectorVersion() throws Exception;
}