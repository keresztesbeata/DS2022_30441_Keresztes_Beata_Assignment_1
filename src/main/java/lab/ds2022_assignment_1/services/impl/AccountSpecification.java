package lab.ds2022_assignment_1.services.impl;

import lab.ds2022_assignment_1.controllers.handlers.requests.SearchCriteria;
import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.UserRole;
import lab.ds2022_assignment_1.model.exceptions.InvalidFilterException;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountSpecification implements Specification<Account>, FilterValidator {
    private final SearchCriteria searchCriteria;
    private final Map<String, String> dictionary;

    private enum FilterableField {
        ID("id"),
        NAME("name"),
        USERNAME("username"),
        ROLE("role");

        @Getter
        private final String value;

        FilterableField(String value) {
            this.value = value;
        }
    }

    private static final String INVALID_FILTER_ERR_MSG = "Invalid filter!";

    public AccountSpecification(SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
        this.dictionary = Arrays.stream(FilterableField.values())
                .collect(Collectors.toMap(FilterableField::getValue, FilterableField::name));
    }

    @Override
    public void validate(SearchCriteria searchCriteria) throws InvalidFilterException {
        if (Arrays.stream(FilterableField.values()).noneMatch(field -> field.getValue().equals(searchCriteria.getFilterKey()))) {
            throw new InvalidFilterException(searchCriteria.getFilterKey(), INVALID_FILTER_ERR_MSG);
        }
    }

    @Override
    public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final String searchStr = searchCriteria.getFilterValue().toLowerCase();

        switch (FilterableField.valueOf(dictionary.get(searchCriteria.getFilterKey()))) {
            case ID -> {
                return criteriaBuilder.equal(root.get(searchCriteria.getFilterKey()), UUID.fromString(searchStr));
            }
            case ROLE -> {
                return criteriaBuilder.equal(root.get(searchCriteria.getFilterKey()), UserRole.valueOf(searchStr.toUpperCase()));
            }
            default -> {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
                        "%" + searchStr + "%");
            }
        }
    }
}
