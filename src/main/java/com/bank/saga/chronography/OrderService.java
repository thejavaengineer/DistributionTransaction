package com.bank.saga.chronography;

import java.util.concurrent.CompletableFuture;

class OrderService implements Service {
    @Override
    public CompletableFuture<Void> execute() {
        System.out.println("OrderService: Placing order");
        try {
            Thread.sleep(1000); // Simulating order processing time
            System.out.println("OrderService: Order placed successfully");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> compensate() {
        System.out.println("OrderService: Cancelling order");
        return CompletableFuture.completedFuture(null);
    }
}
