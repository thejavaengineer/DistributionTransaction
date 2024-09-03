package com.bank.threephasecommit;

import java.util.ArrayList;
import java.util.List;

class Coordinator {
    private List<Participant> participants = new ArrayList<>();
    private State state = State.PREPARED;

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public boolean prepare() {
        System.out.println("Coordinator preparing...");
        for (Participant participant : participants) {
            participant.prepare();
        }
        boolean allReady = true;
        for (Participant participant : participants) {
            if (participant.state != State.PREPARED) {
                allReady = false;
                break;
            }
        }
        state = allReady ? State.PREPARED : State.ABORTED;
        return state == State.PREPARED;
    }

    public boolean preparePreparing() {
        if (state == State.PREPARED) {
            System.out.println("Coordinator preparing...");
            for (Participant participant : participants) {
                if (!participant.preparePreparing()) {
                    abort();
                    return false;
                }
            }
            state = State.PREPARING;
            return true;
        }
        return false;
    }

    public boolean commit() {
        if (state == State.PREPARING) {
            System.out.println("Coordinator committing...");
            for (Participant participant : participants) {
                if (!participant.commit()) {
                    abort();
                    return false;
                }
            }
            state = State.COMMITTED;
            return true;
        }
        return false;
    }

    public void abort() {
        System.out.println("Coordinator aborting...");
        state = State.ABORTED;
        for (Participant participant : participants) {
            participant.abort();
        }
    }
}
