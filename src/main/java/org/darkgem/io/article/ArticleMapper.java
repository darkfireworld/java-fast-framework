package org.darkgem.io.article;

import org.apache.ibatis.annotations.Param;
import org.darkgem.annotation.Nullable;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface ArticleMapper {
    /**
     * 获取全部
     *
     * @return 文章列表
     */
    List<Article> selectList();

    /**
     * 新增文章
     *
     * @param article 文章
     */
    void insert(@Param("article") Article article);

    /**
     * 获取文章
     */
    @Nullable
    Article select(@Param("id") String id);
}
