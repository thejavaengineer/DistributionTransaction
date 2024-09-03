package com.bank.saga.chronography;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class SagaManager {
    private final SagaChronography chronography;
    private final List<Service> services;

    public SagaManager(SagaChronography chronography, List<Service> services) {
        this.chronography = chronography;
        this.services = services;
    }

    public void executeSaga(String sagaId) throws InterruptedException, ExecutionException {
        chronography.registerSaga(sagaId, "Saga");
        for (Service service : services) {
            CompletableFuture<Void> future = service.execute();
            chronography.updateSagaStatus(sagaId, service.getClass().getSimpleName(), SagaChronography.SagaStatus.IN_PROGRESS);
            future.thenAccept(v -> {
                chronography.updateSagaStatus(sagaId, service.getClass().getSimpleName(), SagaChronography.SagaStatus.COMPLETED);
            }).exceptionally(ex -> {
                chronography.updateSagaStatus(sagaId, service.getClass().getSimpleName(), SagaChronography.SagaStatus.FAILED);
                return null;
            });
        }
    }

    public void compensateSaga(String sagaId) throws InterruptedException, ExecutionException {
        for (int i = services.size() - 1; i >= 0; i--) {
            Service service = services.get(i);
            CompletableFuture<Void> future = service.compensate();
            chronography.updateSagaStatus(sagaId, service.getClass().getSimpleName(), SagaChronography.SagaStatus.ABORTED);
            future.exceptionally(ex -> {
                // Log error and continue compensation
                System.out.println("Error during compensation: " + ex.getMessage());
                return null;
            });
        }
    }
}
