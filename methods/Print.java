package methods;

import static misc.Variables.*;

/**Print the whole data sheet to the (standard output)*/
public class Print {
    /**The main method that print the whole data sheet to the (standard output)*/
    public static void main (){

        //Loop through the number of rows
        for (int i = 0; i < noOfRows ; i++) {
            System.out.print("|");
                    /*Effectively looping through each cell in
                    the previously selected rows and printing it*/
            for (int j = 0; j < noOfColumns; j++) {
                
                /*Check the cell type, and print it's value from
                the corresponding 2 dim. array (strArr/inrArr), using
                the (index) array to determine the column number in (strArr/inrArr)*/
                /*Use the columnWidth array in the (format) argument of the (printf) 
                method to maintain a consistent width across the whole column*/
                if (index[j][0] == 0){
                    System.out.printf("%1$-" + columnWidth[j] + "s", strArr[index[j][1]] [i]);
                    System.out.print("|");
                }
                else if (index[j][0] == 1){
                    System.out.printf("%1$-" + columnWidth[j] + "s", intArr[index[j][1]] [i]);
                    System.out.print("|");
                }
            }
            System.out.println("");
        }
    }
}
        
