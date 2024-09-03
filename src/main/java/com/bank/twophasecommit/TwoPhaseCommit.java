package com.bank.twophasecommit;

public class TwoPhaseCommit {
    public static void main(String[] args) {
        Coordinator coord = new Coordinator();

        Participant p1 = new Participant("P1");
        Participant p2 = new Participant("P2");

        coord.addParticipant(p1);
        coord.addParticipant(p2);

        // First attempt at commit
        if (coord.commit()) {
            System.out.println("First attempt successful");
        } else {
            System.out.println("First attempt failed");
        }

        // Second attempt at commit
        if (coord.commit()) {
            System.out.println("Second attempt successful");
        } else {
            System.out.println("Second attempt failed");
        }
    }
}
