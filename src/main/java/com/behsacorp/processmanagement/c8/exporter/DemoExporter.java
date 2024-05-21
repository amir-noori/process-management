package com.behsacorp.processmanagement.c8.exporter;

import io.camunda.zeebe.exporter.api.Exporter;
import io.camunda.zeebe.exporter.api.context.Context;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;

public class DemoExporter implements Exporter {

    private static final String EVENT_EXPORT_URL = "http://172.17.0.1:3000/zeebeEvent";
    private static final String MIME_TYPE = "application/json";

//    private Logger logger;

    Controller controller;

    @Override
    public void configure(Context context) throws Exception {
//        logger = context.getLogger();
    }

    @Override
    public void open(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void close() {
    }

    @Override
    public void export(Record record) {
        String recordJson = "";
        try {
            recordJson = record.toJson();
        } catch (Throwable e) {
            System.out.println("error in export: " + e.getMessage());
            recordJson = record.toString();
        }

        System.out.println("################################ Demo Exporter ################################");
        System.out.println(recordJson);
//
//        RecordType recordType = record.getRecordType();
//        ValueType valueType = record.getValueType();
//        StringBuilder sqlStringBuilder = new StringBuilder();
//        if (1 == 1) {
//            throw new RuntimeException("this is very wrong!");
//        }
//        try {
//            switch (recordType) {
//                case COMMAND:
//                    switch (valueType) {
//                        case PROCESS_INSTANCE_CREATION:
//                            sqlStringBuilder.append("insert into zeebe.process_log (processInstanceId, data) values ('")
//                                    .append(record.getKey())
//                                    .append("',")
//                                    .append("'")
//                                    .append(record.toJson())
//                                    .append("')");
//
//                            DatabaseUtil.execute(sqlStringBuilder.toString());
//                    }
//                    break;
//                case EVENT:
//                    switch (valueType) {
//                        case INCIDENT:
//                            sqlStringBuilder.append("insert into zeebe.process_error (processInstanceId, data) values ('")
//                                    .append(record.getKey())
//                                    .append("',")
//                                    .append("'")
//                                    .append(record.toJson())
//                                    .append("')");
//
//                            DatabaseUtil.execute(sqlStringBuilder.toString());
//                            break;
//                    }
//                    break;
//            }
//        } catch (SQLException e) {
//            System.out.println("something went wrong!");
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }

        /**
         * The following error will happen on zeebe start logger.debug is used
         *
         *
         *
         * 2024-05-08 18:04:10.124 [Broker-0] [zb-fs-workers-0] [Exporter-1] ERROR
         *       io.camunda.zeebe.broker.exporter - Actor 'Exporter-1' failed in phase STARTED with: java.lang.LinkageError: loader constraint violation: loader io.camunda.zeebe.util.jar.ExternalJarClassLoader @744db9fb wants to load interface org.slf4j.Logger. A different interface with the same name was previously loaded by 'app'. (org.slf4j.Logger is in unnamed module of loader 'app') .
         * java.lang.LinkageError: loader constraint violation: loader io.camunda.zeebe.util.jar.ExternalJarClassLoader @744db9fb wants to load interface org.slf4j.Logger. A different interface with the same name was previously loaded by 'app'. (org.slf4j.Logger is in unnamed module of loader 'app')
         *         at java.base/java.lang.ClassLoader.defineClass1(Native Method) ~[?:?]
         *         at java.base/java.lang.ClassLoader.defineClass(Unknown Source) ~[?:?]
         *         at java.base/java.security.SecureClassLoader.defineClass(Unknown Source) ~[?:?]
         *         at java.base/java.net.URLClassLoader.defineClass(Unknown Source) ~[?:?]
         *         at java.base/java.net.URLClassLoader$1.run(Unknown Source) ~[?:?]
         *         at java.base/java.net.URLClassLoader$1.run(Unknown Source) ~[?:?]
         *         at java.base/java.security.AccessController.doPrivileged(Unknown Source) ~[?:?]
         *         at java.base/java.net.URLClassLoader.findClass(Unknown Source) ~[?:?]
         *         at io.camunda.zeebe.util.jar.ExternalJarClassLoader.loadClass(ExternalJarClassLoader.java:54) ~[zeebe-util-8.5.0.jar:8.5.0]
         *         at io.zeebe.DemoExporter.export(DemoExporter.java:46) ~[zeebe-exporter-demo-1.0-SNAPSHOT.jar:?]
         *         at io.camunda.zeebe.broker.exporter.stream.ExporterContainer.lambda$export$5(ExporterContainer.java:202) ~[zeebe-broker-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.util.jar.ThreadContextUtil.runCheckedWithClassLoader(ThreadContextUtil.java:58) ~[zeebe-util-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.util.jar.ThreadContextUtil.runWithClassLoader(ThreadContextUtil.java:34) ~[zeebe-util-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.broker.exporter.stream.ExporterContainer.export(ExporterContainer.java:201) ~[zeebe-broker-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.broker.exporter.stream.ExporterContainer.exportRecord(ExporterContainer.java:179) ~[zeebe-broker-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.broker.exporter.stream.ExporterDirector$RecordExporter.export(ExporterDirector.java:570) ~[zeebe-broker-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.scheduler.retry.BackOffRetryStrategy.run(BackOffRetryStrategy.java:51) ~[zeebe-scheduler-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.scheduler.ActorJob.invoke(ActorJob.java:94) ~[zeebe-scheduler-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.scheduler.ActorJob.execute(ActorJob.java:47) [zeebe-scheduler-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.scheduler.ActorTask.execute(ActorTask.java:122) [zeebe-scheduler-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.scheduler.ActorThread.executeCurrentTask(ActorThread.java:130) [zeebe-scheduler-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.scheduler.ActorThread.doWork(ActorThread.java:108) [zeebe-scheduler-8.5.0.jar:8.5.0]
         *         at io.camunda.zeebe.scheduler.ActorThread.run(ActorThread.java:227) [zeebe-scheduler-8.5.0.jar:8.5.0]
         */
//        logger.debug(recordJson);
        System.out.println("###############################################################################");

//        try (final CloseableHttpClient client = HttpClients.createDefault()) {
//            final HttpPost httpPost = new HttpPost(EVENT_EXPORT_URL);
//            httpPost.setHeader("Content-Type", MIME_TYPE);
//            httpPost.setEntity(new StringEntity(recordJson));
//            client.execute(httpPost, new HttpClientResponseHandler<Object>() {
//                @Override
//                public Object handleResponse(ClassicHttpResponse classicHttpResponse) throws HttpException, IOException {
//                    System.out.println(classicHttpResponse);
//                    return "whatever...";
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
////            logger.error("error happened while exporting event: " + e.getMessage());
//        }

        this.controller.updateLastExportedRecordPosition(record.getPosition());
    }
}
