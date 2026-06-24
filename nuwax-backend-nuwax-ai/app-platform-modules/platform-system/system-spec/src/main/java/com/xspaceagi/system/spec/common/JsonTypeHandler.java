package com.xspaceagi.system.spec.common;

import com.xspaceagi.system.spec.jackson.JsonSerializeUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class JsonTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object object, JdbcType jdbcType) throws SQLException {
        if (Objects.nonNull(object)) {
            preparedStatement.setString(i, JsonSerializeUtil.toJSONStringGeneric(object));
        }
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        if (null != resultSet.getString(s)) {
            return JsonSerializeUtil.parseObjectGeneric(resultSet.getString(s));
        }
        return null;
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        if (null != resultSet.getString(i)) {
            return JsonSerializeUtil.parseObjectGeneric(resultSet.getString(i));
        }
        return null;
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        if (null != callableStatement.getString(i)) {
            return JsonSerializeUtil.parseObjectGeneric(callableStatement.getString(i));
        }
        return null;
    }
}
