package misc;

import static misc.Variables.*;

/**
 * A class that contain misalliance methods that are called in various parts of the program
 */
public class Methods {
    /** Calculate the minimum possible width for all columns depending on the
     * largest cell in the column, and<br> save the value the in <i>columnWidth</i> 
     * array which will be called when printing that column*/
    public static void setSheetWidth(){
        //Loop through all columns
        for (int i = 0; i < noOfColumns; i++) {
            //A temporary var
            int maxColumnWidthTemp = 0;

            //Loop (noOfRows) times in the selected column, as these are the cells that contain data in it
            for (int j = 0; j < noOfRows; j++) {

                //Get the column no. in (strArr OR intArr) by calling (index[columnNumber][1])
                //Save the literal string representation of the cell value in literalSTR
                String literalSTR  = String.valueOf( strArr[  index[i][1]  ] [j] );

                //Compare (literalSTR.length) with the largest cell width found
                maxColumnWidthTemp = Integer.max(maxColumnWidthTemp, literalSTR.length());
            }
            //Save the final value in columnWidth array
            columnWidth[i] = maxColumnWidthTemp;
        }
    }
    
    /**print a specific row to the standard out*/
    public static void printRow(int rowNo) {

        for (int j = 0; j < noOfColumns; j++) {

            if (index[j][0] == 1){
                System.out.printf("%1$-" + columnWidth[j] + "s", intArr[index[j][1]] [rowNo]);
                System.out.print("|");

            }
            else if (index[j][0] == 0){
                System.out.printf("%1$-" + columnWidth[j] + "s", strArr[index[j][1]] [rowNo]);
                System.out.print("|");
            }
        }
        System.out.println("");
    }
    
    /**Check the given string and return a (true) only if 
     * the string can be assigned to an int variable*/
    public static boolean isInt( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

}
