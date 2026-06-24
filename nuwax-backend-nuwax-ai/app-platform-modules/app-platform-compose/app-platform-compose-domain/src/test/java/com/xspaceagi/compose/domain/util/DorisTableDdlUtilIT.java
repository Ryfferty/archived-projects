package com.xspaceagi.compose.domain.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;
import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.compose.sdk.enums.TableFieldTypeEnum;
import com.xspaceagi.system.spec.enums.YnEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * Integration tests for {@link DorisTableDdlUtil} that execute DDL against a
 * real Doris database.
 * NOTE: Requires a running Doris instance accessible at DORIS_JDBC_URL.
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ensure setup/teardown order
class DorisTableDdlUtilIT {

    // Database connection details (consider using a properties file or test
    // profile)
    // 从 application-dev.yml 获取
    private static final String DORIS_JDBC_URL_BASE = "jdbc:mysql://127.0.0.1:9030/?serverTimezone=Asia/Shanghai&characterEncoding=utf-8&allowMultiQueries=true&socketTimeout=300000&useSSL=false";
    private static final String DORIS_JDBC_URL_TEST_DB = "jdbc:mysql://127.0.0.1:9030/test_db?serverTimezone=Asia/Shanghai&characterEncoding=utf-8&allowMultiQueries=true&socketTimeout=300000&useSSL=false";
    private static final String DORIS_USER = "admin";
    private static final String DORIS_PASSWORD = ""; // 你的密码
    private static final String TEST_DB_NAME = "test_db";
    private static final String TEST_TABLE_NAME = "test_table";
    // 单节点开发环境下，副本数应该设置为1
    private static final int REPLICATION_NUM = 1;

    private static Connection baseConnection; // Connection without specific DB
    private static Connection testDbConnection; // Connection to test_db
    private CustomTableDefinitionModel tableModel;

    @BeforeAll
    static void setupDatabase() throws SQLException {
        log.debug("Setting up database for integration tests...");
        try {
            // Load the driver (optional for modern JDBC, but good practice)
            Class.forName("com.mysql.cj.jdbc.Driver");

            baseConnection = DriverManager.getConnection(DORIS_JDBC_URL_BASE, DORIS_USER, DORIS_PASSWORD);
            log.debug("Base connection established.");

            // Create test database if it doesn't exist
            try (Statement stmt = baseConnection.createStatement()) {
                log.debug("Creating database if not exists: " + TEST_DB_NAME);
                stmt.execute("CREATE DATABASE IF NOT EXISTS " + TEST_DB_NAME);
                log.debug("Database check/creation complete.");
            }

            // Establish connection to the specific test database
            testDbConnection = DriverManager.getConnection(DORIS_JDBC_URL_TEST_DB, DORIS_USER, DORIS_PASSWORD);
            log.debug("Connection to " + TEST_DB_NAME + " established.");

        } catch (ClassNotFoundException e) {
            log.error("MySQL JDBC Driver not found. Make sure it's in the classpath (scope=test).");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            log.error("Database connection failed: " + e.getMessage());
            log.error("Check if Doris is running at 127.0.0.1:9030 and credentials are correct.");
            throw e; // Re-throw to fail the setup
        }
        log.debug("Database setup complete.");
    }

    @AfterAll
    static void tearDownDatabase() throws SQLException {
        log.debug("Tearing down database...");
        // Close connections first
        if (testDbConnection != null && !testDbConnection.isClosed()) {
            testDbConnection.close();
            log.debug("Test DB connection closed.");
        }
        // Drop the test database carefully
        if (baseConnection != null) {
            try (Statement stmt = baseConnection.createStatement()) {
                log.debug("Dropping database: " + TEST_DB_NAME);
                // WARNING: Dropping the database can affect other tests or applications.
                // Consider only dropping the table in @AfterEach or manual cleanup.
                // stmt.execute("DROP DATABASE IF EXISTS " + TEST_DB_NAME);
                log.debug(
                        "Database cleanup skipped (manual cleanup recommended or drop table in @AfterEach/BeforeEach).");
            } finally {
                if (!baseConnection.isClosed()) {
                    baseConnection.close();
                    log.debug("Base connection closed.");
                }
            }
        }
        log.debug("Database teardown complete.");
    }

