package lesson07.hw.mapper;

/**
 * Создает SQL - запросы
 */
interface EntitySQLMetaData {

    String getSelectAllSql()

    String getSelectByIdSql(long id)

    String getInsertSql(Map<String, String> values)

    String getUpdateSql(Map<String, String> fields, long id)
}
