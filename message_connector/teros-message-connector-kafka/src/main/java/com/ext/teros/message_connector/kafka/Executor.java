package com.ext.teros.message_connector.kafka;

import com.ext.teros.message_connector.kafka.common.config.NodeProperties;
import com.ext.teros.message_connector.kafka.config.NodeConfig;
import com.ext.teros.message_connector.kafka.information.ProgramInformation;
import com.ext.teros.message_connector.spec.MessageConnectorSpec;
import com.teros.ext.common.parser.XmlParser;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Executor implements MessageConnectorSpec {

    XmlParser xmlParser = null;
    NodeProperties nodeProperties = null;
    NodeConfig nodeConfig = null;

    private Properties properties = null;
    private Producer kafkaProducer = null;
    private String message = "";

    byte[] rawMessage = null;
    int messageFormatFlag = 0;

    // extra
    private ProgramInformation programInformation;

    public Executor() {
        properties = new Properties();
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
        tempValue = nodeProperties.getProperty(xmlParser, "connection.host");
        nodeConfig.setConnectionHost(tempValue);

        tempValue = nodeProperties.getProperty(xmlParser, "connection.port");
        nodeConfig.setConnectionPort(tempValue);

        tempValue = nodeProperties.getProperty(xmlParser, "connection.topic");
        nodeConfig.setConnectionTopic(tempValue);

        // messageSet
        tempValue = nodeProperties.getProperty(xmlParser, "message.contents-format");
        nodeConfig.setMessageFormat(tempValue);
    }

    @Override
    public void initialize() throws Exception {

        // set kafka proerties
        String connectInfo = nodeConfig.getConnectionHost() + ":" + nodeConfig.getConnectionPort();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, connectInfo);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProducer = new KafkaProducer<>(properties);
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

    }

    @Override
    public void postInput() throws Exception {

    }

    @Override
    public void preOutput() throws Exception {
    }

    @Override
    public void output() throws Exception {

        String contents = new String(this.rawMessage);

        ProducerRecord<String, String> record = new ProducerRecord<>(nodeConfig.getConnectionTopic(), contents);
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                System.out.println("kafka data send failed.");
            }
        });
        kafkaProducer.flush();

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
        return null;
    }

    @Override
    public Object getOutputData() throws Exception {
        return null;
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
