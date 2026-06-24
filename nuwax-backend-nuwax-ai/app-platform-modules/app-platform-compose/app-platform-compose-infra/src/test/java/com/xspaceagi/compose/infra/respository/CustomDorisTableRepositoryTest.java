package com.xspaceagi.compose.infra.respository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xspaceagi.compose.sdk.vo.doris.DorisTableDefinitionVo;

@ExtendWith(MockitoExtension.class)
class CustomDorisTableRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    // 如果 CustomDorisTableRepository 依赖 self 进行 getCreateTableDdl 调用，
    // 可能需要 @Spy 或其他方式处理，但这里我们假设 getCreateTableDdl 直接被 getTableDefinition 调用
    // 或者我们将测试逻辑放在 parseCreateTableDdl 使其可见 (不推荐修改源码可见性)
    // 这里选择通过 mock getTableDefinition 的依赖来测试其内部调用
    @InjectMocks
    private CustomDorisTableRepository customDorisTableRepository;

    private final String MOCK_DB = "test_db";
    private final String MOCK_TABLE = "agent_component_config";
    private String mockDorisCreateTableDdl;

    @BeforeEach
    void setUp() {
        // 模拟的 Doris SHOW CREATE TABLE 输出
        mockDorisCreateTableDdl = "CREATE TABLE `" + MOCK_DB + "`.`" + MOCK_TABLE + "` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',\n" +
                "  `_tenant_id` BIGINT NULL DEFAULT '1' COMMENT '商户ID',\n" +
                "  `name` VARCHAR(64) NULL COMMENT '节点名称',\n" +
                "  `icon` VARCHAR(255) NULL COMMENT '组件图标',\n" +
                "  `description` TEXT NULL COMMENT '组件描述',\n" +
                "  `agent_id` BIGINT NULL COMMENT 'AgentID',\n" +
                "  `type` VARCHAR(64) NOT NULL COMMENT '组件类型',\n" +
                "  `target_id` BIGINT NULL COMMENT '关联的组件ID',\n" +
                "  `bind_config` STRING NULL COMMENT '组件绑定配置 (Using STRING for JSON in Doris)',\n" +
                "  `exception_out` TINYINT NULL DEFAULT '0' COMMENT '异常是否抛出，中断主要流程',\n" +
                "  `fallback_msg` TEXT NULL COMMENT '异常时兜底内容',\n" +
                "  `modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Modified Time',\n" +
                "  `created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created Time'\n" +
                ") ENGINE=OLAP\n" +
                "DUPLICATE KEY(`id`, `_tenant_id`)\n" +
                "COMMENT '智能体组件配置 - Doris Version'\n" +
                "DISTRIBUTED BY HASH(`id`, `type`) BUCKETS 16\n" +
                "PROPERTIES (\n" +
                "    \"replication_num\" = \"1\",\n" +
                "    \"in_memory\" = \"false\",\n" +
                "    \"storage_format\" = \"V2\",\n" +
                "    \"function_column.sequence_type\" = \"auto_increment\"\n" +
                ");";
    }

    @Test
    void getTableDefinition_ShouldParseDorisDdlCorrectly() {
        // --- Arrange --- 
        // 1. Mock SHOW CREATE TABLE result
        when(jdbcTemplate.queryForMap(eq("SHOW CREATE TABLE `" + MOCK_DB + "`.`" + MOCK_TABLE + "`")))
                .thenReturn(Map.of("Create Table", mockDorisCreateTableDdl));

        // 2. Mock DESC table result (can be simplified if only testing parsing)
        List<Map<String, Object>> fieldsResult = new ArrayList<>(); // Populate if needed
        when(jdbcTemplate.queryForList(eq("DESC `" + MOCK_DB + "`.`" + MOCK_TABLE + "`")))
                .thenReturn(fieldsResult);

        // 3. Mock SHOW INDEX result (can be simplified if only testing parsing)
        List<Map<String, Object>> indexesResult = new ArrayList<>(); // Populate if needed
        when(jdbcTemplate.queryForList(eq("SHOW INDEX FROM `" + MOCK_DB + "`.`" + MOCK_TABLE + "`")))
                .thenReturn(indexesResult);

        // --- Act --- 
        DorisTableDefinitionVo actualDefinition = customDorisTableRepository.getTableDefinition(MOCK_DB, MOCK_TABLE);

        // --- Assert --- 
        assertNotNull(actualDefinition);

        // Verify fields parsed by parseCreateTableDdl
        assertEquals("智能体组件配置 - Doris Version", actualDefinition.getComment());
        assertEquals("OLAP", actualDefinition.getEngine());
        assertEquals(16, actualDefinition.getBuckets());
        assertEquals(1, actualDefinition.getReplicationNum()); // Parsed from properties
        assertEquals(Arrays.asList("id", "type"), actualDefinition.getDistributedKeys());
        assertEquals(Arrays.asList("id", "_tenant_id"), actualDefinition.getDuplicateKeys());

        // Verify properties map
        assertNotNull(actualDefinition.getProperties());
        assertEquals(4, actualDefinition.getProperties().size());
        assertEquals("1", actualDefinition.getProperties().get("replication_num"));
        assertEquals("false", actualDefinition.getProperties().get("in_memory"));
        assertEquals("V2", actualDefinition.getProperties().get("storage_format"));
        assertEquals("auto_increment", actualDefinition.getProperties().get("function_column.sequence_type"));

        // Verify the original DDL is stored
        assertEquals(mockDorisCreateTableDdl, actualDefinition.getCreateTableDdl());

        // Verify basic info
        assertEquals(MOCK_DB, actualDefinition.getDatabase());
        assertEquals(MOCK_TABLE, actualDefinition.getTable());

        // Fields and Indexes list can be asserted if mock data was provided for them
        // assertNotNull(actualDefinition.getFields());
        // assertNotNull(actualDefinition.getIndexes());
    }

    // TODO: Add more tests for edge cases, e.g., missing optional clauses in DDL,
    // different property formats, different key definitions.

} 