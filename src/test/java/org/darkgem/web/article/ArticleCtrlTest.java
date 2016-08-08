package org.darkgem.web.article;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.darkgem.SpringTest;
import org.darkgem.io.article.Article;
import org.darkgem.io.article.ArticleIo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

public class ArticleCtrlTest extends SpringTest {
    //路径
    final static String PATH = "/main/ArticleCtrl/";

    @Autowired
    ArticleIo articleIo;

    @Test
    public void getListTest() throws Exception {
        //插入一条数据
        articleIo.insert(new Article(null, "测试数据", false));
        //检测接口
        getMockMvc().perform(MockMvcRequestBuilders.post(PATH + "getList")).andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                JSONObject ret = JSON.parseObject(result.getResponse().getContentAsString());
                //ok
                Assert.assertTrue(ret.getInteger("code") == 0);
                //check list
                Assert.assertTrue(ret.getJSONArray("msg").size() > 0);
            }
        });
    }

    @Test
    public void getTest() throws Exception {
        final Article article = new Article(null, "测试数据", false);
        //插入一条数据
        articleIo.insert(article);
        //检测接口
        getMockMvc().perform(MockMvcRequestBuilders.post(PATH + "get").param("id", article.getId())).andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                JSONObject ret = JSON.parseObject(result.getResponse().getContentAsString());
                //ok
                Assert.assertTrue(ret.getInteger("code") == 0);
                //check
                Assert.assertTrue(ret.getJSONObject("msg").getString("id").equals(article.getId()));
            }
        });
    }


    @Test
    public void putTest() throws Exception {
        final String content = UUID.randomUUID().toString();
        final String token = "darkfireworld";
        //检测接口
        getMockMvc().perform(MockMvcRequestBuilders.post(PATH + "put").param("token", token).param("content", content)).andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                JSONObject ret = JSON.parseObject(result.getResponse().getContentAsString());
                //ok
                Assert.assertTrue(ret.getInteger("code") == 0);
                //check
                Assert.assertTrue(ret.getJSONObject("msg").getString("id") != null);
                Assert.assertTrue(ret.getJSONObject("msg").getString("content").equals(content));
            }
        });
    }
}
