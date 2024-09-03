package com.bank.threephasecommit;

public class ThreePhaseCommit {
    public static void main(String[] args) {
        Coordinator coord = new Coordinator();

        Participant p1 = new Participant("P1");
        Participant p2 = new Participant("P2");

        coord.addParticipant(p1);
        coord.addParticipant(p2);

        // First attempt at commit
        if (coord.prepare()) {
            System.out.println("First prepare successful");
            if (coord.preparePreparing()) {
                System.out.println("First preparePreparing successful");
                if (coord.commit()) {
                    System.out.println("First commit successful");
                } else {
                    System.out.println("First commit failed");
                }
            } else {
                System.out.println("First preparePreparing failed");
            }
        } else {
            System.out.println("First prepare failed");
        }

        // Second attempt at commit
        if (coord.prepare()) {
            System.out.println("Second prepare successful");
            if (coord.preparePreparing()) {
                System.out.println("Second preparePreparing successful");
                if (coord.commit()) {
                    System.out.println("Second commit successful");
                } else {
                    System.out.println("Second commit failed");
                }
            } else {
                System.out.println("Second preparePreparing failed");
            }
        } else {
            System.out.println("Second prepare failed");
        }
    }
}
