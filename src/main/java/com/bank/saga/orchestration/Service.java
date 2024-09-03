package com.bank.saga.orchestration;

import java.util.concurrent.CompletableFuture;

interface Service {
    CompletableFuture<Void> execute(String sagaId);

    void compensate(String sagaId);
}
