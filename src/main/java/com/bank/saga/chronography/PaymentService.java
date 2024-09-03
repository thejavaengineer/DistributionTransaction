package com.bank.saga.chronography;

import java.util.concurrent.CompletableFuture;

class PaymentService implements Service {
    @Override
    public CompletableFuture<Void> execute() {
        System.out.println("PaymentService: Processing payment");
        try {
            Thread.sleep(1500); // Simulating payment processing time
            System.out.println("PaymentService: Payment processed successfully");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> compensate() {
        System.out.println("PaymentService: Refunding payment");
        return CompletableFuture.completedFuture(null);
    }
}
