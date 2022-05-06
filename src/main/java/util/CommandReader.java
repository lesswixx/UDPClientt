package util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CommandReader read and parse strings from input to Request object
 */
public class CommandReader {

    private final Console console;
    private final CommandManager commandManager;


    public CommandReader() {

        console = Console.getInstance();
        commandManager = new CommandManager(this);
    }

    public boolean enable() throws IOException{

        while (true) {

            if (!RequestHandler.getInstance().getSocketStatus()) return false;

            String line;
            console.print("Enter the command: ");
            try {
                line = console.read();
            } catch (IOException exception) {
                line = null;
            }

            Request newCommand = readCommand(line);
            if (newCommand != null) {
                if (newCommand.getCommand().equals("exit") && newCommand.getArg() == null) {
                    console.print(Text.getGreenText("Have a nice day!\n"));
                    return true;
                }
                commandManager.transferCommand(newCommand);
            } else {
                console.print(Text.getRedText("Command entered incorrectly!\n"));
            }
        }
    }

    public Request readCommand(String anInputString) {

        if (anInputString == null) return null;

        String command;
        String arg;
        Pattern commandName = Pattern.compile("^\\w+\\s+");
        Pattern argName = Pattern.compile("^.+");

        Matcher matcher = commandName.matcher(anInputString + " ");

        if (matcher.find()) {
            command = matcher.group().trim();
        } else {
            return null;
        }

        matcher = argName.matcher(anInputString.substring(command.length()));

        if (matcher.find()) {
            arg = matcher.group().trim();
            if (arg.equals("")) arg = null;
        } else arg = null;

        return new Request(command, arg);
    }
}