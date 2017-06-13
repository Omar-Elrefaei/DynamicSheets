package methods;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static misc.Methods.isInt;
import static misc.Methods.setSheetWidth;
import static misc.Variables.*;
/**Class that select's an row and, allow the user to modify it*/
public class Modify {
    
    /**Jcommander's main parameter that ideally hold the user 
     * specified position in the 1st element*/
    /*For more info, refer to the similar variable in the Sort class*/
    @Parameter()
    static List<String> positionSTR = new ArrayList<>();

    /**The main method that select's an row and, allow the user to modify it*/
    public static void main(String[] args) throws IOException, InterruptedException {
        
        //Parse the arguments with Jcommander
        Modify parse = new Modify();
        new JCommander(parse, args);

        //A var that will hold the position of the row the user want to modify
        int rowPosition;

        /*Exit the (Modify.main) method if the user didn't enter any thing, or 
        *entered a string, or a no. less than 1, or more than the no. of columns*/
        if ( positionSTR.get(0).isEmpty() 
                ||!isInt(positionSTR.get(0))
                ||Integer.parseInt(positionSTR.get(0)) < 1
                ||Integer.parseInt(positionSTR.get(0))-1 > noOfRows){
            
            System.out.println("Usage:\nmodify <RowNumber>");
            Thread.sleep(10);
            System.err.println("(RowNumber) not found, or not valid");
            return;
        }
        //This will only be executed if the user entered a valid int
        else {
            //save the value in an int, and -1 bec. arrays begin at 0
            rowPosition = Integer.parseInt(positionSTR.get(0));
            rowPosition--;
            
            //Loop through all columns, and let the user edit the cell in the specified position
            for (int i = 0; i < noOfColumns; i++){
                //if the column type is string
                if (index[i][0] == 0){
                    System.out.print(columnNames[i]+ ": ");
                    strArr[ index[i][1] ][rowPosition] = consRead.readLine();
                }
                //if the column type is integer
                else if (index[i][0] == 1){
                    System.out.print(columnNames[i]+ ": ");
                    intArr[ index[i][1] ][rowPosition] = Integer.parseInt(consRead.readLine());
                }
            }
        }
        //Reset the width data and print the sheet
        setSheetWidth();
        Print.main();
    }
}