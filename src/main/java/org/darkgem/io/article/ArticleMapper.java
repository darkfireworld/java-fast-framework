package org.darkgem.io.article;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface ArticleMapper {

    List<Article> selectList(@Param("content") String content, @Param("type") Article.Type type);

    /**
     * 类型转换
     */
    @MappedTypes(Article.Type.class)
    class ArticleTypeHandler implements TypeHandler<Article.Type> {
        @Override
        public void setParameter(PreparedStatement ps, int i, Article.Type parameter, JdbcType jdbcType) throws SQLException {
            ps.setString(i, parameter.toString());
        }

        @Override
        public Article.Type getResult(ResultSet rs, String columnName) throws SQLException {
            return Article.Type.valueOf(rs.getString(columnName));
        }

        @Override
        public Article.Type getResult(ResultSet rs, int columnIndex) throws SQLException {
            return Article.Type.valueOf(rs.getString(columnIndex));
        }

        @Override
        public Article.Type getResult(CallableStatement cs, int columnIndex) throws SQLException {
            return Article.Type.valueOf(cs.getString(columnIndex));
        }
    }
}
