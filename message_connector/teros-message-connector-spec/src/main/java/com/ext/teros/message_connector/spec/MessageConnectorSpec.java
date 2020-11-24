package com.ext.teros.message_connector.spec;

public interface MessageConnectorSpec {

	public void loadConfig(String config) throws Exception;
	public void initialize() throws Exception;
	public void connect() throws Exception;
	public void open() throws Exception;
	public void input() throws Exception;
	public void output() throws Exception;
	public void commit() throws Exception;
	public void rollback() throws Exception;
	public void close() throws Exception;
	public void disconnect() throws Exception;
	public void uninitialize() throws Exception;
	public String getData() throws Exception;
	public void setData(String data) throws Exception;
	public String getConnectorType() throws Exception;
	public String getConnectorVersion() throws Exception;
}