package com.xspaceagi.compose.domain.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;
import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.compose.sdk.enums.TableFieldTypeEnum;
import com.xspaceagi.system.spec.enums.YnEnum;
import com.xspaceagi.system.spec.exception.ComposeException;

import lombok.extern.slf4j.Slf4j;

/**
 * Unit tests for {@link DorisTableDdlUtil}
 */
@Slf4j
class DorisTableDdlUtilTest {

    private CustomTableDefinitionModel tableModel;

    @BeforeEach
    void setUp() {
        tableModel = new CustomTableDefinitionModel();
        tableModel.setDorisDatabase("test_db");
        tableModel.setDorisTable("test_table");
        tableModel.setTableDescription("Test Table Description with 'quotes'");
    }

    // Helper method to create a field
    private CustomFieldDefinitionModel createField(String name, TableFieldTypeEnum type, String description,
            Integer nullableFlag, String defaultValue) {
        CustomFieldDefinitionModel field = CustomFieldDefinitionModel.builder()
                .fieldName(name)
                .fieldType(type.getCode())
                .fieldDescription(description)
                .nullableFlag(nullableFlag)
                .defaultValue(defaultValue)
                .enabledFlag(YnEnum.Y.getKey())
                .build();
        return field;
    }

    // --- Tests for buildCreateTableSql ---

