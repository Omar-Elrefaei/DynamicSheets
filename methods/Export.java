package methods;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static misc.Methods.isInt;
import static misc.Variables.*;

/**
 * Created by Omar H.Elrefaei on 4/20/2017.
 */
public class Export {
    public static int columnNo;

    @Parameter()
    static List<String> arguments = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        Export parser = new Export();
        new JCommander(parser, args);

        if ( arguments.get(0).isEmpty() ) columnNo = 0;
        else if ( isInt(arguments.get(0)) ) {
            if ( Integer.parseInt(arguments.get(0)) <= noOfColumns )
                columnNo = Integer.parseInt(arguments.get(0));
            else {
                System.err.println("Error, please enter a valid argument");
                return;
            }
        }
        else {
            System.err.println("Error, please enter a valid argument");
            return;
        }
        export();
    }

    static void export() throws IOException {
        //Read the file name from the user
        System.out.println("Enter file name");
        input = consRead.readLine();
        
        //Create a .txt file with the user desired name in the desktop directory
        String home = System.getProperty("user.home");
        String slash = System.getProperty("file.separator");
        String path  = home + slash + "Desktop" + slash + input + ".txt";
        PrintWriter exportFile = new PrintWriter(path);
        System.out.println("Your file was created at:\n>>> " + path);

        if ( columnNo == 0 ) {
            //Printing columns types
            exportFile.print("Column types = {");
            for (int i = 0; i < noOfColumns; i++) {
                
                if (index[i][0] == 0) exportFile.print("str");
                if (index[i][0] == 1) exportFile.print("int");
                
                if (i != noOfColumns - 1 ) exportFile.print(", ");
                
            }exportFile.print("}\n");
            
            //Printing columns names
            exportFile.print("Column names = {");
            for (int i = 0; i < noOfColumns; i++) {
                
                exportFile.print(columnNames[i]);
                if (i != noOfColumns - 1 ) exportFile.print(", ");
            } 
            exportFile.print("}\n\n");



            for (int i = 0; i < noOfRows ; i++) {
                for (int j = 0; j < noOfColumns; j++) {

                    if (index[j][0] == 1){
                        exportFile.printf("%1$-" + columnWidth[j] + "s", intArr[index[j][1]] [i]);
                        exportFile.print("|");

                    }
                    else if (index[j][0] == 0){
                        exportFile.printf("%1$-" + columnWidth[j] + "s", strArr[index[j][1]] [i]);
                        exportFile.print("|");
                    }
                }
               exportFile.println("");
            }
        }//if the user entered a  number for a specific column
        else if ( columnNo > 0 ) {

            for (int i = 0; i < noOfRows; i++) {
                if ( index[columnNo - 1][0] == 1 ) {
                    exportFile.printf("%1$-" + columnWidth[columnNo - 1] + "s", intArr[index[columnNo - 1][1]][i]);
                } else if ( index[columnNo - 1][0] == 0 ) {
                    exportFile.printf("%1$-" + columnWidth[columnNo - 1] + "s", strArr[index[columnNo - 1][1]][i]);
                }
                exportFile.println("");
            }

        }
        exportFile.close();
    }
}
