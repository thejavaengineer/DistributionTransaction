package com.bank.saga.orchestration;

import java.util.concurrent.CompletableFuture;

class InventoryService implements Service {
    @Override
    public CompletableFuture<Void> execute(String sagaId) {
        System.out.println("InventoryService: Reducing inventory for " + sagaId);
        // Simulate inventory reduction
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void compensate(String sagaId) {
        System.out.println("InventoryService: Restoring inventory for " + sagaId);
        // Simulate inventory restoration
    }
}
