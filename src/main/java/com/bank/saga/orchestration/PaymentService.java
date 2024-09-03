package com.bank.saga.orchestration;

import java.util.concurrent.CompletableFuture;

class PaymentService implements Service {
    @Override
    public CompletableFuture<Void> execute(String sagaId) {
        System.out.println("PaymentService: Processing payment for " + sagaId);
        // Simulate payment processing
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void compensate(String sagaId) {
        System.out.println("PaymentService: Refunding payment for " + sagaId);
        // Simulate refund
    }
}
