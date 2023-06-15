package com.wordify.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wordify.model.dto.common.BaseDto;
import com.wordify.repository.dao.mapper._DefinitionElementMapperBase;

public abstract class _DefinitionElementDaoBase<T extends BaseDto, M extends _DefinitionElementMapperBase<T>> extends _DaoBase{
    
    protected abstract M createMapper();

    public Map<Integer, List<T>> getElementMapByDefinitionIds(Connection conn, Set<Integer> definitionIds) throws SQLException {
        M mapper = createMapper();
        StringBuilder builder = new StringBuilder("SELECT id, definition_id, "); // カラム名を追加する
        String columnName = getColumnName();
        String tableName = getTableName(); // テーブル名を取得する
        builder.append(columnName).append(" FROM ").append(tableName).append(" WHERE definition_id IN (");
        for (int i = 0; i < definitionIds.size(); i++) {
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        String sql = builder.toString();
        System.out.println(sql);
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
    public int RegisterElement(Connection conn,String element,int definitionId) throws SQLException{
        int id;
        StringBuilder builder = new StringBuilder("INSERT INTO ");
        builder.append(getTableName()).append("(").append(getColumnName()).append(",definition_id").append(") VALUES (?,?);");
        String sql = builder.toString();
        System.out.println(sql);
        try(PreparedStatement pstmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1,element);
            pstmt.setInt(2, definitionId);
            id = pstmt.executeUpdate();
        }
        return id;
    }

    protected abstract String getColumnName();//カラム名を返す中小メソッド
    protected abstract String getTableName(); // テーブル名を返す抽象メソッド

}
