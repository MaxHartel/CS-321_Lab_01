/**
*Indicates a user input error
*@author Max Hartel
*/
public class InvalidUserInputException extends RuntimeException {
    public InvalidUserInputException(String msg) {
        super(msg);
    }
}