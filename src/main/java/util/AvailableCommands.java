package util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for storage and sort available commands
 */
public class AvailableCommands {

    public final Set<String> noArgumentCommands = new HashSet<>();
    public final Set<String> numArgumentCommands = new HashSet<>();
    public final Set<String> stringArgumentCommands = new HashSet<>();
    public final Set<String> objectArgumentCommands = new HashSet<>();
    public final String scriptArgumentCommand;
    public final String objAndNumArgumentCommand;

    public AvailableCommands() {

        Collections.addAll(noArgumentCommands,
                "help",
                "info",
                "show",
                "clear",
                "history",
                "sum_of_annual_turnover",
                "min_by_creation_date");

        Collections.addAll(numArgumentCommands,
                "remove_by_id",
                "remove_at");



        Collections.addAll(objectArgumentCommands,
                "add",
                "add_if_max",
                "add_if_min","insert_at");

        scriptArgumentCommand = "execute_script";

        objAndNumArgumentCommand = "update";
    }
}