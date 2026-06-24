package com.xspaceagi.compose.spec.constants;

/**
 * Doris配置常量
 */
public class DorisConfigContants {

    /**
     * 默认doris数据库
     */
    public static final String DEFAULT_DORIS_DATABASE = "agent_platform";

    /**
     * 默认doris表前缀
     */
    public static final String DEFAULT_DORIS_TABLE_PREFIX = "custom_table_";

    /**
     * 是否固定字段允许为空,true:固定为可空字段,false:不固定,根据字段定义的nullableFlag决定
     */
    public static final boolean FIXED_FIELD_NULLABLE = true;
    
    /**
     * 默认字符串长度,短文本默认值最大长度
     */
    public static final int DEFAULT_STRING_LENGTH = 255;

    /**
     * 默认查询限制
     */
    public static final Long DEFAULT_QUERY_LIMIT = 1000L;

    /**
     * 导入excel数据最大行数
     */
    public static final int IMPORT_EXCEL_DATA_MAX_ROWS = 1000000;
    /**
     * 导出excel数据最大行数
     */
    public static final int EXPORT_EXCEL_DATA_MAX_ROWS = 1000000;

    /**
     * 检查excel数据行数是否超过限制，最大支持100000行
     */
    public static final int IMPORT_EXCEL_DATA_MAX_ROWS_CHECK = 100000;

    /**
     * 批量导入数据时每批处理的数据条数
     */
    public static final int IMPORT_EXCEL_DATA_BATCH_SIZE = 100;

    /**
     * Excel数值精度限制（Excel最大精确显示15位数字）
     */
    public static final int EXCEL_MAX_PRECISION_DIGITS = 15;

    /**
     * Excel数值范围限制（超过此范围可能显示为科学计数法）
     */
    public static final double EXCEL_MAX_SAFE_NUMBER = 1e15;

    /**
     * 是否强制转换所有主键为字符串
     */
    public static final boolean FORCE_CONVERT_PRIMARY_KEY = true;

    /**
     * 是否启用智能长数值转换
     */
    public static final boolean ENABLE_SMART_NUMBER_CONVERSION = true;

    /**
     * Excel数值格式化策略
     * AUTO: 自动判断是否需要转换为字符串
     * FORCE_STRING: 强制将所有数值转换为字符串
     * PRESERVE_NUMBER: 保持数值格式，不进行转换
     */
    public static final String EXCEL_NUMBER_FORMAT_STRATEGY = "AUTO";

    /**
     * 是否为长数值添加单引号前缀（Excel中可防止科学计数法）
     */
    public static final boolean ADD_QUOTE_PREFIX_FOR_LONG_NUMBERS = false;

    /**
     * 自定义数值格式化阈值
     */
    public static final long CUSTOM_NUMBER_THRESHOLD = 1000000000000L; // 1万亿

    /**
     * JavaScript安全整数最大值 (2^53-1)
     */
    public static final long JS_MAX_SAFE_INTEGER = 9007199254740991L;

    /**
     * JavaScript安全整数最小值 -(2^53-1)
     */
    public static final long JS_MIN_SAFE_INTEGER = -9007199254740991L;

    /**
     * JavaScript数值的最大有效数字位数（超过此位数可能丢失精度）
     */
    public static final int JS_MAX_SAFE_DIGITS = 15;

    /**
     * JavaScript数值的安全范围阈值（考虑小数精度）
     */
    public static final double JS_SAFE_NUMBER_THRESHOLD = 1e14; // 10^14

    /**
     * 是否启用Web端智能数值转换（防止JavaScript精度丢失）
     */
    public static final boolean ENABLE_WEB_SMART_NUMBER_CONVERSION = true;

    /**
     * 根据id获取doris表名
     * 
     * @param id 表id
     * @return 表名
     */
    public static String obtainTableName(Long id) {
        return DEFAULT_DORIS_TABLE_PREFIX + id;
    }

}
