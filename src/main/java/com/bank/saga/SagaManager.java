package com.bank.saga;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class SagaManager {
    private final List<Service> services;

    public SagaManager(List<Service> services) {
        this.services = services;
    }

    public void executeSaga() throws InterruptedException, ExecutionException {
        for (Service service : services) {
            CompletableFuture<Void> future = service.execute();
            future.get(); // Wait for each service to complete
        }
    }

    public void compensateSaga() throws InterruptedException, ExecutionException {
        for (int i = services.size() - 1; i >= 0; i--) {
            CompletableFuture<Void> future = services.get(i).compensate();
            future.get(); // Wait for each service to complete
        }
    }
}
