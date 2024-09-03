package com.bank.saga.chronography;

import java.time.Instant;
import java.util.concurrent.*;

class SagaChronography {
    private final ConcurrentMap<String, SagaEvent> sagaEvents;
    private final ScheduledExecutorService scheduler;

    public SagaChronography(int maxSagaAgeInSeconds) {
        this.sagaEvents = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.scheduler.scheduleAtFixedRate(this::cleanupOldSagas, 60, 60, TimeUnit.SECONDS);
    }

    public void registerSaga(String sagaId, String serviceName) {
        sagaEvents.putIfAbsent(sagaId, new SagaEvent(serviceName));
    }

    public void updateSagaStatus(String sagaId, String serviceName, SagaStatus status) {
        SagaEvent event = sagaEvents.computeIfPresent(sagaId, (id, existingEvent) -> {
            existingEvent.setStatus(status);
            return existingEvent;
        });
        if (event != null) {
            System.out.println("Updated saga status for " + sagaId + ": " + status);
        }
    }

    public SagaEvent getSagaEvent(String sagaId) {
        return sagaEvents.getOrDefault(sagaId, null);
    }

    public void cleanupOldSagas() {
        Instant now = Instant.now();
        sagaEvents.entrySet().removeIf(entry -> {
            SagaEvent event = entry.getValue();
            if (now.toEpochMilli() - event.getStartTime() > TimeUnit.SECONDS.toMillis(600)) {
                System.out.println("Removing old saga: " + entry.getKey());
                return true;
            }
            return false;
        });
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }

    private static class SagaEvent {
        private final String serviceName;
        private SagaStatus status;
        private long startTime;

        public SagaEvent(String serviceName) {
            this.serviceName = serviceName;
            this.startTime = System.currentTimeMillis();
        }

        public String getServiceName() {
            return serviceName;
        }

        public SagaStatus getStatus() {
            return status;
        }

        public void setStatus(SagaStatus status) {
            this.status = status;
        }

        public long getStartTime() {
            return startTime;
        }
    }

    public enum SagaStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        FAILED,
        ABORTED
    }
}
