package com.bank.saga.chronography;

import java.util.concurrent.CompletableFuture;

class InventoryService implements Service {
    @Override
    public CompletableFuture<Void> execute() {
        System.out.println("InventoryService: Reducing inventory");
        try {
            Thread.sleep(2000); // Simulating inventory update time
            System.out.println("InventoryService: Inventory updated successfully");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> compensate() {
        System.out.println("InventoryService: Restoring inventory");
        return CompletableFuture.completedFuture(null);
    }
}
