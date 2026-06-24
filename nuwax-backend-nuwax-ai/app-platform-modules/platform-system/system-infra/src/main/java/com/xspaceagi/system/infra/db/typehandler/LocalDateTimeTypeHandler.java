package com.xspaceagi.system.infra.db.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;

/**
 * LocalDateTime 字段类型支持
 *
 * @author soddy
 */
@Component
@MappedTypes(LocalDateTime.class)
@MappedJdbcTypes(value = JdbcType.TIME, includeNullJdbcType = true)
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    private final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .appendOptional(DateTimeFormatter.ofPattern(" HH:mm:ss"))
            .appendOptional(DateTimeFormatter.ofPattern(".SSSSSS"))
            .toFormatter()
            .withResolverStyle(ResolverStyle.LENIENT);

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, LocalDateTime localDateTime,
                                    JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, localDateTime);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String target = resultSet.getString(columnName);
        if (target == null || "".equals(target)) {

            return null;
        }
        return LocalDateTime.parse(target, dateTimeFormatter);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String target = resultSet.getString(columnIndex);
        if (target == null || "".equals(target)) {

            return null;
        }
        return LocalDateTime.parse(target, dateTimeFormatter);
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        String target = callableStatement.getString(columnIndex);
        if (target == null || "".equals(target)) {

            return null;
        }
        return LocalDateTime.parse(target, dateTimeFormatter);
    }


}
