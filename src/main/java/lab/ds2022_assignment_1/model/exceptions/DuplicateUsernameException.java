package lab.ds2022_assignment_1.model.exceptions;

public class DuplicateUsernameException extends Exception {

    public DuplicateUsernameException() {
    }

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
