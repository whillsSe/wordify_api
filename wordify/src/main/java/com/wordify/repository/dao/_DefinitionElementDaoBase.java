package com.wordify.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wordify.model.dto.BaseDto;
import com.wordify.repository.dao.mapper._DefinitionElementMapperBase;

public abstract class _DefinitionElementDaoBase<T extends BaseDto, M extends _DefinitionElementMapperBase<T>> {
    
    protected abstract M createMapper();

    public Map<Integer, List<T>> getElementMapByDefinitionIds(Connection conn, Set<Integer> definitionIds) throws SQLException {
        M mapper = createMapper();
        StringBuilder builder = new StringBuilder("SELECT id, definition_id, "); // カラム名を追加する
        String columnName = getColumnName();
        String tableName = getTableName(); // テーブル名を取得する
        builder.append(columnName).append("FROM ").append(tableName).append(" WHERE definition_id IN (");
        for (int i = 0; i < definitionIds.size(); i++) {
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        String sql = builder.toString();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Integer param : definitionIds) {
                pstmt.setInt(index, param);
                index++;
            }
            ResultSet rs = pstmt.executeQuery();
            return mapper.mapToMap(rs);
        }
    }
    protected abstract String getColumnName();//カラム名を返す中小メソッド
    protected abstract String getTableName(); // テーブル名を返す抽象メソッド

}
