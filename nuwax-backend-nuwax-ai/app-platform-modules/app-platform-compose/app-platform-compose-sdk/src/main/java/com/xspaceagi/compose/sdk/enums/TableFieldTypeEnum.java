package com.xspaceagi.compose.sdk.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.util.Arrays;

/**
 * 字段类型枚举，包含原始类型、对应的 Apache Doris 数据类型映射以及默认的完整类型定义
 * String -> VARCHAR -> VARCHAR(255)
 * Integer -> INT -> INT
 * Number -> DOUBLE -> DECIMAL(20,6) // 注意：之前是 DOUBLE，现在改为 DECIMAL 以提供更精确的数值类型
 * Boolean -> BOOLEAN -> TINYINT(1)
 * Date -> DATETIME-> DATETIME
 * PrimaryKey -> BIGINT -> BIGINT AUTO_INCREMENT // 主键类型，用于自增主键
 * MEDIUMTEXT -> MEDIUMTEXT -> MEDIUMTEXT // 文本类型，用于存储较长的文本数据
 */
@Getter
@Schema(description = "字段类型：1:String(VARCHAR(255));2:Integer(INT);3:Number(DECIMAL(20,6));4:Boolean(TINYINT(1));5:Date(DATETIME);6:PrimaryKey(BIGINT);7:MEDIUMTEXT(MEDIUMTEXT)")
public enum TableFieldTypeEnum {

    STRING(1, "String", "VARCHAR", "VARCHAR(255)", "VARCHAR", "VARCHAR(255)"),
    INTEGER(2, "Integer", "INT", "INT", "INT", "INT"),
    NUMBER(3, "Number", "DECIMAL", "DECIMAL(28,6)", "DECIMAL", "DECIMAL(28,6)"),
    BOOLEAN(4, "Boolean", "BOOLEAN", "TINYINT(1)", "TINYINT", "TINYINT(1)"),
    DATE(5, "Date", "DATETIME", "DATETIME", "DATETIME", "DATETIME"),
    PRIMARY_KEY(6, "PrimaryKey", "BIGINT", "BIGINT(20)", "BIGINT", "BIGINT(20)"),
    MEDIUMTEXT(7, "MediumText", "TEXT", "TEXT", "MEDIUMTEXT", "MEDIUMTEXT");

    private final Integer code;
    private final String name;
    private final String dorisType; // 基础 Doris 类型 (例如 VARCHAR, INT)
    private final String dorisDefinition; // 完整的默认 Doris 类型定义 (例如 VARCHAR(255), DECIMAL(20,6))
    private final String mysqlType; // 基础 MySQL 类型 (例如 VARCHAR, INT)
    private final String mysqlDefinition; // 完整的默认 MySQL 类型定义 (例如 VARCHAR(255), DECIMAL(20,6))

    TableFieldTypeEnum(Integer code, String name, String dorisType, String dorisDefinition, String mysqlType, String mysqlDefinition) {
        this.code = code;
        this.name = name;
        this.dorisType = dorisType;
        this.dorisDefinition = dorisDefinition;
        this.mysqlType = mysqlType;
        this.mysqlDefinition = mysqlDefinition;
    }

    /**
     * 根据Doris/MySQL 类型获取枚举值
     * 
     * @param dorisType Doris/MySQL类型
     * @return 枚举值
     */
    public static TableFieldTypeEnum tryGetByDorisType(String dorisType) {
        return Arrays.stream(TableFieldTypeEnum.values())
                .filter(type -> type.getDorisType().equals(dorisType))
                .findFirst()
                .orElse(null);
    }

}
