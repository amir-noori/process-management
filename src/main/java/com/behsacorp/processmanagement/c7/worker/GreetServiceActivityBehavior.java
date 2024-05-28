package com.behsacorp.processmanagement.c7.worker;

import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;

public class GreetServiceActivityBehavior implements ActivityBehavior {

    @Override
    public void execute(ActivityExecution execution) throws Exception {
        System.out.println("greet service called.");
        Object orderId = execution.getVariable("orderId");
        System.out.println("GreetServiceDelegate orderId: " + orderId);
        if (orderId != null && ((String) orderId).equals("1000")) {
            System.out.println("you can pass through...");
        } else {
            throw new RuntimeException("you shall not pass!");
        }
    }
}
