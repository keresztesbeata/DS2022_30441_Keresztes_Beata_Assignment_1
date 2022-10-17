package lab.ds2022_assignment_1.model.exceptions;

import lombok.Getter;

public class InvalidFilterException extends Exception{
    @Getter
    private String field;

    public InvalidFilterException() {
    }

    public InvalidFilterException(String message) {
        super(message);
    }

    public InvalidFilterException(String field, String message) {
        super(message);
        this.field = field;
    }
}
