package lab.ds2022_assignment_1.model.exceptions;

public class NoLoggedInUserException extends Exception{
    public NoLoggedInUserException() {
    }

    public NoLoggedInUserException(String message) {
        super(message);
    }
}
