package com.bank.saga.orchestration;

import java.util.concurrent.CompletableFuture;

// Sample services
class OrderService implements Service {
    @Override
    public CompletableFuture<Void> execute(String sagaId) {
        System.out.println("OrderService: Placing order for " + sagaId);
        // Simulate order placement
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void compensate(String sagaId) {
        System.out.println("OrderService: Cancelling order for " + sagaId);
        // Simulate order cancellation
    }
}
