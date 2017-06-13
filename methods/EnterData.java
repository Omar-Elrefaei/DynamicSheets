package methods;

import java.io.*;

import static misc.Methods.setSheetWidth;
import static misc.Variables.*;


/**
 * The EnterData class contains some methods that are responsible for entering data to 
 * the program, either interactively from the command line or automatically from a file
 */
public class EnterData {
    static int noOfInt = -1, noOfStr = -1;

    /**The main method that let the user choose, between entering the data manually or from a file*/
    public static void main() throws IOException {

        //Print the menu, and read user choice
        System.out.println("Choose from the following: \n"
                + "man\t-Enter the data manually yourself-"
                + "\nfile\t-load the data from a text file "
                + "formatted as the attached \"example.txt\" file-");
        input = consRead.readLine();

        switch (input) {
            case "man":
                manually();
                break;
            case "file":
                fromFile();
                break;
            default:
                System.err.println("Not a valid option");
                return;
        }
    }

    private static void manually() throws IOException {
        
            System.out.println("Enter \"end\" when you want to finish entering data\n");

            //Read Names & Types of columns
            for (int i = 0; i < 1000; i++) {
                System.out.print("Enter the name of column " + i + ": ");

                input = consRead.readLine();
                if ( input.equals("end") ) break;

                columnNames[i] = input;
                System.out.print("Enter the type of \"" + columnNames[i] + "\" (str/int): ");
                input = consRead.readLine();

                if ( input.equals("int") ) {
                    noOfColumns++;    //Increase total columns
                    noOfInt++;        //Increase total integers
                    index[noOfColumns - 1][0] = 1;        //Type is int
                    index[noOfColumns - 1][1] = noOfInt;  //Order in integers
                } else if ( input.equals("str") ) {
                    noOfColumns++;    //Increase total columns
                    noOfStr++;        //Increase total strings
                    index[noOfColumns - 1][0] = 0;        //Type is String
                    index[noOfColumns - 1][1] = noOfStr;  //Order in Strings

                } 
                else System.out.print("error: enter a valid option (int, str)");

                System.out.println("");
            }

            //Enter the data in the rows. Max 10,000 row
            dataEntryMode: {
                System.out.println("\nEnter \"end\" when you want to finish entering data\n");

                for (int i = 0; i < 1000; i++) {
                    for (int j = 0; j < noOfColumns; j++) {
                        if ( index[j][0] == 0 ) {
                            System.out.print(columnNames[j] + " " + i + " = ");
                            input = consRead.readLine();
                            if ( input.equals("end") ) break dataEntryMode;
                            strArr[index[j][1]][i] = input;
                        }
                        else if ( index[j][0] == 1 ) {
                            System.out.print(columnNames[j] + " " + i + " = ");
                            input = consRead.readLine();
                            if ( input.equals("end") ) break dataEntryMode;
                            intArr[index[j][1]][i] = Integer.parseInt(input);
                        }
                    }
                    System.out.println();
                    noOfRows++;
                }
                System.err.println("Sorry, you can't enter more than 1000 row");
            }
            //call setSheetWidth to calibrate (columnWidth) array to the new data
            setSheetWidth();
            Print.main();
        
    }

    private static void fromFile()  {
        //Declaring BufferedReader to read data from a text file
        System.out.println("in fromFile");
        BufferedReader txtRead;

        //Surround all the file operations with a try block, so if any IOException occurred,
        //just print an error message in the catch block without braking the whole program
        try {
        
            //Requesting & Reading file path
            while (true) {
                System.out.println("Please enter the full path of the file you want to import");
                File path = new File(consRead.readLine());

                //If file exists, break the loop and continue the program, if not print error then, request it again
                try {
                    txtRead = new BufferedReader(new FileReader(path));
                    break;
                } catch (FileNotFoundException e) {
                    System.err.println("Please enter a valid file path");
                }
            }


            //Reading the declaration line (first line) & split the values by ","
            txtRead.skip(16);
            String decelerationLine = txtRead.readLine();
            String[] decelerators = decelerationLine.split("\\s*,\\s*");
            
            //Remove the curly bracket from the last element
            decelerators[decelerators.length-1] = decelerators[decelerators.length-1].substring(0, 3);
            noOfColumns = decelerators.length;

            //Reading the names line (second line) & split the values by ","
            txtRead.skip(16);
            String namingLine = txtRead.readLine();
            columnNames = namingLine.split("\\s*,\\s*");
            columnNames[noOfColumns-1] = columnNames[noOfColumns-1]
                    .substring(0, columnNames[noOfColumns-1].length()-1);

            //skip an empty line
            txtRead.readLine();

            //Assign values to some variables depending on columns types
            for (int i = 0; i < noOfColumns; i++) {
                if ( decelerators[i].equals("str") ) {
                    noOfStr++;
                    index[i][0] = 0;
                    index[i][1] = noOfStr;
                } else if ( decelerators[i].equals("int") ) {
                    noOfInt++;
                    index[i][0] = 1;
                    index[i][1] = noOfInt;
                }
            }

            //Read each row, split it by "|", then assign the values to the (strArr/intArr) arrays
            for (int i = 0; i < 1000; i++) {
                //TODO or (noOfRows++)
                noOfRows = i;

                //Read a row
                input = txtRead.readLine();

                //break if we reached end of file
                if (input == null) break;

                //Split the row and assign it into an array
                String [] inputLine = input.split("\\s*\\|\\s*");

                //Loop through each column, checking it's type, and assigning the corresponding 
                //element from (inputLine) array to the cell in the intersection of column[j] and row[i]
                for (int j = 0; j < noOfColumns; j++) {
                    if ( index[j][0] == 0 )
                        strArr[ index[j][1] ] [i] = inputLine[j].trim();

                    else if ( index[j][0] == 1 )
                        intArr[ index[j][1] ] [i] = Integer.valueOf( inputLine[j].trim() );
                }
            }
            setSheetWidth();
            Print.main();

        } catch (IOException e) {
            System.err.println("Sorry, an error occurred while reading the file, please check it's format and try again");
        }
    }
}
