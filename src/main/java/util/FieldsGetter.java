package util;

import data.Address;
import data.Coordinates;
import data.Location;
import data.OrganizationType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Class is used to receive fields
 */
public class FieldsGetter implements FieldsReceiver {

    private final Console console;

    public FieldsGetter(Console aConsole) {
        console = aConsole;
    }

    /**
     * @return null - if end of script or empty String
     */
    private Object workWithTypes(String line, TypeOfArgument type, boolean minExist, boolean nullAvailable) {

        try {
            switch (type) {
                case INTEGER: {
                    if (line != null) Integer.parseInt(line);
                    else if (nullAvailable) return null;
                    break;
                }
                case DOUBLE: {
                    if (line != null) Double.parseDouble(line);
                    else if (nullAvailable) return null;
                    break;
                }
                case LONG: {
                    if (line != null) Long.parseLong(line);
                    else if (nullAvailable) return null;
                    break;
                }
                case FLOAT:
                    if (line != null) Float.parseFloat(line);
                    else if (nullAvailable) return null;
                    break;
                case STRING: {

                    if (line != null) return line;
                    break;
                }
            }
        } catch (NumberFormatException e) {
            line = null;
        }

        if (line != null) {
            switch (type) {

                case INTEGER: {
                    if (!minExist || Integer.parseInt(line) > 0) return Integer.parseInt(line);
                    break;
                }
                case DOUBLE: {
                    if (!minExist || Double.parseDouble(line) > 0) return Double.parseDouble(line);
                    break;
                }
                case FLOAT:
                    if (!minExist || Float.parseFloat(line) > 0) return Float.parseFloat(line);
                    break;
                case LONG: {
                    if (!minExist || Long.parseLong(line) > 0) return Long.parseLong(line);
                    break;
                }
            }
        }

        return null;
    }

    private String getUniversalRequest(String requestField, String options, Console console, boolean nullAvailable) throws IOException {

        do {
            StringBuilder sb = new StringBuilder();
            sb.append("\t").append(Text.getRedText(Text.getRedText(requestField))).
                    append(Text.getRedText(" should be ")).append(Text.getRedText(options)).
                    append(Text.getRedText("!\n"));
            sb.append("Enter ").append(requestField).append(" again: ");
            console.print(sb, true);

            String line;

            line = console.read();

            if (line == null && console.getExeStatus()) return null;
            if (line != null || nullAvailable) return line;
        } while (true);
    }

    private Object getGeneralRequest(String requestField, String options,
                                     Console console, boolean minExist, boolean nullAvailable, TypeOfArgument type) {

        StringBuilder sb = new StringBuilder();

        sb.append(requestField).append("\n");

        if (nullAvailable) sb.append("\t").append(Text.getRedText("(You can skip this field)\n"));
        sb.append("Enter ").append(Text.getBlueText(requestField)).append(Text.getBlueText(": "));
        console.print(sb, true);

        String line;
        try {
            line = console.read();
        } catch (IOException exception) {
            return null;
        }

        if (line == null) return null;
        else if (line.trim().equals("")) line = null;

        Object firstRequest = workWithTypes(line, type, minExist, nullAvailable);

        if (firstRequest != null || (nullAvailable && line == null)) return firstRequest;

        do {

            try {
                line = getUniversalRequest(requestField, options, console, nullAvailable);
            } catch (IOException exception) {
                return null;
            }

            if (line == null) return null;
            else if (line.trim().equals("")) line = null;

            Object request = workWithTypes(line, type, minExist, nullAvailable);

            if (request != null || (nullAvailable && line == null)) return request;

        } while (true);
    }


    private String getFirstEnumRequest(String requestField, String enumerateList, Console console) {

        StringBuilder sb = new StringBuilder();
        sb.append(requestField).append("\n");
        sb.append("Available ").append(requestField).append(":\n");
        sb.append(Text.getGreenText(enumerateList));
        sb.append("\n\nEnter ").append(Text.getBlueText(requestField)).append(Text.getBlueText(": "));
        console.print(sb, true);

        try {
            return console.read();
        } catch (IOException exception) {
            return null;
        }
    }

    private String getUniversalEnumRequest(String requestField, Console console) {
        StringBuilder sb = new StringBuilder();
        sb.append(Text.getRedText("It's incorrect ")).
                append(Text.getRedText(requestField)).append(Text.getRedText("!"));
        sb.append("\nEnter ").append(requestField).append(" again: ");
        console.print(sb, true);

        try {
            return console.read();
        } catch (IOException exception) {
            return null;
        }
    }

    @Override
    public LocalDate creationDate() {
        return null;
    }

    @Override
    public String getName() {

        return (String) getGeneralRequest("Organization name", "not null and not empty string",
                console, false, false, TypeOfArgument.STRING);
    }

    @Override
    public Coordinates getCoordinates() {

        Double x = (Double) getGeneralRequest("x coordinate",
                "not null int number", console, false, false, TypeOfArgument.DOUBLE);
        if (x == null) return null;

        Float y = (Float) getGeneralRequest("y coordinate", "not null Double number", console,
                false, false, TypeOfArgument.FLOAT);
        if (y == null) return null;

        return new Coordinates(x, y);
    }

    @Override
    public Double getAnnualTurnover()  {
        return (Double) getGeneralRequest("Annual Turnover" ,
                "positive Double" , console , true , false , TypeOfArgument.DOUBLE);
    }

    @Override
    public String getFullName() {
        return (String) getGeneralRequest("Organization fullname", "not null and not empty string",
                console, false, false, TypeOfArgument.STRING);
    }

    @Override
    public OrganizationType getType() throws IllegalArgumentException {
        String line = getFirstEnumRequest("organization types", Arrays.toString(OrganizationType.values())
                + Text.getBlueText("\n\tYou can write form of education in lower case!")
                + Text.getBlueText("\n\t(You can skip this field)"), console);
        if (line == null || line.trim().equals("")) return null;

        while (true) {

            if (line != null && line.trim().equals("")) line = null;
            if (line == null) return null;

            if (OrganizationType.includeTypes(line.toUpperCase()))
                return OrganizationType.valueOf(line.toUpperCase());

            line = getUniversalEnumRequest("form of education", console);
        }
    }

    @Override
    public Address postalAddress() {


        String street = (String) getGeneralRequest("postal Address street" , "not null" , console , false , false , TypeOfArgument.STRING);
        if (street == null) return null;

        String zipCode = (String) getGeneralRequest("postal Address zipCode" , "not null" , console , false , false , TypeOfArgument.STRING);
        if (zipCode == null) return null;

        Location town = getTown();

        return new Address(street , zipCode , town);
    }

    @Override
    public Location getTown() throws NullPointerException {


        Long x = (Long) getGeneralRequest("Location x", "not null Long number" , console , false , false , TypeOfArgument.LONG);
        if (x == null) return null;

        Double y = (Double) getGeneralRequest("Location y", "not null Double number"  ,console , false , false , TypeOfArgument.DOUBLE);
        if (y == null) return null;


        Double z = (Double) getGeneralRequest("Location z", "not null Double number"  ,console , false , false , TypeOfArgument.DOUBLE);
        if (z == null) return null;
        return new Location(x,y,z);
    }

    @Override
    public Integer getEmployeesCount() {

        return (Integer) getGeneralRequest( "Employees Count" , "not null positive Integer number" ,
                console , true ,false , TypeOfArgument.INTEGER);
    }




}