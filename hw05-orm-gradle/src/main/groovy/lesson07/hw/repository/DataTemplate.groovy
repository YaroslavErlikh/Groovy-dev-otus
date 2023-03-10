package lesson07.hw.repository
/**
 * CRUD operations with database (like JdbcTemplate)
 */
interface DataTemplate<T> {
    Optional<T> findById(connection, long id);

    List<T> findAll(connection);

    long insert(connection, T object);

    long update(connection, T object);
}
