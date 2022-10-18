package lab.ds2022_assignment_1.services.impl;

import lab.ds2022_assignment_1.controllers.handlers.requests.SearchCriteria;
import lab.ds2022_assignment_1.model.exceptions.InvalidFilterException;

public interface FilterValidator {
    void validate(SearchCriteria searchCriteria) throws InvalidFilterException;
}
