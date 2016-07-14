package org.darkgem.io.article;

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

    public List<Article> selectList(String key, Article.Type type) {
        return articleMapper.selectList('%' + key + '%', type);
    }
}
