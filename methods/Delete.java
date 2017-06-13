package methods;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static misc.Methods.isInt;
import static misc.Methods.setSheetWidth;
import static misc.Variables.*;

/**The delete class only contains one method that delete a specified row by the user*/
public class Delete {
    /**An optional parameter to hold all the arguments*/
    @Parameter()
    static List<String> positionSTR = new ArrayList<>();

    /**Integer to hold the row number the user want to delete, Beginning from 0 NOT 1*/
    static int position;

    /**
     * The method that is responsible to delete the row specified by the user,
     * it exits without any action in case the user entered:<BR><BR>
     * 1)null OR empty string <BR>
     * 2)A String <BR>
     * 3)An Integer that is less than 1 <BR>
     * 4)An Integer that is more than the number of rows <BR>
     */
    public static void main(String[] args) throws IOException {

        //parse any arguments that was passed to the Delete class with Jcommander
        Delete parser = new Delete();
        new JCommander(parser, args);
        
        //If the user didn't pass an argument, or entered a string,
        //inform him with the error and return to the DynamicSheet class
        if ( positionSTR.get(0).isEmpty() || !isInt(positionSTR.get(0)) ) {
            System.err.println("<RowNumber> argument NOT found");
            System.out.println("Usage:\ndelete <RowNumber>");
            return;
        }
        //so, the user entered an int. convert it to an int var and decrease
        //by one, so if the user want to delete the first row, the var will be [0]
        else {
            position = Integer.parseInt(positionSTR.get(0));
            position--;

            //If the user didn't enter a valid int, print error & exit
            if ( position - 1 > noOfRows || position < 0) {
                System.err.println("Please enter a valid row number");
                return;
            }
            //If the user entered a valid int, shift all the cells 
            //from that position one step up to delete that whole row
            else {
                for (int i = 0; i < noOfColumns; i++) {
                    for (int j = position; j < noOfRows-1; j++) {
                        strArr[i][j] = strArr[i] [j + 1];
                        intArr[i][j] = intArr[i] [j + 1];
                    }
                }
            }
        }

        //Decrease the number of rows
        noOfRows--;
        //Calibrate all column width & print
        setSheetWidth();
        Print.main();
    }
}
