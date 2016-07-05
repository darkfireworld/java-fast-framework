package org.darkgem.io.beetle;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class BeetlIo {
    GroupTemplate gt;

    /**
     * 初始化Beetl
     */
    public BeetlIo() {
        try {
            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
            Configuration cfg = Configuration.defaultConfiguration();
            gt = new GroupTemplate(resourceLoader, cfg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取一个模版
     */
    public Template getTemplate(String tpl) {
        return gt.getTemplate(tpl);
    }
}
