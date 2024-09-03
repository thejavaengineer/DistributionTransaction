package com.bank.saga;

import java.util.concurrent.CompletableFuture;

class InventoryService implements Service {
    @Override
    public CompletableFuture<Void> execute() {
        System.out.println("InventoryService: Reducing inventory");
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000); // Simulating inventory update time
                System.out.println("InventoryService: Inventory updated successfully");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public CompletableFuture<Void> compensate() {
        System.out.println("InventoryService: Restoring inventory");
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000); // Simulating restoration time
                System.out.println("InventoryService: Inventory restored successfully");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
