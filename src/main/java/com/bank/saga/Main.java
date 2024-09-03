package com.bank.saga;

import java.util.List;
import java.util.concurrent.ExecutionException;

// Usage
public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Service> services = List.of(
                new OrderService(),
                new InventoryService(),
                new PaymentService()
        );

        SagaManager manager = new SagaManager(services);

        System.out.println("Executing saga...");
        manager.executeSaga();

        System.out.println("\nCompensating saga...");
        manager.compensateSaga();
    }
}
