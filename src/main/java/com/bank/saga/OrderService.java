package com.bank.saga;

import java.util.concurrent.CompletableFuture;

class OrderService implements Service {
    @Override
    public CompletableFuture<Void> execute() {
        System.out.println("OrderService: Placing order");
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000); // Simulating order processing time
                System.out.println("OrderService: Order placed successfully");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public CompletableFuture<Void> compensate() {
        System.out.println("OrderService: Cancelling order");
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000); // Simulating cancellation time
                System.out.println("OrderService: Order cancelled successfully");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
