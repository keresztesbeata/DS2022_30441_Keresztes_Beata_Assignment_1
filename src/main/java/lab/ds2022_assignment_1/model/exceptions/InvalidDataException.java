package lab.ds2022_assignment_1.model.exceptions;

public class InvalidDataException extends Exception{
    public InvalidDataException() {
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
