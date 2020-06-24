package org.feasy.cloud.mysql.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Int数组分割转换
 * @author yangxiaohui
 * @date Create by 2020/1/6 11:59 上午
 */
public class IntArrayTypeHandler extends BaseTypeHandler<int[]> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, int[] parameter, JdbcType jdbcType) throws SQLException {
        List<String> list = new ArrayList<>();
        for (int item : parameter) {
            list.add(String.valueOf(item));
        }
        ps.setString(i, String.join(",", list));
    }

    @Override
    public int[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        }
        return Arrays.stream(str.split(",")).mapToInt(Integer::valueOf).toArray();
    }

    @Override
    public int[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        }
        return Arrays.stream(str.split(",")).mapToInt(Integer::valueOf).toArray();
    }

    @Override
    public int[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        }
        return Arrays.stream(str.split(",")).mapToInt(Integer::valueOf).toArray();
    }
}
