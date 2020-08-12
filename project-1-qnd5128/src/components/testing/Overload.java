package components.testing;

import components.*;
import components.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class read in a file and create new objects of all the components. Then take the user input
 *
 * @author Nhu Quynh Duong, qnd5128
 */
public class Overload {

    public static final int BAD_ARGS = 1;
    public static final int FILE_NOT_FOUND = 2;
    public static final int BAD_FILE_FORMAT = 3;
    public static final int UNKNOWN_COMPONENT = 4;
    public static final int REPEAT_NAME = 5;
    public static final int UNKNOWN_COMPONENT_TYPE = 6;
    public static final int UNKNOWN_USER_COMMAND = 7;
    public static final int UNSWITCHABLE_COMPONENT = 8;

    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String[] NO_STRINGS = new String[0];

    private static final String PROMPT = "? ";

    HashMap<String, Component> comps = new HashMap<>();

    static {
        Reporter.addError(
                BAD_ARGS, "Usage: java components.Overload <configFile>");
        Reporter.addError(FILE_NOT_FOUND, "Config file not found");
        Reporter.addError(BAD_FILE_FORMAT, "Error in config file");
        Reporter.addError(
                UNKNOWN_COMPONENT,
                "Reference to unknown component in config file"
        );
        Reporter.addError(
                REPEAT_NAME,
                "Component name repeated in config file"
        );
        Reporter.addError(
                UNKNOWN_COMPONENT_TYPE,
                "Reference to unknown type of component in config file"
        );
        Reporter.addError(
                UNKNOWN_USER_COMMAND,
                "Unknown user command"
        );
    }

    /**
     * Read the file and create new objects for all components and store them in a hashMap
     * @param args command line arguments
     */
    public void read(String args) {
        try {
            File file = new File(args);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] brokenLine = line.split(WHITESPACE_REGEX);
                String firstWord = brokenLine[0];
                if (firstWord.equals("PowerSource")) {
                    comps.put(brokenLine[1], new PowerSource(brokenLine[1]));

                } else if (firstWord.equals("CircuitBreaker")) {
                    comps.put(brokenLine[1], new CircuitBreaker(brokenLine[1], comps.get(brokenLine[2]),
                            Integer.parseInt(brokenLine[3])));

                } else if (firstWord.equals("Outlet")) {
                    comps.put(brokenLine[1], new Outlet(brokenLine[1], (CircuitBreaker) comps.get(brokenLine[2])));
//
                } else if (firstWord.equals("Appliance")) {
                    comps.put(brokenLine[1], new Appliance(brokenLine[1], comps.get(brokenLine[2]),
                            Integer.parseInt(brokenLine[3])));
                } else {
                    Reporter.usageError(UNKNOWN_COMPONENT_TYPE);
                }
            }
            for (Component cmp : comps.values()) {
                if (cmp instanceof PowerSource) {
                    ((PowerSource) cmp).engage();
                }
            }



        } catch (FileNotFoundException ex) {
            Reporter.usageError(FILE_NOT_FOUND);
        }
    }

    /**
     * Take the user input to do the commands the user wants
     */
    public void userInput() {
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.print(PROMPT);
            String input = in.nextLine();
            String[] inputs = input.split(" ");
            if (inputs[0].equals("toggle")) {
                if (comps.containsKey(inputs[1])) {
                    if ((comps.get(inputs[1]) instanceof Appliance) || (comps.get(inputs[1]) instanceof CircuitBreaker)) {
                        comps.get(inputs[1]).toggle();
                    }
                    else{
                        Reporter.usageError(UNSWITCHABLE_COMPONENT);
                    }
                }

            }

            else if (inputs[0].equals("connect")) {
                if (comps.containsKey(inputs[3])) {
                    if (inputs[1].equals("Appliance")) {
                        Appliance app = new Appliance(inputs[2], comps.get(inputs[3]), Integer.parseInt(inputs[4]));
                        comps.put(inputs[2], app);

                    }
                }
                else {
                    Reporter.usageError(UNKNOWN_COMPONENT);
                }

            }

            else if (inputs[0].equals("display")) {
                for (Component cmp : comps.values()) {
                    if (cmp instanceof PowerSource) {
                        ((PowerSource) cmp).display();
                    }
                }

            }

            else if (inputs[0].equals("quit")) {
                break;
            }

            else {
                Reporter.usageError(UNKNOWN_USER_COMMAND);
            }
        }
    }


    /**
     * Execute the program
     * create a new instance of Overload to call the functions
     * @param args command line arguments
     */
    public static void main( String[] args ) {
        System.out.println( "Overload Project, CS2" );
        Overload a = new Overload();
        if(args.length < 1) {
            Reporter.usageError(BAD_ARGS);
        }
        a.read(args[0]);
        a.userInput();

    }

}
