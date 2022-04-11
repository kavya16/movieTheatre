# movie theatre reservation system

The movie theatre reservation system takes in name of the input file as an argument and generates an output file that contains the reservations and its seats. <br>
Example Input:
```
R001 2
R002 4
R003 4
R004 3
R005 20
R006 5
```

Example output:
```
R001 J0,J1
R002 J2,J3,J4,J5
R003 J6,J7,J8,J9
R004 J10,J11,J12
R005 J13,J14,J15,J16,I0,I1,I2,I3,I4,I5,I6,I7,I8,I9,I10,I11,I12,I13,I14,I15
R006 H0,H1,H2,H3,H4
```

## Assumptions:
* Input file contains rows in this format "ReservationNumber seatsToReserve"
* Only positive numbers are sent as part of seatsToReserve
* When seats cannot be reserved, the reservation in the output file will have "INSUFFICIENT_SEATS" in place of assigned seat numbers.
* Program will continue to allocate the seats even any of the reservations cannot be fulfilled/ sees INSUFFICIENT_SEATS.
* Users prefer the top rows (farthest from the screen) / J in the given case.
* Every row will have 3 seats as buffer for user safety concerns.

## How algorithm works
* Picks the top row(the farthest away from screen like J) that is available.
* If seats cannot be filled in the current row, then the row which has the capacity to fill all seats will be picked.
* If seats cannot be filled in one row, then seats will be split between different rows.

## Run the project
Go to the Terminal inside the folder to execute the following commands
* compile:
javac -d output src/com/company/*.java src/com/company/services/*.java

*Run:
cd output
java com.company.Main /Users/kavyalakshmiguttikonda/IdeaProjects/movieTheatre/sampleReservationFile.txt

