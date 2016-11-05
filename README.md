# Motivation
I want to learn and understand how the Enable\* annotations work in Spring. So I want to build my own EnableMonitorServer, EnableMonitorClient, EnableMonitorDashboard annotations, which will add specific implementation to your applications.

# Spring Cloud Monitor
This is a monitoring component for spring-cloud applications. This component adds a possibility to monitor the status of the microservices based on spring-cloud.

# Components
- monitor-server-starter
- monitor-client-starter
- monitor-dashboard

# Examples
- service-registry
- monitor-server, import as dependency the monitor-server-starter, monitor-client-starter, built monitor-dashboard in /static
- monitor-client, import as dependency the monitor-client-starter

# Running exmaples
    // Build dependencies
    cd monitor-server-starter
    mvn clean install
  
    cd monitor-client-starter
    mvn clean install
  
    // Build and start examples
    cd examples/services-registry
    mvn clean instal spring-boot:run
  
    cd examples/monitor-client
    mvn clean instal spring-boot:run
  
    cd examples/monitor-server
    mvn clean instal spring-boot:run
    
now goto:  [http://localhost:8095](http://localhost:8095), and check the RAM Usage of your applications. 
Both monitor-server and monitor-client will report to monitor-server their status.
