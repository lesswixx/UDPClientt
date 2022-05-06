package util;

/**
 * Class to validate commands
 */
public class Validator {

    private static AvailableCommands availableCommands;
    private static Validator instance;

    private Validator() {
    }

    public static Validator getInstance() {
        if (instance == null) {
            availableCommands = new AvailableCommands();
            instance = new Validator();
        }
        return instance;
    }

    public boolean notObjectArgumentCommands(Request aCommand) {
        return validateNoArgumentCommand(aCommand) ||
                validateNumArgumentCommands(aCommand) ||
                validateStringArgumentCommands(aCommand);
    }

    public boolean objectArgumentCommands(Request aCommand) {
        return validateObjectArgumentCommands(aCommand) ||
                validateObjAndNumArgumentCommand(aCommand);
    }

    private boolean validateNoArgumentCommand(Request aCommand) {
        return availableCommands.noArgumentCommands.contains(aCommand.getCommand()) &&
                aCommand.getArg() == null;
    }

    private boolean validateNumArgumentCommands(Request aCommand) {
        return availableCommands.numArgumentCommands.contains(aCommand.getCommand()) &&
                aCommand.isArgInt() && Integer.parseInt(aCommand.getArg()) > 0;
    }

    private boolean validateStringArgumentCommands(Request aCommand) {
        return availableCommands.stringArgumentCommands.contains(aCommand.getCommand()) &&
                aCommand.getArg() != null;
    }

    private boolean validateObjectArgumentCommands(Request aCommand) {
        return availableCommands.objectArgumentCommands.contains(aCommand.getCommand()) &&
                aCommand.getArg() == null;
    }

    private boolean validateObjAndNumArgumentCommand(Request aCommand) {
        return availableCommands.objAndNumArgumentCommand.contains(aCommand.getCommand()) &&
                aCommand.isArgInt() && Integer.parseInt(aCommand.getArg()) > 0;
    }

    public boolean validateScriptArgumentCommand(Request aCommand) {
        return availableCommands.scriptArgumentCommand.equals(aCommand.getCommand()) &&
                aCommand.getArg() != null;
    }
}