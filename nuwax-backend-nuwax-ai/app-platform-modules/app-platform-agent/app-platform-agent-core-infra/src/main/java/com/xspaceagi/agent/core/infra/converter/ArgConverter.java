package com.xspaceagi.agent.core.infra.converter;

import com.google.common.base.Joiner;
import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import com.xspaceagi.agent.core.spec.enums.DataTypeEnum;
import com.xspaceagi.compose.sdk.vo.define.TableFieldDefineVo;
import com.xspaceagi.mcp.sdk.dto.McpArgDto;
import com.xspaceagi.system.spec.enums.YnEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArgConverter {

    public static List<Arg> convertTableFieldsToArgs(List<TableFieldDefineVo> fieldVos) {
        List<Arg> args = new ArrayList<>();
        if (fieldVos == null) {
            return args;
        }
        fieldVos.forEach(field -> {
            Arg arg = new Arg();
            arg.setName(field.getFieldName());
            arg.setDescription(field.getFieldDescription());
            arg.setDataType(getDataType(field.getFieldType()));
            arg.setKey(field.getFieldName());
            arg.setBindValue(field.getDefaultValue());
            Boolean require = YnEnum.Y.getKey().equals(field.getNullableFlag()) ? false : true;
            arg.setRequire(require);
            arg.setSystemVariable(YnEnum.Y.getKey().equals(field.getSystemFieldFlag()));
            args.add(arg);
        });
        return args;
    }

    public static String convertArgsToSimpleTableStructure(List<TableFieldDefineVo> fieldDefineVos) {
        List<Arg> args = convertTableFieldsToArgs(fieldDefineVos);
        StringBuilder sb = new StringBuilder();
        if (args == null) {
            return sb.toString();
        }
        sb.append("CREATE TABLE IF NOT EXISTS `custom_table` (");
        args.forEach(arg -> {
            sb.append(arg.getName()).append(" ").append(getMysqlType(arg.getDataType())).append(" ");
            if (arg.isRequire()) {
                sb.append("NOT NULL ");
            }
            sb.append("COMMENT '").append(arg.getDescription().replace("'", "\\'")).append("'");
            sb.append(",");
        });
        return sb.substring(0, sb.length() - 1) + ")";
    }

    private static String getMysqlType(DataTypeEnum dataType) {
        switch (dataType) {
            case String:
                return "varchar(255)";
            case Integer:
                return "int(10)";
            case Number:
                return "bigDecimal";
            case Boolean:
                return "tinyint(4)";
        }
        return "datetime";
    }

    private static DataTypeEnum getDataType(Integer fieldType) {
        //1:String;2:Integer;3:Number;4:Boolean;5:Date
        switch (fieldType) {
            case 2:
                return DataTypeEnum.Integer;
            case 3:
                return DataTypeEnum.Number;
            case 4:
                return DataTypeEnum.Boolean;
            default:
                return DataTypeEnum.String;
        }
    }

    public static List<Arg> convertMcpArgsToArgs(List<McpArgDto> inputArgs) {
        if (inputArgs == null) {
            return new ArrayList<>();
        }
        return inputArgs.stream().map(mcpArgDto -> {
            Arg arg = new Arg();
            arg.setName(mcpArgDto.getName());
            arg.setDescription(mcpArgDto.getDescription());
            arg.setDataType(DataTypeEnum.valueOf(mcpArgDto.getDataType().name()));
            arg.setRequire(mcpArgDto.isRequire());
            arg.setBindValue(mcpArgDto.getBindValue());
            arg.setBindValueType(Arg.BindValueType.Input);
            arg.setSubArgs(convertMcpArgsToArgs(mcpArgDto.getSubArgs()));
            arg.setEnable(true);
            arg.setKey(mcpArgDto.getName());
            if (mcpArgDto.getEnums() != null) {
                arg.setDescription(arg.getDescription() + "；可选范围：" + Joiner.on(",").join(mcpArgDto.getEnums()));
            }
            return arg;
        }).collect(Collectors.toList());
    }

    public static Map<String, Object> convertArgsToJsonSchema(List<Arg> args) {
        return convertArgsToJsonSchema(args, false);
    }

    public static Map<String, Object> convertArgsToJsonSchema(List<Arg> args, boolean showFilePrompt) {
        List<String> required = new ArrayList<>();
        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.putAll(Map.of("type", "object", "properties", properties, "required", required));
        if (args != null && args.size() > 0) {
            for (Arg inputArg : args) {
                if (inputArg.getEnable() == null || !inputArg.getEnable()) {
                    continue;
                }
                if (inputArg.isRequire()) {
                    required.add(inputArg.getName());
                }
                DataTypeEnum dataType = inputArg.getDataType();
                if (dataType == null) {
                    dataType = DataTypeEnum.String;
                }
                if (dataType == DataTypeEnum.Object) {
                    properties.put(inputArg.getName(), convertArgsToJsonSchema(inputArg.getSubArgs()));
                    continue;
                }
                if (dataType == DataTypeEnum.Array_Object) {
                    properties.put(inputArg.getName(), Map.of("type", "array",
                            "items", convertArgsToJsonSchema(inputArg.getSubArgs())));
                    continue;
                }
                if (dataType.name().startsWith("Array_String") || dataType.name().startsWith("Array_Number")
                        || dataType.name().startsWith("Array_Boolean") || dataType.name().startsWith("Array_Integer")) {
                    properties.put(inputArg.getName(), Map.of("type", "array",
                            "description", inputArg.getDescription() == null ? inputArg.getName() : inputArg.getDescription(),
                            "items", Map.of("type", dataType.name().split("_")[1].toLowerCase())));
                    continue;
                }
                if (dataType.name().startsWith("Array_File")) {
                    properties.put(inputArg.getName(), Map.of("type", "array", "items", Map.of("type", "string", "description", generateFilePrompt(inputArg, showFilePrompt))));
                    continue;
                }
                if (dataType.name().startsWith("Array")) {
                    properties.put(inputArg.getName(), Map.of("type", "array", "items", Map.of("type", "string")));
                    continue;
                }

                String dataTypeStr = DataTypeEnum.String.name().toLowerCase();
                if (inputArg.getDataType() == DataTypeEnum.Number) {
                    dataTypeStr = DataTypeEnum.Number.name().toLowerCase();
                } else if (inputArg.getDataType() == DataTypeEnum.Boolean) {
                    dataTypeStr = DataTypeEnum.Boolean.name().toLowerCase();
                } else if (inputArg.getDataType() == DataTypeEnum.Integer) {
                    dataTypeStr = DataTypeEnum.Integer.name().toLowerCase();
                } else if (dataType.name().startsWith("File")) {
                    inputArg.setDescription(generateFilePrompt(inputArg, showFilePrompt));
                }
                properties.put(inputArg.getName(), Map.of("type", dataTypeStr, "description", inputArg.getDescription() == null ? inputArg.getName() : inputArg.getDescription()));
            }
        }
        return params;
    }

    private static String generateFilePrompt(Arg inputArg, boolean showFilePrompt) {
        DataTypeEnum dataType = inputArg.getDataType();
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(inputArg.getDescription())) {
            sb.append(inputArg.getDescription());
        } else {
            sb.append("文件（图片、音视频或文档等）URL地址");
        }
        if (showFilePrompt) {
            if (dataType == DataTypeEnum.File_Image || dataType == DataTypeEnum.Array_File_Image) {
                sb.append("，文件格式限制为图片类型");
            }
            if (dataType == DataTypeEnum.File_Audio || dataType == DataTypeEnum.Array_File_Audio) {
                sb.append("，文件格式限制为音频类型");
            }
            if (dataType == DataTypeEnum.File_Video || dataType == DataTypeEnum.Array_File_Video) {
                sb.append("，文件格式限制为视频类型");
            }
            if (dataType == DataTypeEnum.File_PPT || dataType == DataTypeEnum.Array_File_PPT) {
                sb.append("，文件格式限制为PPT类型");
            }
            if (dataType == DataTypeEnum.File_Doc || dataType == DataTypeEnum.Array_File_Doc) {
                sb.append("，文件格式限制为文档类型");
            }
            if (dataType == DataTypeEnum.File_PDF || dataType == DataTypeEnum.Array_File_PDF) {
                sb.append("，文件格式限制为PDF类型");
            }
            if (dataType == DataTypeEnum.File_Zip || dataType == DataTypeEnum.Array_File_Zip) {
                sb.append("，文件格式限制为ZIP类型");
            }
            if (dataType == DataTypeEnum.File_Excel || dataType == DataTypeEnum.Array_File_Excel) {
                sb.append("，文件格式限制为Excel类型");
            }
            if (dataType == DataTypeEnum.File_Code || dataType == DataTypeEnum.Array_File_Code) {
                sb.append("，文件格式限制为代码类型");
            }
            if (dataType == DataTypeEnum.File_Svg || dataType == DataTypeEnum.Array_File_Svg) {
                sb.append("，文件格式限制为SVG类型");
            }
            if (dataType == DataTypeEnum.File_Default || dataType == DataTypeEnum.Array_File_Default) {
                sb.append("，文件格式限制为任意类型");
            }
            sb.append("，使用后面定义的文件上传接口，上传成功后将文件的url放置在该字段。\n### 文件上传接口\n")
                    .append("- 接口地址：/api/file/upload\n")
                    .append("- 请求方式：POST\n")
                    .append("- 请求类型：multipart/form-data\n")
                    .append("- 请求参数（文件数据，必须）：file\n")
                    .append("- 响应数据结构：{\"code\":\"0000\",\"displayCode\":\"0000\",\"message\":\"\",\"data\":{\"url\":\"文件网络URL地址\"},\"success\":true}");
        }
        return sb.toString();
    }
}
