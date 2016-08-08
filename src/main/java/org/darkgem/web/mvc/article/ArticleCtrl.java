package org.darkgem.web.mvc.article;

import org.darkgem.io.article.Article;
import org.darkgem.io.article.ArticleIo;
import org.darkgem.web.support.handler.Token;
import org.darkgem.web.support.msg.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main/ArticleCtrl")
public class ArticleCtrl {
    @Autowired
    ArticleIo articleIo;

    /**
     * 获取文章列表
     *
     * @return {@link Message}
     * <pre>
     *     {code:0,msg:[{@link Article}]}
     * </pre>
     */
    @RequestMapping("/getList")
    public Message getList() {
        return Message.okMessage(articleIo.selectList());
    }

    /**
     * 获取文章
     *
     * @param id 文章
     * @return {@link Message}
     * <pre>
     *     {code:0,msg:{@link Article}}
     *     {code:1,msg:"不存在对应的文章"}
     * </pre>
     */
    @RequestMapping("/get")
    public Message get(@RequestParam("id") String id) {
        Article article = articleIo.select(id);
        if (article == null) {
            return Message.selfErrorMessage(1, "不存在对应的文章");
        }
        return Message.okMessage(article);
    }

    /**
     * 新增文章
     *
     * @param token   密钥
     * @param content 文章内容
     * @return {@link Message}
     * <pre>
     *     {code:0,msg:{@link Article}}
     *     {coode:1,msg:"token错误"}
     * </pre>
     */
    @RequestMapping("/put")
    public Message put(@Token String token, @RequestParam("content") String content) {
        if (!"darkfireworld".equals(token)) {
            return Message.selfErrorMessage(1, "token错误");
        }
        Article article = new Article(null, content, false);
        articleIo.insert(article);
        return Message.okMessage(article);
    }
}
