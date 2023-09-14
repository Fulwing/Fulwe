package com.fulwin.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LongListTypeHandler extends BaseTypeHandler<List<Long>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Long> parameter, JdbcType jdbcType) throws SQLException {
        // Convert the List<Long> to a string and set it as a parameter
        ps.setString(i, String.join(",", (CharSequence) parameter));
    }

    @Override
    public List<Long> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // Retrieve the string from the database and convert it to a List<Long>
        String value = rs.getString(columnName);
        return convertToLongList(value);
    }

    @Override
    public List<Long> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // Retrieve the string from the database and convert it to a List<Long>
        String value = rs.getString(columnIndex);
        return convertToLongList(value);
    }

    @Override
    public List<Long> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // Retrieve the string from the database and convert it to a List<Long>
        String value = cs.getString(columnIndex);
        return convertToLongList(value);
    }

    private List<Long> convertToLongList(String value) {
        // Convert a comma-separated string to a List<Long>
        if (value != null && !value.isEmpty()) {
            String[] tokens = value.split(",");
            List<Long> result = new ArrayList<>();
            for (String token : tokens) {
                result.add(Long.parseLong(token));
            }
            return result;
        }
        return Collections.emptyList();
    }
}

