package com.bank.saga;

import java.util.concurrent.CompletableFuture;

class PaymentService implements Service {
    @Override
    public CompletableFuture<Void> execute() {
        System.out.println("PaymentService: Processing payment");
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1500); // Simulating payment processing time
                System.out.println("PaymentService: Payment processed successfully");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public CompletableFuture<Void> compensate() {
        System.out.println("PaymentService: Refunding payment");
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1500); // Simulating refund time
                System.out.println("PaymentService: Refund processed successfully");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
