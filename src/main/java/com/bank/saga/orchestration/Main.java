package com.bank.saga.orchestration;

// Usage
public class Main {
    public static void main(String[] args) throws InterruptedException {
        SagaOrchestrator orchestrator = new SagaOrchestrator(10); // Max 10 concurrent sagas

        // Register services
        orchestrator.registerService("order", new SagaOrchestrator.ServiceInfo("OrderService", s -> new OrderService().execute(s), (s, e) -> new OrderService().compensate(s)));
        orchestrator.registerService("inventory", new SagaOrchestrator.ServiceInfo("InventoryService", s -> new InventoryService().execute(s), (s, e) -> new InventoryService().compensate(s)));
        orchestrator.registerService("payment", new SagaOrchestrator.ServiceInfo("PaymentService", s -> new PaymentService().execute(s), (s, e) -> new PaymentService().compensate(s)));

        // Start a saga
        String sagaId = "SAGA-001";
        orchestrator.orchestrateSaga(sagaId, "order").join();

        // Shutdown the orchestrator
        orchestrator.shutdown();
    }
}
