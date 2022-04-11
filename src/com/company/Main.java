package com.company;

import com.company.services.ReservationService;
import com.company.services.ReservationServiceTest;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Tests
        /* TODO: Use a unit testing framework like Junit to better test this. */
        ReservationServiceTest reservationServiceTest = new ReservationServiceTest();
        reservationServiceTest.testWhenLessThan20SeatsNeedsToBeAssigned();
        reservationServiceTest.testWhenGreaterThan20SeatsNeedsToBeAssigned();
        reservationServiceTest.testWhen19SeatsNeedsToBeAssignedWhile5SeatsAreAlreadyAssigned();
        reservationServiceTest.testWhenGreaterThan200SeatsNeedsToBeAssigned();
        reservationServiceTest.testWhen0SeatsNeedsToBeAssigned();

        if (args.length > 0) {
            ReservationService reservationService = new ReservationService();

            try {
                /* Read the content of the file into a bufferedReader. */
                BufferedReader bufferedReader = readFile(args[0]);

                File file = new File("output.txt");
                FileWriter fileWriter = new FileWriter(file);

                /* File to write assigned seats to the file */
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                /* Read the first reservation */
                String currentReservation = bufferedReader.readLine();

                /* This will be null when there are no more reservations to read */
                while (currentReservation != null) {
                    // Assumption: All the lines in the input file will be in this format "ReservationNumber SeatsToAssign"
                    String[] split = currentReservation.split(" ");

                    /* Read reservation number and number of seats to reserve. */
                    String reservationNumber = split[0];
                    int seatsToAssign = Integer.parseInt(split[1]);

                    /* Assign the seats if possible */
                    List<Seat> assignedSeats;
                    /* Check if the seats can be assigned. */
                    if (reservationService.canAssignSeats(seatsToAssign)) {
                        assignedSeats = reservationService.assignSeats(seatsToAssign);
                        /* This should not happen here but can happen if assignSeats() is called after canAssignSeats() returns false */
                        if (assignedSeats == null) {
                            bufferedWriter.write(reservationNumber + " " + "Something went wrong" + "\n");
                        }
                        /* build output reservation line from assigned seats */
                        StringBuilder sb = new StringBuilder();
                        assignedSeats.forEach((seat -> sb.append(seat.getSeatNumber()).append(',')));
                        /* remove the last ','. */
                        sb.deleteCharAt(sb.length() - 1);

                        /* Write the reservation number and assigned seats to the file */
                        bufferedWriter.write(reservationNumber + " " + sb + "\n");
                    }
                    else { /* When cannot assign seats */
                        // Assumption: INSUFFICIENT_SEATS is sent back when cannot reserve the requested seats
                        bufferedWriter.write(reservationNumber + " " + "INSUFFICIENT_SEATS" + "\n");
                    }

                    /* read next reservation */
                    currentReservation = bufferedReader.readLine();
                }
                /* Finish writing to output file */
                bufferedWriter.close();

                /* print the path of output file */
                System.out.println(file.getAbsolutePath());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private static BufferedReader readFile(String arg) throws FileNotFoundException {
        File file = new File(arg);
        FileReader fileReader = new FileReader(file);
        return new BufferedReader(fileReader);
    }
}
