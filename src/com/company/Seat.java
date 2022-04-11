package com.company;

/**
 * Seat represents a seat in the movie theatre.
 */
public class Seat {
    /* Row of the seat */
    char row;
    /* Column number in the row of the seat */
    int colNumber;
    /* Tells if the seat is reserved or not */
    boolean isReserved;

    public Seat(char row, int number){
        this.row = row;
        this.colNumber = number;
    }

    /**
     * Sets the reserved state of the seat.
     * @param isReserved boolean that represents the reserved state of the seat
     */
    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    /**
     * Gets the reserved state of the seat.
     */
    public boolean isReserved() {
        return this.isReserved;
    }

    /**
     * Gets the seat number.
     */
    public String getSeatNumber(){
        return new StringBuilder().append(row).append(colNumber).toString();
    }
}
