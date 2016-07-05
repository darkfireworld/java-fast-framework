package org.darkgem.web.mvc.main;

import com.alibaba.fastjson.JSONObject;
import org.darkgem.web.support.msg.Message;
import org.darkgem.web.support.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/MainCtrl")
public class MainCtrl {
    @Autowired
    JdbcTemplate template;


    @RequestMapping("/ask.do")
    public Object ask(@Token String token) {
        JSONObject ret = new JSONObject();
        ret.put("token", token);
        ret.put("query", template.queryForList("SELECT * FROM test"));
        return Message.okMessage(ret);
    }
}
