# Read Me First
* Spring Boot Version: `2.1.10.RELEASE`
* Activiti Version: `7.1.0.M5`

# Getting Started

### Reference Documentation
The project is created using the Activiti 7 - [Activiti Core deep dive](https://hub.alfresco.com/t5/alfresco-process-services/activiti-7-deep-dive-series-using-the-core-libraries/ba-p/288484).
The only difference is the Activiti version.

#How to run the project
* The project includes a sample activiti bpmn diagram which consists of one User Task and One System Task.
* Package using `mvn clean package`
* Run using `java -jar target/activiti7-api-basic-events-0.0.1-SNAPSHOT.jar`
* You are good to go with the APIs.

#Users Configured
Username | Passowrd | Role 
--- | --- | --- 
dfluxuser|1234|ROLE_ACTIVITI_USER
testuser|1234|ROLE_ACTIVITI_USER
system|1234|ROLE_ACTIVITI_USER
admin|1234|ROLE_ACTIVITI_ADMIN 

#APIs exposed
API | Input | Activiti User Role Required
---- | ---- | ----
/process-definitions | NA | ROLE_ACTIVITI_USER
/start-process | `key` from result of `/process-definitions` | ROLE_ACTIVITI_USER
/my-tasks | NA | ROLE_ACTIVITI_USER
/complete-task | `taskId` from result of `/my-tasks` | ROLE_ACTIVITI_USER
/all-tasks | NA | ROLE_ACTIVITI_ADMIN 

##Event Listeners
* Event Listeners will help you generate notifications whenever your process / task status is changed.
* This project contains limited set of event and we simply log messages for illustration purpose.