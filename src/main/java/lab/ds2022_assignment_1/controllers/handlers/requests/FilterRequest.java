package lab.ds2022_assignment_1.controllers.handlers.requests;

import lombok.Data;

@Data
public class FilterRequest {
    private String field;
    private String value;
}
