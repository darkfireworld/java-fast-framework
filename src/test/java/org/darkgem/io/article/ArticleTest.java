package org.darkgem.io.article;

import org.darkgem.SpringTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class ArticleTest extends SpringTest{

    @Autowired
    ArticleIo articleIo;

    @Test
    public void selectListTest() {
        List<Article> list = articleIo.selectList();
        Assert.assertTrue(list != null);
    }

    @Test
    public void insertTest() {
        Article article = new Article(null, "测试插入", true);
        articleIo.insert(article);
        Assert.assertTrue(article.getId() != null);
    }

    @Test
    public void selectTest() {
        Article article = new Article(null, "测试插入", true);
        articleIo.insert(article);
        Assert.assertTrue(article.getId() != null);
        Article get = articleIo.select(article.getId());
        Assert.assertTrue(get.getId().equals(article.getId()));
    }
}
