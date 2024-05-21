package com.behsacorp.processmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.behsacorp.processmanagement.c7", "com.behsacorp.processmanagement.pm"})
public class App {
    public static void main(String[] args) {
        /*
        TODO
        in order to ignore the following exception "agrona.disable.bounds.checks" is set tot true:

        java.lang.IllegalArgumentException: offset=96 length=14548992 not valid for capacity=424
            at org.agrona.concurrent.UnsafeBuffer.boundsCheckWrap(UnsafeBuffer.java:2435) ~[agrona-1.16.0.jar:1.16.0]
            at org.agrona.concurrent.UnsafeBuffer.wrap(UnsafeBuffer.java:277) ~[agrona-1.16.0.jar:1.16.0]
            at io.camunda.zeebe.protocol.impl.record.RecordMetadata.wrap(RecordMetadata.java:92) ~[zeebe-protocol-impl-8.1.0-alpha4.jar:8.1.0-alpha4]
            at io.camunda.zeebe.logstreams.impl.log.LoggedEventImpl.readMetadata(LoggedEventImpl.java:112) ~[zeebe-logstreams-8.1.0-alpha4.jar:8.1.0-alpha4]
            at io.camunda.zeebe.streamprocessor.MetadataEventFilter.applies(MetadataEventFilter.java:24) ~[zeebe-workflow-engine-8.1.0-alpha4.jar:8.1.0-alpha4]
            at io.camunda.zeebe.streamprocessor.ProcessingStateMachine.tryToReadNextRecord(ProcessingStateMachine.java:214) ~[zeebe-workflow-engine-8.1.0-alpha4.jar:8.1.0-alpha4]
            at io.camunda.zeebe.streamprocessor.ProcessingStateMachine.readNextRecord(ProcessingStateMachine.java:191) ~[zeebe-workflow-engine-8.1.0-alpha4.jar:8.1.0-alpha4]
            at io.camunda.zeebe.scheduler.ActorJob.invoke(ActorJob.java:92) ~[zeebe-scheduler-8.1.0-alpha4.jar:8.1.0-alpha4]
            at io.camunda.zeebe.scheduler.ActorJob.execute(ActorJob.java:45) ~[zeebe-scheduler-8.1.0-alpha4.jar:8.1.0-alpha4]
            at io.camunda.zeebe.scheduler.ActorTask.execute(ActorTask.java:119) ~[zeebe-scheduler-8.1.0-alpha4.jar:8.1.0-alpha4]
            at io.camunda.zeebe.scheduler.ActorThread.executeCurrentTask(ActorThread.java:106) ~[zeebe-scheduler-8.1.0-alpha4.jar:8.1.0-alpha4]
            at io.camunda.zeebe.scheduler.ActorThread.doWork(ActorThread.java:87) ~[zeebe-scheduler-8.1.0-alpha4.jar:8.1.0-alpha4]
            at io.camunda.zeebe.scheduler.ActorThread.run(ActorThread.java:198) ~[zeebe-scheduler-8.1.0-alpha4.jar:8.1.0-alpha4]
         */
        System.setProperty("agrona.disable.bounds.checks", "true");
        SpringApplication.run(App.class, args);
    }
}
