package com.bank.saga.chronography;

import java.util.List;
import java.util.concurrent.*;

// Usage
public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SagaChronography chronography = new SagaChronography(600); // 10 minutes max age

        List<Service> services = List.of(
                new OrderService(),
                new InventoryService(),
                new PaymentService()
        );

        SagaManager manager = new SagaManager(chronography, services);

        String sagaId = "SAGA-001";
        System.out.println("Executing saga...");
        manager.executeSaga(sagaId);

        // Simulate failure after some time
        Thread.sleep(5000);

        System.out.println("\nCompensating saga...");
        manager.compensateSaga(sagaId);
    }
}
