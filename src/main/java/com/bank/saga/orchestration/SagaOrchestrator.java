package com.bank.saga.orchestration;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

class SagaOrchestrator {
    private final Map<String, ServiceInfo> serviceRegistry;
    private final ExecutorService executor;

    public SagaOrchestrator(int maxConcurrentSagas) {
        this.serviceRegistry = new ConcurrentHashMap<>();
        this.executor = Executors.newFixedThreadPool(maxConcurrentSagas);
    }

    public void registerService(String serviceName, ServiceInfo serviceInfo) {
        serviceRegistry.put(serviceName, serviceInfo);
    }

    public CompletableFuture<Void> orchestrateSaga(String sagaId, String initialService) throws InterruptedException {
        AtomicBoolean sagaCompleted = new AtomicBoolean(false);

        return CompletableFuture.runAsync(() -> {
            System.out.println("Starting saga orchestration for " + sagaId);
            executeService(initialService, sagaId, sagaCompleted);
        }, executor);
    }

    private void executeService(String serviceName, String sagaId, AtomicBoolean sagaCompleted) {
        if (!serviceRegistry.containsKey(serviceName)) {
            throw new RuntimeException("Unknown service: " + serviceName);
        }

        ServiceInfo info = serviceRegistry.get(serviceName);
        System.out.println("Executing service: " + serviceName + ", Saga ID: " + sagaId);

        CompletableFuture<Void> future = info.execute(sagaId);
        future.thenAccept(v -> {
            System.out.println("Service " + serviceName + " completed successfully");
            checkSagaCompletion(sagaId, sagaCompleted);
        }).exceptionally(ex -> {
            System.err.println("Error executing service " + serviceName + ": " + ex.getMessage());
            compensateService(sagaId, serviceName);
            return null;
        });
    }

    private void checkSagaCompletion(String sagaId, AtomicBoolean sagaCompleted) {
        if (sagaCompleted.compareAndSet(false, true)) {
            System.out.println("Saga " + sagaId + " completed successfully");
        } else {
            System.out.println("Saga " + sagaId + " already completed");
        }
    }

    private void compensateService(String sagaId, String serviceName) {
        System.out.println("Compensating service: " + serviceName + ", Saga ID: " + sagaId);
        ServiceInfo info = serviceRegistry.get(serviceName);
        info.compensate(sagaId);
    }

    public void shutdown() {
        executor.shutdownNow();
    }

    static class ServiceInfo {
        private final String serviceName;
        private final Function<String, CompletableFuture<Void>> executeFunction;
        private final BiConsumer<String, Throwable> compensationFunction;

        public ServiceInfo(String serviceName, Function<String, CompletableFuture<Void>> executeFunction, BiConsumer<String, Throwable> compensationFunction) {
            this.serviceName = serviceName;
            this.executeFunction = executeFunction;
            this.compensationFunction = compensationFunction;
        }

        public CompletableFuture<Void> execute(String sagaId) {
            return executeFunction.apply(sagaId);
        }

        public void compensate(String sagaId) {
            compensationFunction.accept(sagaId, null);
        }
    }

    @FunctionalInterface
    interface Function<T, R> {
        R apply(T t);
    }

    @FunctionalInterface
    interface BiConsumer<T, U> {
        void accept(T t, U u);
    }
}
