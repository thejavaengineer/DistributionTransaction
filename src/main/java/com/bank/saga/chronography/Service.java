package com.bank.saga.chronography;

import java.util.concurrent.CompletableFuture;

interface Service {
    CompletableFuture<Void> execute();

    CompletableFuture<Void> compensate();
}
