package com.company.services;

import com.company.Seat;

import java.util.Arrays;
import java.util.List;

public class ReservationServiceTest {
    ReservationService reservationService;

    public ReservationServiceTest() {
        reservationService = new ReservationService();
    }

    public void testWhen0SeatsNeedsToBeAssigned() {
        reservationService = new ReservationService();

        List<Seat> assignedSeats = reservationService.assignSeats(0);
        if (assignedSeats == null) {
            System.out.println("Test testWhen0SeatsNeedsToBeAssigned SUCCEEDED");
        } else {
            System.out.println("Test testWhen0SeatsNeedsToBeAssigned FAILED");
        }
    }

    public void testWhenGreaterThan200SeatsNeedsToBeAssigned() {
        reservationService = new ReservationService();

        List<Seat> assignedSeats = reservationService.assignSeats(201);
        if (assignedSeats == null) {
            System.out.println("Test testWhenGreaterThan200SeatsNeedsToBeAssigned SUCCEEDED");
        } else {
            System.out.println("Test testWhenGreaterThan200SeatsNeedsToBeAssigned FAILED");
        }
    }

    public void testWhenLessThan20SeatsNeedsToBeAssigned() {
        reservationService = new ReservationService();

        List<Seat> assignedSeats = reservationService.assignSeats(2);
        assignedSeats.forEach((seat -> {
            if (!seat.isReserved()) {
                System.out.println("Test failed: seat " + seat + " needs to be occupied");
            }
        }));

        List<String> expected = Arrays.asList("J0", "J1");
        if (!assignedSeats.stream().map(Seat::getSeatNumber).toList().equals(expected)) {
            System.out.println("Test failed: expected list " + expected + " but received" + assignedSeats.stream().map(Seat::getSeatNumber));
        } else {
            System.out.println("Test testWhenLessThan20SeatsNeedsToBeAssigned SUCCEEDED");
        }
    }

    public void testWhen19SeatsNeedsToBeAssignedWhile5SeatsAreAlreadyAssigned() {
        reservationService = new ReservationService();
        reservationService.assignSeats(5);
        List<Seat> assignedSeats = reservationService.assignSeats(19);
        assignedSeats.forEach((seat -> {
            if (!seat.isReserved()) {
                System.out.println("Test failed: seat " + seat + "needs to be occupied");
            }
        }));

        List<String> expected = Arrays.asList("I0", "I1", "I2"
                , "I3", "I4", "I5", "I6", "I7", "I8", "I9", "I10", "I11", "I12", "I13", "I14", "I15", "I16", "I17", "I18");

        if (!assignedSeats.stream().map(Seat::getSeatNumber).toList().equals(expected)) {
            System.out.println("Test failed: expected list " + expected + " but received" + assignedSeats.stream().map(Seat::getSeatNumber));
        } else {
            System.out.println("Test testWhen19SeatsNeedsToBeAssignedWhile5SeatsAreAlreadyAssigned SUCCEEDED");
        }
    }

    public void testWhenGreaterThan20SeatsNeedsToBeAssigned() {
        reservationService = new ReservationService();

        List<Seat> assignedSeats = reservationService.assignSeats(21);
        assignedSeats.forEach((seat -> {
            if (!seat.isReserved()) {
                System.out.println("Test failed: seat " + seat + "needs to be occupied");
            }
        }));

        List<String> expected = Arrays.asList("J0", "J1", "J2"
                , "J3", "J4", "J5", "J6", "J7", "J8", "J9", "J10", "J11", "J12", "J13", "J14", "J15", "J16", "J17", "J18", "J19", "I0");

        if (!assignedSeats.stream().map(Seat::getSeatNumber).toList().equals(expected)) {
            System.out.println("Test failed: expected list " + expected + " but received" + assignedSeats.stream().map(Seat::getSeatNumber));
        } else {
            System.out.println("Test testWhenGreaterThan20SeatsNeedsToBeAssigned SUCCEEDED");
        }
    }
}
