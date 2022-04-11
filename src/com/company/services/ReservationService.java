package com.company.services;

import com.company.Seat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Reservation service handles reserving the seats.
 */
public class ReservationService {
    /* This contains the snapshot of the current state of the seats, this is not used in the program */
    private final Seat[][] seats;
    /* At any point of time availableSeats will have seats that are not reserved */
    private List<LinkedList<Seat>> availableSeats;
    private int currentOccupancy;
    private final int totalCapacity;

    public ReservationService() {
        seats = new Seat[10][20];
        this.currentOccupancy = 0; // initially no seats are assigned
        this.totalCapacity = 10 * 20; // Number of rows * cols
        /* initialize the seats in the movie theater. */
        this.initializeSeats();
        /* initialize the availableSeats in the movie theater. */
        this.initializeAvailableSeats();
    }

    /**
     * Checks if there is enough space in the theatre to assign given number of seats.
     * @param seatsToAssign number of seats to assign.
     * @return true if seats can be assigned, false if not.
     */
    public boolean canAssignSeats(int seatsToAssign) {
        return currentOccupancy + seatsToAssign <= totalCapacity;
    }

    /**
     * Assigns the seats in the following order.
     * <li> Picks the top row(the farthest away from screen like J) that is available. </li>
     * <li> If seats cannot be filled in the current row, then the row which has the capacity to fill all seats will be picked. </li>
     * <li> If seats cannot be filled in one row, then seats will be split between different rows. </li>
     *
     * @param seatsToAssign number of seats to assign. This has to be a positive number.
     * @return List of seats that are assigned.
     */
    public List<Seat> assignSeats(int seatsToAssign) {
        /* seatsToAssign cannot be less than or equal to 0 */
        if(seatsToAssign <= 0) return null;
        /* If we cannot assign requested seats return null */
        if (currentOccupancy + seatsToAssign > totalCapacity) return null;

        // Check if single row assignment is not possible. O(1) operation.
        if (seatsToAssign > 20) {
            // All seats cannot be filled in one row, so split them.
            return splitAssignSeats(seatsToAssign);
        } else {
            // Check if there is a row that has required free seats. 0(rows) operation.
            // can use PriorityQueue<LinkedList<available seats>> to get O(1);
            int rowWithRequiredSeats = canAssignInOneRow(seatsToAssign);
            if (rowWithRequiredSeats != -1) {
                // All seats can be filled in one row.
                return assignSeatsInOneRow(seatsToAssign, rowWithRequiredSeats);
            } else {
                // All seats cannot be filled in one row, so split them.
                return splitAssignSeats(seatsToAssign);
            }
        }
    }

    private int canAssignInOneRow(int seatsToAssign) {
        // Check every row starting from top row to see if any row has all required seats.
        for (int i = 9; i >= 0; i--) {
            if (availableSeats.get(i).size() >= seatsToAssign) {
                return i;
            }
        }
        return -1;
    }

    private List<Seat> assignSeatsInOneRow(int seatsToAssign, int rowToAssign) {
        List<Seat> assignedSeats = new ArrayList<>();

        // it is guaranteed that rowToAssign has required number of seats.
        while (seatsToAssign > 0) {
            // get empty seats in the current row
            Seat current = availableSeats.get(rowToAssign).removeFirst();
            current.setReserved(true);
            assignedSeats.add(current); // O(1) since Doubly linked list
            seatsToAssign--;
        }

        // update currentOccupancy
        currentOccupancy += assignedSeats.size();
        return assignedSeats;
    }

    private List<Seat> splitAssignSeats(int seatsToAssign) {
        List<Seat> assignedSeats = new ArrayList<>();
        // Start with top most row.
        int currentRow = 9;

        // split seats into multiple rows. O(rows)
        while (seatsToAssign > 0) {
            // if current row has any empty seats assign them. else move on to next row.
            while (seatsToAssign > 0 && availableSeats.get(currentRow).size() > 0) {
                // get empty seats in the current row
                Seat current = availableSeats.get(currentRow).removeFirst();
                current.setReserved(true);
                assignedSeats.add(current); // O(1) since Doubly linked list
                seatsToAssign--;
            }
            currentRow--;
        }

        /* update currentOccupancy */
        currentOccupancy += assignedSeats.size();
        return assignedSeats;
    }

    private void initializeAvailableSeats() {
        this.availableSeats = new LinkedList<>();
        for (int row = 0; row < seats.length; row++) {
            availableSeats.add(new LinkedList<>());
            for (int col = 0; col < seats[row].length; col++) {
                availableSeats.get(row).add(seats[row][col]);
            }
        }
    }

    private void initializeSeats() {
        for (int row = 0; row < 10; row++) {
            char rowNumber = (char) (row + 'A');
            for (int col = 0; col < 20; col++) {
                seats[row][col] = new Seat(rowNumber, col);
            }
        }
    }
}
