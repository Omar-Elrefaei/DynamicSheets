package methods;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static misc.Methods.isInt;
import static misc.Variables.*;

/**
 * A Class that contain methods to sort the entire sheet given a column number
 */
public class Sort {

    /**List that Jcommander will assign all the passed arguments to it.<BR>
     * Ideally the first element will contain the column number */
    /*It is declared as a list to avoid throwing an error if the user entered any 
     trailing unintended arguments, and to enable Jcommander to parse the parameters
     without the need to write a parameter name "-s" before an argument "3" */
    @Parameter()
    static List<String> columnNoSTR = new ArrayList<>();

    /**A boolean parameter that determine whether the sheet should be sorted descendingly or not*/
    @Parameter(names = {"-d"})
    static boolean descending = false;

    /**The main method that parse the arguments, and check if the (columnNoSTR.get(0))
     *  is a valid int, if so pass it's numeric value to the sort method  */
    public static void main (String [] args) throws IOException {
        //Parse the arguments with Jcommander
        Sort parse = new Sort();
        new JCommander(parse, args);
        
        //If the passed argument is empty or not integer, print correct usage and return
        if (columnNoSTR.get(0).isEmpty() || !isInt(columnNoSTR.get(0))){
            System.out.println("Usage:\nsort   [options] <columnNumberToSortBy>\n\noptions:\n\t-d\t\t\tsort descending");

            descending = false;
            return;
        }
        //else convert the passed parameter to an integer
        else {
            int columnNo = Integer.parseInt(columnNoSTR.get(0));
            
            //then check if it is a valid column number, if NOT print an error and return
            if ( columnNo > noOfColumns || columnNo < 1) {
                System.err.println("Error, \"" + columnNo + "\" is Not a valid column number");
                descending = false;
                return;
            }
            //If it is indeed a valid int, call the sort method passing the column number to it
            else {
                sort(columnNo);
                descending = false;
            }
        }
    }
    /**A method that sort the whole sheet by a specific column*/
    static void sort(int columnNo){

        //Start Selection sort algorithm
        for (int i = 0; i < noOfRows; i++) {
            int flag = i;
            
            //If the user want to sort ascendingly
            if(!descending) {
                for (int j = i; j < noOfRows; j++) {
                    
                    //If the selected column is a string
                    if ( index[columnNo][0] == 0 ) {
                        //if the 
                        if ( strArr[index[columnNo][1]][j].compareTo(strArr[index[columnNo][1]][flag]) < 0 ) {
                            flag = j;
                        } 
                    }//Else if the selected column is an integer
                    else if ( index[columnNo][0] == 1 ) {
                        if ( intArr[index[columnNo][1]][j] < (intArr[index[columnNo][1]][flag]) ){
                            flag = j;
                        }
                    }
                }
            }
            //The same as the above but sort descendingly by, inverting the signs in the comparision
            else if (descending) {
                for (int j = i; j < noOfRows; j++) {
                    if ( index[columnNo][0] == 0 ) {
                        if ( strArr[index[columnNo][1]][j].compareTo(strArr[index[columnNo][1]][flag]) > 0 )
                            flag = j;
                    } else if ( index[columnNo][0] == 1 ) {
                        if ( intArr[index[columnNo][1]][j] > (intArr[index[columnNo][1]][flag]) ){
                            flag = j;
                        }
                    }
                }
            }

            //Loop through swapping the values of i & flag rows
            for (int k = 0; k < noOfColumns; k++) {
                //if string
                if ( index[k][0] == 0 ) {
                    String temp = strArr [ index[columnNo] [1]] [i];
                    strArr [index[columnNo][1]] [i] = strArr [index[columnNo][1]] [flag];
                    strArr [index[columnNo][1]] [flag] = temp;
                //if int
                } else if ( index[k][0] == 1 ) {
                    int temp = intArr[index[columnNo][1]][i];
                    intArr [index[columnNo][1]] [i] = intArr [index[columnNo][1]] [flag];
                    intArr [index[columnNo][1]] [flag] = temp;
                }


            }


        }
        Print.main();
    }
}