package util;

import data.*;


import java.io.IOException;
import java.time.LocalDate;

/**
 * Class for creating Organization without id and Date
 */
public class OrganizationFactory {

    private final FieldsReceiver fieldsReceiver;

    public OrganizationFactory() {

        fieldsReceiver = new FieldsGetter(Console.getInstance());
    }


    public Organization createOrganization() {
        try {
            String name = fieldsReceiver.getName();
            System.out.println(name);
            Coordinates coordinates = fieldsReceiver.getCoordinates();
            System.out.println(coordinates.getX());
            System.out.println(coordinates.getY());
            LocalDate creationDate = fieldsReceiver.creationDate();
            System.out.println(creationDate);
            Double annualTurnover = fieldsReceiver.getAnnualTurnover();
            System.out.println(annualTurnover);
            String fullName = fieldsReceiver.getFullName();
            System.out.println(fullName);
            int employeesCount = fieldsReceiver.getEmployeesCount();
            System.out.println(employeesCount);
            OrganizationType type = fieldsReceiver.getType();
            System.out.println(type);
            Address postalAddress = fieldsReceiver.postalAddress();
            System.out.println(postalAddress.getStreet());
            System.out.println(postalAddress.getZipCode());
            System.out.println(postalAddress.getTown().getX());
            System.out.println(postalAddress.getTown().getY());
            System.out.println(postalAddress.getTown().getY());
            if (name != null && coordinates != null && annualTurnover != null && fullName != null && employeesCount > 0 && postalAddress != null) {
                return new Organization(name, coordinates, creationDate, annualTurnover, fullName, employeesCount, type, postalAddress);
            }
        } catch (IOException e) {
            System.out.println(Text.getRedText("Input or Output error!"));
            return null;
        }
        return null;
    }
}