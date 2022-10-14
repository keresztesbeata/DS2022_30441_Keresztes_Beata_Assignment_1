package lab.ds2022_assignment_1.dtos.mappers;

/**
 * Mapper for converting between entities, dto-s and request body.
 *
 * @param <T> the entity type
 * @param <U> the request body type
 * @param <R> the dto type
 */
public interface Mapper<T, U, R> {
    T mapToEntity(U data);

    R mapToDto(T entity);
}