    @Test
    @DisplayName("Build CREATE TABLE SQL for basic table")
    void testBuildCreateTableSql_Basic() {
        List<CustomFieldDefinitionModel> fields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "Primary Key", -1, null),
                createField("name", TableFieldTypeEnum.STRING, "User Name", 1, "default_user"), // Corrected default
                                                                                                // value format for test
                createField("created_at", TableFieldTypeEnum.DATE, "Creation timestamp", -1, null));

        String expectedSql = "CREATE TABLE IF NOT EXISTS `test_db`.`test_table` (\n" +
                "    `id` INT NOT NULL COMMENT 'Primary Key',\n" +
                "    `name` VARCHAR(255) NULL DEFAULT 'default_user' COMMENT 'User Name',\n" +
                "    `created_at` DATETIME NOT NULL COMMENT 'Creation timestamp'\n" +
                ") \n" +
                "ENGINE=OLAP\n" +
                "DUPLICATE KEY(`id`)\n" +
                "COMMENT 'Test Table Description with \\\'quotes\\\''\n" +
                "DISTRIBUTED BY HASH(`id`) BUCKETS 10\n" +
                "PROPERTIES (\n" +
                "    \"replication_num\" = \"3\"\n" +
                ")";

        String actualSql = DorisTableDdlUtil.buildCreateTableSql(tableModel, fields);
        // assertEquals normalizes line endings, trim() handles leading/trailing
        // whitespace
        assertEquals(expectedSql.trim(), actualSql.trim());
    }

    @Test
    @DisplayName("Build CREATE TABLE SQL with various default values")
    void testBuildCreateTableSql_DefaultValues() {
        List<CustomFieldDefinitionModel> fields = Arrays.asList(
                createField("pk", TableFieldTypeEnum.INTEGER, "PK", -1, null),
                createField("str_col", TableFieldTypeEnum.STRING, "String with default", 1, "hello world"),
                createField("int_col", TableFieldTypeEnum.INTEGER, "Int with default", 1, "123"),
                createField("num_col", TableFieldTypeEnum.NUMBER, "Decimal with default", 1, "99.99"),
                createField("bool_col_true", TableFieldTypeEnum.BOOLEAN, "Boolean true default", 1, "true"),
                createField("bool_col_false", TableFieldTypeEnum.BOOLEAN, "Boolean false default", 1, "0"),
                createField("null_col", TableFieldTypeEnum.STRING, "String with NULL default", 1, "NULL"));

        String expectedSql = "CREATE TABLE IF NOT EXISTS `test_db`.`test_table` (\n" +
                "    `pk` INT NOT NULL COMMENT 'PK',\n" +
                "    `str_col` VARCHAR(255) NULL DEFAULT 'hello world' COMMENT 'String with default',\n" +
                "    `int_col` INT NULL DEFAULT 123 COMMENT 'Int with default',\n" +
                "    `num_col` DECIMAL(20,6) NULL DEFAULT 99.99 COMMENT 'Decimal with default',\n" +
                "    `bool_col_true` TINYINT(1) NULL DEFAULT 1 COMMENT 'Boolean true default',\n" +
                "    `bool_col_false` TINYINT(1) NULL DEFAULT 0 COMMENT 'Boolean false default',\n" +
                "    `null_col` VARCHAR(255) NULL DEFAULT NULL COMMENT 'String with NULL default'\n" +
                ") \n" +
                "ENGINE=OLAP\n" +
                "DUPLICATE KEY(`pk`)\n" +
                "COMMENT 'Test Table Description with \\\'quotes\\\''\n" +
                "DISTRIBUTED BY HASH(`pk`) BUCKETS 10\n" +
                "PROPERTIES (\n" +
                "    \"replication_num\" = \"3\"\n" +
                ")";

        String actualSql = DorisTableDdlUtil.buildCreateTableSql(tableModel, fields);
        assertEquals(expectedSql.trim(), actualSql.trim());
    }

    @Test
    @DisplayName("Build CREATE TABLE SQL should throw exception for empty fields")
    void testBuildCreateTableSql_EmptyFields() {
        List<CustomFieldDefinitionModel> fields = Collections.emptyList();
        String expectedMessage = "数据不存在";
        ComposeException exception = assertThrows(ComposeException.class, () -> {
            DorisTableDdlUtil.buildCreateTableSql(tableModel, fields);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Build CREATE TABLE SQL should throw exception for null table name")
    void testBuildCreateTableSql_NullTableName() {
        tableModel.setDorisTable(null);
        List<CustomFieldDefinitionModel> fields = Collections.singletonList(
                createField("id", TableFieldTypeEnum.INTEGER, "ID", -1, null));
        String expectedMessage = "数据不存在";
        ComposeException exception = assertThrows(ComposeException.class, () -> {
            DorisTableDdlUtil.buildCreateTableSql(tableModel, fields);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    // --- Tests for buildAlterTableSqls ---

    @Test
    @DisplayName("Build ALTER TABLE SQL for adding a new column")
    void testBuildAlterTableSqls_AddColumn() {
        List<CustomFieldDefinitionModel> oldFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "PK", -1, null));
        List<CustomFieldDefinitionModel> newFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "PK", -1, null),
                createField("new_col", TableFieldTypeEnum.STRING, "Newly added column", 1, "new_val") // Corrected
                                                                                                      // default
        );

        List<String> expectedSqls = Collections.singletonList(
                "ALTER TABLE `test_db`.`test_table` ADD COLUMN `new_col` VARCHAR(255) NULL DEFAULT 'new_val' COMMENT 'Newly added column'");

        List<String> actualSqls = DorisTableDdlUtil.buildAlterTableSqls("test_db", "test_table", oldFields, newFields);
        assertEquals(expectedSqls, actualSqls);
    }

    @Test
    @DisplayName("Build ALTER TABLE SQL for modifying a column comment")
    void testBuildAlterTableSqls_ModifyComment() {
        List<CustomFieldDefinitionModel> oldFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "Old Comment", -1, null));
        List<CustomFieldDefinitionModel> newFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "New Comment 'with quote'", -1, null) // Changed comment
        );

        List<String> expectedSqls = Collections.singletonList(
                // Note: MODIFY requires the full definition
                "ALTER TABLE `test_db`.`test_table` MODIFY COLUMN `id` INT NOT NULL COMMENT 'New Comment \\\'with quote\\\''");

        List<String> actualSqls = DorisTableDdlUtil.buildAlterTableSqls("test_db", "test_table", oldFields, newFields);
        assertEquals(expectedSqls, actualSqls);
    }

    @Test
    @DisplayName("Build ALTER TABLE SQL for dropping a column")
    void testBuildAlterTableSqls_DropColumn() {
        List<CustomFieldDefinitionModel> oldFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "PK", -1, null),
                createField("to_drop", TableFieldTypeEnum.STRING, "This will be dropped", 1, null));
        List<CustomFieldDefinitionModel> newFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "PK", -1, null));

        List<String> expectedSqls = Collections.singletonList(
                "ALTER TABLE `test_db`.`test_table` DROP COLUMN `to_drop`");

        List<String> actualSqls = DorisTableDdlUtil.buildAlterTableSqls("test_db", "test_table", oldFields, newFields);
        assertEquals(expectedSqls, actualSqls);
    }

    @Test
    @DisplayName("Build ALTER TABLE SQL for adding, modifying comment, and dropping")
    void testBuildAlterTableSqls_AddModifyDrop() {
        List<CustomFieldDefinitionModel> oldFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "Old PK Comment", -1, null),
                createField("to_drop", TableFieldTypeEnum.STRING, "Drop me", 1, null),
                createField("to_keep", TableFieldTypeEnum.BOOLEAN, "Keep me", 1, "true"));
        List<CustomFieldDefinitionModel> newFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "New PK Comment", -1, null), // Modify comment
                createField("to_keep", TableFieldTypeEnum.BOOLEAN, "Keep me", 1, "true"), // Keep
                createField("added_col", TableFieldTypeEnum.DATE, "Added Date", 1, null) // Add
        );

        List<String> actualSqls = DorisTableDdlUtil.buildAlterTableSqls("test_db", "test_table", oldFields, newFields);

        // Order might vary depending on implementation details (add/modify vs drop
        // loops)
        // Check contents regardless of order
        assertEquals(3, actualSqls.size());
        assertTrue(actualSqls.contains(
                "ALTER TABLE `test_db`.`test_table` ADD COLUMN `added_col` DATETIME NULL COMMENT 'Added Date'"));
        assertTrue(actualSqls.contains(
                "ALTER TABLE `test_db`.`test_table` MODIFY COLUMN `id` INT NOT NULL COMMENT 'New PK Comment'"));
        assertTrue(actualSqls.contains("ALTER TABLE `test_db`.`test_table` DROP COLUMN `to_drop`"));
    }

    @Test
    @DisplayName("Build ALTER TABLE SQL should return empty list for no changes")
    void testBuildAlterTableSqls_NoChanges() {
        List<CustomFieldDefinitionModel> oldFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "Comment", -1, null));
        List<CustomFieldDefinitionModel> newFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "Comment", -1, null) // Identical
        );

        List<String> actualSqls = DorisTableDdlUtil.buildAlterTableSqls("test_db", "test_table", oldFields, newFields);
        assertTrue(actualSqls.isEmpty());
    }

    @Test
    @DisplayName("Build ALTER TABLE SQL should handle null/empty lists")
    void testBuildAlterTableSqls_NullEmptyLists() {
        List<CustomFieldDefinitionModel> fields = Collections.singletonList(
                createField("id", TableFieldTypeEnum.INTEGER, "ID", -1, null));

        // Old null
        List<String> addSqls = DorisTableDdlUtil.buildAlterTableSqls("test_db", "test_table", null, fields);
        assertEquals(1, addSqls.size());
        assertTrue(addSqls.get(0).contains("ADD COLUMN `id`"));

        // New null
        List<String> dropSqls = DorisTableDdlUtil.buildAlterTableSqls("test_db", "test_table", fields, null);
        assertEquals(1, dropSqls.size());
        assertTrue(dropSqls.get(0).contains("DROP COLUMN `id`"));

        // Both null
        List<String> noChangeSqls = DorisTableDdlUtil.buildAlterTableSqls("test_db", "test_table", null, null);
        assertTrue(noChangeSqls.isEmpty());

        // Both empty
        List<String> noChangeSqlsEmpty = DorisTableDdlUtil.buildAlterTableSqls("test_db", "test_table",
                Collections.emptyList(), Collections.emptyList());
        assertTrue(noChangeSqlsEmpty.isEmpty());
    }

    @Test
    @DisplayName("Build ALTER TABLE SQL should not generate change for only type/nullability diff (currently)")
    void testBuildAlterTableSqls_TypeOrNullabilityChange_NoOp() {
        List<CustomFieldDefinitionModel> oldFields = Arrays.asList(
                createField("col1", TableFieldTypeEnum.INTEGER, "Comment", -1, null) // INT, NOT NULL
        );
        List<CustomFieldDefinitionModel> newFields = Arrays.asList(
                createField("col1", TableFieldTypeEnum.STRING, "Comment", 1, null) // VARCHAR, NULL
        );

        List<String> actualSqls = DorisTableDdlUtil.buildAlterTableSqls("test_db", "test_table", oldFields, newFields);
        // Currently, only comment changes are detected for existing columns
        assertTrue(actualSqls.isEmpty(), "Expected no ALTER statements as only type/nullability changed");
    }
}