package org.activiti.training.activiti7apibasicevents.service;

import org.activiti.api.process.model.IntegrationContext;
import org.activiti.api.process.runtime.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "serviceTaskImpl")
public class ServiceTask1Connector implements Connector {
    private Logger logger = LoggerFactory.getLogger(ServiceTask1Connector.class);

    @Override
    public IntegrationContext apply(IntegrationContext integrationContext) {
        logger.info("Some Service Task logic....[processInstanceId: " + integrationContext.getProcessInstanceId() + "]");

        return integrationContext;
    }
}
