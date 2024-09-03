package com.bank.twophasecommit;

class Participant {
    private final String name;
    State state = State.PREPARED;

    public Participant(String name) {
        this.name = name;
    }

    public void prepare() {
        System.out.println(name + " prepared");
        state = State.PREPARED;
    }

    public boolean commit() {
        if (state == State.PREPARED) {
            System.out.println(name + " committed");
            state = State.COMMITTED;
            return true;
        }
        return false;
    }

    public void abort() {
        System.out.println(name + " aborted");
        state = State.ABORTED;
    }
}
