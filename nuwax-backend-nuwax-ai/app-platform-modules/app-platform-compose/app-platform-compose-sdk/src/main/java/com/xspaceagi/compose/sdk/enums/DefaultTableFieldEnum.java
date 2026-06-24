package com.xspaceagi.compose.sdk.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.xspaceagi.compose.sdk.vo.define.TableFieldDefineVo;
import lombok.Getter;

/**
 * 系统默认的固定字段,所有的表定义必须包含这些字段
 */
@Getter
public enum DefaultTableFieldEnum {

    ID("id", "主键ID", TableFieldTypeEnum.PRIMARY_KEY, -1, null, -1, 1),
    UID("uid", "用户唯一标识", TableFieldTypeEnum.STRING, -1, null, -1, 1),
    USER_NAME("user_name", "用户名", TableFieldTypeEnum.STRING, 1, null, -1, 1),
    NICK_NAME("nick_name", "用户昵称", TableFieldTypeEnum.STRING, 1, null, -1, 1),
    AGENT_ID("agent_id", "智能体唯一标识", TableFieldTypeEnum.STRING, 1, null, -1, 1),
    AGENT_NAME("agent_name", "智能体名称", TableFieldTypeEnum.STRING, 1, null, -1, 1),
    CREATED("created", "数据创建时间", TableFieldTypeEnum.DATE, -1, "CURRENT_TIMESTAMP", -1, 1),
    MODIFIED("modified", "数据修改时间", TableFieldTypeEnum.DATE, -1, "CURRENT_TIMESTAMP", -1, 1);

    /**
     * 字段名
     */
    private final String fieldName;

    /**
     * 字段描述
     */
    private final String fieldDescription;

    /**
     * 字段类型：1:String;2:Integer;3:Number;4:Boolean;5:Date
     */
    private final TableFieldTypeEnum fieldType;

    /**
     * 是否可为空：1-可空 -1-非空
     */
    private final Integer nullableFlag;

    /**
     * 默认值
     */
    private final String defaultValue;

    /**
     * 是否唯一：1-唯一 -1-非唯一
     */
    private final Integer uniqueFlag;

    /**
     * 是否启用：1-启用 -1-禁用
     */
    private final Integer enabledFlag;

    DefaultTableFieldEnum(String fieldName,
            String fieldDescription,
            TableFieldTypeEnum fieldType,
            Integer nullableFlag,
            String defaultValue,
            Integer uniqueFlag,
            Integer enabledFlag) {
        this.fieldName = fieldName;
        this.fieldDescription = fieldDescription;
        this.fieldType = fieldType;
        this.nullableFlag = nullableFlag;
        this.defaultValue = defaultValue;
        this.uniqueFlag = uniqueFlag;
        this.enabledFlag = enabledFlag;
    }

    /**
     * 将枚举转换为字段定义VO
     */
    public TableFieldDefineVo toFieldDefineVo() {
        return TableFieldDefineVo.builder()
                .id(null)
                .systemFieldFlag(1)
                .fieldName(this.fieldName)
                .fieldDescription(this.fieldDescription)
                .fieldType(this.fieldType.getCode())
                .nullableFlag(this.nullableFlag)
                .defaultValue(this.defaultValue)
                .uniqueFlag(this.uniqueFlag)
                .enabledFlag(this.enabledFlag)
                .build();
    }

    


    /**
     * 获取所有默认字段
     * 
     * @return 默认字段列表
     */
    public static List<TableFieldDefineVo> getAllDefaultFields() {
        return Arrays.stream(DefaultTableFieldEnum.values())
                .map(DefaultTableFieldEnum::toFieldDefineVo)
                .collect(Collectors.toList());
    }

    /**
     * 根据字段名获取对应的枚举值
     * 
     * @param fieldName 字段名
     * @return 对应的枚举值,如果找不到则返回null
     */
    public static DefaultTableFieldEnum getEnumByFieldName(String fieldName) {
        return Arrays.stream(DefaultTableFieldEnum.values())
                .filter(field -> field.getFieldName().equals(fieldName))
                .findFirst()
                .orElse(null);
    }

}
