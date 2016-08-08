package org.darkgem.io.article;

import org.darkgem.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
@Component
public class ArticleIo {
    @Autowired
    ArticleMapper articleMapper;

    /**
     * 获取全部
     *
     * @return 文章列表
     */
    public List<Article> selectList() {
        return articleMapper.selectList();
    }

    /**
     * 新增文章
     *
     * @param article 文章
     */
    public void insert(Article article) {
        article.setId(String.valueOf(System.currentTimeMillis()));
        articleMapper.insert(article);
    }

    /**
     * 获取文章
     */
    @Nullable
    public Article select(String id) {
        return articleMapper.select(id);
    }
}
