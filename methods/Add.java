package methods;

import com.beust.jcommander.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static misc.Methods.isInt;
import static misc.Methods.setSheetWidth;
import static misc.Variables.*;

/**class that contains methods that enable the user to add
 * a row in a specified position and initialize it's values*/
public class Add {

    /**Jcommander's main parameter that ideally hold the user 
     * specified position in the 1st element*/
    /*For more info, refer to the similar variable in the Sort class*/
    @Parameter()
    static List<String> positionSTR = new ArrayList<>();

    //The main method that check the validity of the position the user entered
    public static void main(String []args) throws IOException {
        //Parse the passed arguments with Jcommander
        Add parse = new Add();
        new JCommander(parse, args);
        
        /*Exit the (Add.main) method if the user didn't enter any thing, or 
         *entered a string, or a no. less than 1, or more than the no. of columns*/
        if ( positionSTR.get(0).isEmpty()
                || !isInt(positionSTR.get(0))
                || Integer.parseInt(positionSTR.get(0)) < 1
                || Integer.parseInt(positionSTR.get(0)) - 1 > noOfRows ) {

            System.out.println("Usage:\nadd <RowNumber>");
            System.err.println("(RowNumber) not found, or not valid");
            return;
        }

        //This will only be executed if the user entered a valid int
        else {
            //save the value in an int, and -1 bec. arrays begin at 0
            int position = Integer.parseInt(positionSTR.get(0));
            position--;

            //launch the add method
            add(position);

            setSheetWidth();
            Print.main();
        }
    }

    /**the (add) method add a row to the sheet, and assign values to it's cells */
    static void add(int position) throws IOException {

        //increase the number of rows
        noOfRows++;

        //Boolean var to determine if the user want to add a row in the first position
        boolean first = false;
        if (position == 0) first = true;

        //in the following loop, we need position to 
        //be (1) if the user want a new row in the 1st pos.
        if (first) position = 1;

        //Shift all the elements (from the last one till (position)) 
        // one step down, to free space for the new row
        for (int i = 0; i < noOfColumns; i++) {
            for (int j = noOfRows; j >= position; j--) {

                strArr[i][j] = strArr[i] [j - 1];
                intArr[i][j] = intArr[i] [j - 1];
            }
        }

        //in the following loop, we need position to 
        //be (0) if the user want a new row in the 1st pos.
        if (first) position = 0;

        //Loop through all columns, and initialize the value of the new cell in that column
        for (int i = 0; i < noOfColumns; i++){

            //If the column is string
            if (index[i][0] == 0){
                System.out.print(columnNames[i]+ ": ");
                strArr[index[i][1]][position] = consRead.readLine();
            }
            //If the column is Integer
            else if (index[i][0] == 1){
                System.out.print(columnNames[i]+ ": ");
                intArr[index[i][1]][position] = Integer.parseInt(consRead.readLine());
            }
        }
    }
}