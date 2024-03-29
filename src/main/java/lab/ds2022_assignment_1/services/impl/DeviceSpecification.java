package lab.ds2022_assignment_1.services.impl;

import lab.ds2022_assignment_1.controllers.handlers.requests.SearchCriteria;
import lab.ds2022_assignment_1.model.entities.Device;
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static lab.ds2022_assignment_1.controllers.Constants.UUID_REGEX;

public class DeviceSpecification implements Specification<Device>, FilterValidator {
    private final SearchCriteria searchCriteria;

    private final Map<String, String> dictionary;

    private enum FilterableField {
        ID("id"),
        ADDRESS("address"),
        DESCRIPTION("description"),
        ACCOUNT_ID("accountId");

        @Getter
        private final String value;

        FilterableField(String value) {
            this.value = value;
        }
    }

    private static final String INVALID_FILTER_NAME_ERR_MSG = "Invalid filter name!";
    private static final String INVALID_FILTER_VALUE_ERR_MSG = "Invalid filter value!";
    private static final Pattern UUID_PATTERN = Pattern.compile(UUID_REGEX);

    public DeviceSpecification(SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
        this.dictionary = Arrays.stream(FilterableField.values())
                .collect(Collectors.toMap(FilterableField::getValue, FilterableField::name));
    }

    @Override
    public void validate(SearchCriteria searchCriteria) throws InvalidFilterException {
        if (Arrays.stream(FilterableField.values()).noneMatch(field -> field.getValue().equals(searchCriteria.getFilterKey()))) {
            throw new InvalidFilterException(searchCriteria.getFilterKey(), INVALID_FILTER_NAME_ERR_MSG + " The entity has no such field.");
        }
        if (FilterableField.ACCOUNT_ID.equals(FilterableField.valueOf(dictionary.get(searchCriteria.getFilterKey()))) ||
                FilterableField.ID.equals(FilterableField.valueOf(dictionary.get(searchCriteria.getFilterKey())))) {

            if (!UUID_PATTERN.matcher(searchCriteria.getFilterValue()).matches()) {
                throw new InvalidFilterException(searchCriteria.getFilterKey(), INVALID_FILTER_VALUE_ERR_MSG + " Not a valid uuid!");
            }
        }
    }

    @Override
    public Predicate toPredicate(Root<Device> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final String searchStr = searchCriteria.getFilterValue().toLowerCase();

        switch (FilterableField.valueOf(dictionary.get(searchCriteria.getFilterKey()))) {
            case ACCOUNT_ID -> {
                return criteriaBuilder.equal(root.join("account").get("id"),
                        UUID.fromString(searchStr));
            }
            case ID -> {
                return criteriaBuilder.equal(root.get(searchCriteria.getFilterKey()), UUID.fromString(searchStr));
            }
            default -> {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
                        "%" + searchStr + "%");
            }
        }
    }
}
