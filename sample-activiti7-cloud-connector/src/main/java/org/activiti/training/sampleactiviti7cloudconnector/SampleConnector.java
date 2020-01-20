package org.activiti.training.sampleactiviti7cloudconnector;

import org.activiti.cloud.api.process.model.IntegrationRequest;
import org.activiti.cloud.api.process.model.IntegrationResult;
import org.activiti.cloud.connectors.starter.channels.IntegrationResultSender;
import org.activiti.cloud.connectors.starter.configuration.ConnectorProperties;
import org.activiti.cloud.connectors.starter.model.IntegrationResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableBinding(SampleConnectorChannel.class)
public class SampleConnector {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private ConnectorProperties connectorProperties;

    private final IntegrationResultSender integrationResultSender;

    public SampleConnector(IntegrationResultSender integrationResultSender) {
        this.integrationResultSender = integrationResultSender;
    }

    @StreamListener(value = SampleConnectorChannel.SAMPLE_CONNECTOR_CONSUMER)
    public void execute(IntegrationRequest event) throws InterruptedException {
        System.out.println("Called cloud connector: " + appName);

        String processInstanceIdCalled = SampleConnector.class.getSimpleName() + " was called for instance" +
                event.getIntegrationContext().getProcessInstanceId();

        System.out.println("No high fi logic. Keep it simple!");

        Map<String, Object> results = new HashMap<>();
        results.put("processInstanceIdCalled", processInstanceIdCalled);
        Message<IntegrationResult> message = IntegrationResultBuilder.resultFor(event, connectorProperties)
                .withOutboundVariables(results)
                .buildMessage();

        integrationResultSender.send(message);
    }
}
