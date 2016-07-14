package org.darkgem.web.mvc.main;

import org.darkgem.io.article.Article;
import org.darkgem.io.article.ArticleIo;
import org.darkgem.web.support.handler.Token;
import org.darkgem.web.support.msg.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/MainCtrl")
public class MainCtrl {
    @Autowired
    ArticleIo articleIo;

    @Transactional
    @RequestMapping("/ask")
    public Object ask(@Token String token) {
        return Message.okMessage(articleIo.selectList(token, Article.Type.IT));
    }
}
