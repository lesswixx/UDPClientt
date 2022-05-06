package util;

import data.Organization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The class that serves the command
 */
public class CommandManager {

    private final CommandReader commandReader;
    private final Validator validator;
    private final RequestHandler requestHandler;
    private final Console console;
    private final Set<String> usedScripts;
    private final OrganizationFactory organizationFactory;


    public CommandManager(CommandReader aCommandReader) {

        commandReader = aCommandReader;
        validator = Validator.getInstance();
        requestHandler = RequestHandler.getInstance();
        console = Console.getInstance();
        usedScripts = new HashSet<>();
        organizationFactory = new OrganizationFactory();
    }


    public void transferCommand(Request aCommand) {

        if (validator.notObjectArgumentCommands(aCommand))
            console.print(RequestHandler.getInstance().send(aCommand) + "\n");

        else if (validator.objectArgumentCommands(aCommand)) {
            console.print(RequestHandler.getInstance().send(aCommand, organizationFactory.createOrganization()) + "\n");
        } else if (validator.validateScriptArgumentCommand(aCommand)) {
            executeScript(aCommand.getArg());
        } else {
            console.print(Text.getRedText("Command entered incorrectly!\n"));
        }
    }

    private void executeScript(String scriptName) {

        if (usedScripts.add(scriptName)) {
            try {
                if (usedScripts.size() == 1) console.setExeStatus(true);

                ScriptReader scriptReader = new ScriptReader(this, commandReader, new File(scriptName));
                try {
                    scriptReader.read();

                    System.out.println(Text.getGreenText("\nThe script " + scriptName
                            + " was processed successfully!\n"));
                } catch (IOException exception) {

                    usedScripts.remove(scriptName);

                    if (usedScripts.size() == 0) console.setExeStatus(false);

                    if (!new File(scriptName).exists()) console.print(
                            Text.getRedText("\nThe script does not exist!\n"));
                    else if (!new File(scriptName).canRead()) console.print(
                            Text.getRedText("\nThe system does not have permission to read the file!\n"));
                    else console.print("\nWe have some problem's with script!\n");
                }

                usedScripts.remove(scriptName);

                if (usedScripts.size() == 0) console.setExeStatus(false);

            } catch (FileNotFoundException e) {
                console.print("\nScript not found!\n");
            }
        } else console.print(Text.getRedText("\nRecursion has been detected! Script " + scriptName +
                " will not be ran!\n"));
    }
}