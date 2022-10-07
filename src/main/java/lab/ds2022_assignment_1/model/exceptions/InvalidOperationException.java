package lab.ds2022_assignment_1.model.exceptions;

public class InvalidOperationException extends Exception{
    public InvalidOperationException() {
    }

    public InvalidOperationException(String message) {
        super(message);
    }
}
