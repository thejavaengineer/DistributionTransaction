package com.bank.threephasecommit;

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

    public boolean preparePreparing() {
        if (state == State.PREPARED) {
            System.out.println(name + " preparing");
            state = State.PREPARING;
            return true;
        }
        return false;
    }

    public boolean commit() {
        if (state == State.PREPARING) {
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
