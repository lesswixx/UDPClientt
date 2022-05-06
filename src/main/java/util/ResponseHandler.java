package util;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Class to response processing
 */
public class ResponseHandler{

    private ResponseHandler() {
    }



    public static String receive(ByteBuffer buffer) {
        try {
            ObjectInputStream inObj = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            Response response = (Response) inObj.readObject();
            return response.toString();
        } catch (ClassNotFoundException e) {
            return Text.getRedText("Server version is unsupported!\n");
        } catch (InvalidClassException e) {
            return Text.getRedText("Your version is outdated!\n");
        } catch (IOException e) {
            return Text.getRedText("Response is broken! Try again!\n");
        }
    }

    public static String receive(String errorInformation) {
        return errorInformation;
    }
}