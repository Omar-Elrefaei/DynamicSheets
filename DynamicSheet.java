import methods.*;

import java.io.IOException;
import java.util.Arrays;

import static misc.Variables.*;
I am GIT
/**
 * The DynamicSheet class is the main class in the (DynamicSheet) program<br>
 * the class serve as the main node of the program that read user commands and arguments
 * and pass arguments to methods accordingly<br>
 * <b>Date: </b> 23/5/2017
 * @version 1.0
 * @author Omar Hatem Elrefaei
 */
public class DynamicSheet{

    //The main method that all the program live in
    public static void main (String [] args) throws IOException, InterruptedException {
        /*If this is your first time reading this code, I encourage you to check
          the "misc/Variables.java" class or at least open it beside you, as it contain
          the main variables that will be used in the whole program later and their usages*/
        
        //Fill the main arrays that will hold the user data with empty strings or zeros
        for(int i = 0; i < 1000; i++){
            Arrays.fill(strArr[i], "");
            Arrays.fill(intArr[i], 0);
        }
        
        //Display the available commands to the user
        System.out.println("Please choose an option: ");
        System.out.println(
                "import    -Import programs data manually or from file-\n"
                        + "add       -Add a program to the list-\n"
                        + "sort      -Sort by a specific column-\n"
                        + "search    -Search the sheet-\n"
                        + "modify    -Modify a certain row-\n"
                        + "delete    -Delete a program-\n"
                        + "print     -Print all the sheet-\n"
                        + "export    -Export to file-\n"
                        + "help      -Display this help message-\n"
                        + "exit\n");
        
        //Reading user input and calling appropriate methods, and passing the arguments to them
        mainLoop:
        while (true) {

            //Read user input
            input = consRead.readLine();

            //Split user input from the first " " to separate the command and the arguments
            String[] command = input.split(" ", 2);

            String[] arguments = null;

            //if the user only passed a command without any arguments, 
            // initialize (arguments) with an empty string
            if ( command.length == 1 )
                arguments = new String[]{""};
            
            //else, split the arguments from spaces and save them in (arguments)
            else if ( command.length == 2 )
                arguments = command[1].split(" ");


            //Launch methods depending on user input
            switch (command[0]) {
                case "import":
                    EnterData.main();
                    break;
                case "add":
                    Add.main(arguments);
                    break;
                case "sort":
                    Sort.main(arguments);
                    break;
                case "search":
                    Search.main(arguments);
                    break;
                case "modify":
                    Modify.main(arguments);
                    break;
                case "delete":
                    Delete.main(arguments);
                    break;
                case "print":
                    Print.main();
                    break;
                case "export":
                    Export.main(arguments);
                    break;
                case "exit":
                    break mainLoop;
                case "help":
                    System.out.println(
                            "import    -Import programs data manually or from file-\n"
                                    + "add       -Add a program to the list-\n"
                                    + "sort      -Sort by a specific column-\n"
                                    + "search    -Search the sheet-\n"
                                    + "modify    -Modify a certain row-\n"
                                    + "delete    -Delete a program-\n"
                                    + "print     -Print all the sheet-\n"
                                    + "export    -Export to file-\n" 
                                    + "help      -Display this help message-\n"
                                    + "exit\n");
                    break;
                default:
                    System.err.println("Sorry, \"" + input + "\" is NOT a valid action\n" +
                            "Type \"help\" to view the available commands");
                    break;
            }
        }
    }
}