    @BeforeEach
    void setUpTable() throws SQLException {
        // Drop the table before each test to ensure isolation
        if (testDbConnection == null || testDbConnection.isClosed()) {
            fail("Test DB connection is not available in @BeforeEach");
        }
        try (Statement stmt = testDbConnection.createStatement()) {
            log.debug("Dropping table before test: " + TEST_DB_NAME + "." + TEST_TABLE_NAME);
            stmt.execute("DROP TABLE IF EXISTS `" + TEST_DB_NAME + "`.`" + TEST_TABLE_NAME + "`");
            log.debug("Table dropped.");
        } catch (SQLException e) {
            log.error("Failed to drop table " + TEST_DB_NAME + "." + TEST_TABLE_NAME + ": " + e.getMessage());
            throw e;
        }

        // Setup the table model for the test
        tableModel = new CustomTableDefinitionModel();
        tableModel.setDorisDatabase(TEST_DB_NAME);
        tableModel.setDorisTable(TEST_TABLE_NAME);
        tableModel.setTableDescription("Integration Test Table 'Desc'");
    }

    // Helper method to create a field (same as in unit test)
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

    // --- Integration Tests ---

    @Test
    @Order(1) // Execute CREATE first
    @DisplayName("Execute CREATE TABLE SQL for basic table")
    void testExecuteCreateTableSql_Basic() throws SQLException {
        List<CustomFieldDefinitionModel> fields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "Primary Key", -1, null),
                createField("name", TableFieldTypeEnum.STRING, "User Name", 1, "default_user"),
                createField("score", TableFieldTypeEnum.NUMBER, "User Score", 1, "99.5"),
                createField("is_active", TableFieldTypeEnum.BOOLEAN, "Activity Status", -1, "true"),
                createField("created_at", TableFieldTypeEnum.DATE, "Creation timestamp", -1, null));

        String actualSql = DorisTableDdlUtil.buildCreateTableSql(tableModel, fields);
        log.debug("Executing CREATE SQL:\n" + actualSql);

        assertDoesNotThrow(() -> {
            if (testDbConnection == null || testDbConnection.isClosed()) {
                fail("Test DB connection is not available for CREATE execution");
            }
            try (Statement stmt = testDbConnection.createStatement()) {
                stmt.execute(actualSql);
                log.debug("CREATE TABLE executed successfully.");
            }
        }, "CREATE TABLE statement failed to execute");

        // Optional: Add verification by describing the table
        // assertTableStructure(...)
    }

    @Test
    @Order(2) // Execute ALTER after CREATE
    @DisplayName("Execute ALTER TABLE SQL for adding, modifying, and dropping columns")
    void testExecuteAlterTableSqls() throws SQLException {
        if (testDbConnection == null || testDbConnection.isClosed()) {
            fail("Test DB connection is not available for ALTER execution");
        }
        // 1. Create initial table state (required for ALTER)
        List<CustomFieldDefinitionModel> initialFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "Old PK Comment", -1, null),
                createField("to_drop", TableFieldTypeEnum.STRING, "Drop me", 1, null),
                createField("to_keep", TableFieldTypeEnum.BOOLEAN, "Keep me", 1, "true"));
        String createSql = DorisTableDdlUtil.buildCreateTableSql(tableModel, initialFields);
        log.debug("Executing prerequisite CREATE SQL:\n" + createSql);
        assertDoesNotThrow(() -> {
            try (Statement stmt = testDbConnection.createStatement()) {
                stmt.execute(createSql);
                log.debug("Prerequisite CREATE TABLE executed successfully.");
            }
        }, "Prerequisite CREATE TABLE failed");

        // 2. Define the new desired state
        List<CustomFieldDefinitionModel> newFields = Arrays.asList(
                createField("id", TableFieldTypeEnum.INTEGER, "New PK Comment", -1, null), // Modify comment
                createField("to_keep", TableFieldTypeEnum.BOOLEAN, "Keep me", 1, "true"), // Keep
                createField("added_col", TableFieldTypeEnum.DATE, "Added Date", 1, null) // Add
        );

        // 3. Generate ALTER statements
        List<String> alterSqls = DorisTableDdlUtil.buildAlterTableSqls(
                tableModel.getDorisDatabase(),
                tableModel.getDorisTable(),
                initialFields, // Old state
                newFields // New state
        );

        assertFalse(alterSqls.isEmpty(), "Expected ALTER statements to be generated");
        log.debug("Generated ALTER SQLs: " + alterSqls);

        // 4. Execute ALTER statements
        assertDoesNotThrow(() -> {
            try (Statement stmt = testDbConnection.createStatement()) {
                for (String sql : alterSqls) {
                   log.debug("Executing ALTER SQL: " + sql);
                    // Note: Doris often prefers multiple changes in one ALTER statement,
                    // but executing separately is simpler for testing generated individual
                    // statements.
                    stmt.execute(sql);
                    log.debug("ALTER statement executed successfully: " + sql);
                }
            }
        }, "One or more ALTER TABLE statements failed to execute");

        // Optional: Verify final structure
        // assertTableStructureAfterAlter(...)
    }

    // TODO: Add more integration test cases for specific scenarios if needed
}