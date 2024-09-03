package com.bank.threephasecommit;

enum State {
    PREPARED,
    PREPARING,
    COMMITTED,
    ABORTED
}
