Author: Michael Rossi
Date: April 2013

camel-poc
=========

This is a poc created to demonstrate the Apache Camel integration framework with some basic routes using ActiveMQ.

This is a maven project so use the following commands to do stuff:

[Build the Project] 
mvn compile

[Running the Project]
The application can be executed within either JBoss EAP (tested w/ 6.1) or within the standalone Camel container. Instructions for each are below.

1. Within the Camel container
   --------------------------
The easiest option. ActiveMQ is bundled so there are no additional steps required to start the broker. Connection to the 
broker is made over tcp port:61616

mvn camel:run

2. Within JBoss EAP 6.1 - example xml configuration files are in src/test/resources/eap
   --------------------
In order to run this poc within the EAP container an instance of ActiveMQ must be downloaded and deployed to EAP 6.1. The
instructions below use the standalone configuration of EAP 6.1.

a. Download Apache ActiveMQ RAR (v. 5.8.0): http://repo1.maven.org/maven2/org/apache/activemq/activemq-rar/
b. Create activemq directory (using rar filename) in /jboss-eap-6.0/standalone/deployments
c. Copy RAR file to /jboss-eap-6.0/standalone/deployments/activemq-rar-5.8.0.rar/
d. Run tar xvf activemq-rar-5.8.0.rar on the rar file in the new directory. On Windows, open with WinRAR and extract contents
e. Modify activemq-rar-5.8.0.rar/META-INF/ra.xml and update 'ServerUrl' config property so that AMQ runs within same vm as jboss. This is more efficient than connecting via tcp
f. Modify activemq-rar-5.8.0.rar/META-INF/ra.xml and update 'BrokerXmlConfig' config property so that AMQ reads broker-config.xml from the rar directory via Spring
[Configure embedded ActiveMQ --> broker-config.xml]
g. Modify activemq-rar-5.8.0.rar/broker-config.xml and add 'brokerName' attribute to the 'broker' node
h. Modify activemq-rar-5.8.0.rar/broker-config.xml and add 'broker' attribute to the 'transportConnector' node
i. Modify /jboss-eap-6.0/standalone/configuration/standalone.xml and add new ResourceAdapter for activemq within 'resource-adapters' section
j. Create activemq-rar-5.8.0.rar.dodeploy file in /jboss-eap-6.0/standalone/deployments directory. This will deploy AMQ in JBoss so that an embedded instance is started/stopped
with the application server
k. Start JBoss
l. Access the poc via service endpoint: http://localhost:8080/camel-dsljava-1.0-SNAPSHOT/genworthcredit/creditscore