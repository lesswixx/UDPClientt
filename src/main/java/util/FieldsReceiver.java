package util;

import data.*;

import java.io.IOException;
import java.time.LocalDate;


public interface FieldsReceiver {
    LocalDate creationDate();
    String getName() throws IOException;

    Coordinates getCoordinates() throws IOException;

    Double getAnnualTurnover() throws IOException;

    String getFullName() throws IOException;

    OrganizationType getType() throws IOException ,IllegalArgumentException;

    Address postalAddress() throws IOException;

    Location getTown() throws IOException,NullPointerException;

    Integer getEmployeesCount() throws IOException;


}