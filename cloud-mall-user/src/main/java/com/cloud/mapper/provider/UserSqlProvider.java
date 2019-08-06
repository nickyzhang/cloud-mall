package com.cloud.mapper.provider;

import com.cloud.common.core.constants.SymbolConstants;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

public class UserSqlProvider {

    public String findByCondition(Map<String,Object> conditions) {

        String sql = "SELECT user_id,username,password,salt,phone,email,status,create_date,update_date" +
                " FROM cloud_user";

        return MapUtils.isEmpty(conditions) ? sql : getSql(sql,conditions);
    }

    public String countByCondition(Map<String,Object> conditions) {
        String sql = "SELECT COUNT(1)" +
                " FROM cloud_user";
        if (MapUtils.isEmpty(conditions)) {
            return sql;
        }

        return MapUtils.isEmpty(conditions) ? sql : getSql(sql,conditions);
    }

    private String getSql(String sql, Map<String,Object> conditions) {
        StringBuilder sb = new StringBuilder(sql);
        boolean first = true;
        for(Map.Entry<String,Object> entry : conditions.entrySet()) {
            if (first) {
                sb.append(" WHERE ")
                    .append(entry.getKey())
                    .append(SymbolConstants.EQUALS_SYMBOL)
                    .append(SymbolConstants.SINGLE_QUOTES_SYMBOL)
                    .append(entry.getValue())
                    .append(SymbolConstants.SINGLE_QUOTES_SYMBOL);
                first = false;
            } else {
                sb.append(" AND ")
                    .append(entry.getKey())
                    .append(SymbolConstants.EQUALS_SYMBOL)
                    .append(SymbolConstants.SINGLE_QUOTES_SYMBOL)
                    .append(entry.getValue())
                    .append(SymbolConstants.SINGLE_QUOTES_SYMBOL);
            }
        }
        return sb.toString();
    }
}
