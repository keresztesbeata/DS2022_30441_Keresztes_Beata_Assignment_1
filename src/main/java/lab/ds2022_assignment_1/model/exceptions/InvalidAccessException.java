package lab.ds2022_assignment_1.model.exceptions;

public class InvalidAccessException extends Exception{
    public InvalidAccessException() {
    }

    public InvalidAccessException(String message) {
        super(message);
    }
}
