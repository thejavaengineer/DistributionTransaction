package com.bank.saga;

import java.util.concurrent.CompletableFuture;

interface Service {
    CompletableFuture<Void> execute();

    CompletableFuture<Void> compensate();
}
