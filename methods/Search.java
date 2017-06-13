package methods;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static misc.Methods.printRow;
import static misc.Variables.*;

/**Search the whole sheet or a specific column for a search key*/
public class Search {
    
    /**List that jcommander will assign all the passed arguments to it,<BR>
     * it contains the search keys.<BR>
     * Check the corresponding var in the (Sort) class for more details*/
    @Parameter()
    static List<String> searchKeys = new ArrayList<>();
    
    

    /**The main method that parse the arguments, 
     * search is the sheet for the keys, and display the result */
    public static void main(String[]args) throws IOException {
        
        //parse any arguments that was passed to the Search class with Jcommander
        Search parser = new Search();
        new JCommander(parser, args);

        //If the user didn't pass any parameters while calling the search command, 
        // ask him for the search key now.
        if ( searchKeys.get(0).isEmpty() ) {
            System.out.print("Enter a keyword to search for: ");
            searchKeys = new LinkedList<> (Arrays.asList(consRead.readLine().split(" ")));
        }
        
        //Boolean to check if at least one matching result is found
        boolean atLeastOneIsFound = false;
        //Boolean array to label the printed rows, to prevent duplicates in the search result
        boolean [] printed = new boolean [noOfRows];

        //Loop through every search key
        for (String key : searchKeys) {
            //Loop through every column
            for (int i = 0; i < noOfColumns; i++) {
                //Loop through every cell in the column
                for (int j = 0; j < noOfRows; j++) {
                    
                    //If this column is String
                    if (index[i][0] == 0) {
                        //If the search key was found & this row was not printed before, print it
                        if (strArr[ index[i][1] ][j].contains(key.toLowerCase()) && !printed[j]) {
                            printRow(j);         //print the matched row
                            printed [j] = true;  //label it as printed
                            atLeastOneIsFound = true;
                        }
                    }
                    //Same as the above but for Integer columns
                    if (index[i][0] == 1) {
                        if (Integer.toString(intArr[ index[i][1] ][j] ).contains(key) && !printed[j]) {
                            printRow(j);         
                            printed [j] = true;    
                            atLeastOneIsFound = true;


                        }
                    }
                }
            }
        }
        if (!atLeastOneIsFound) 
            System.out.println("Sorry, No results found.");
    }

}

