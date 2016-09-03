package org.darkgem.io.article;

/**
 * Created by Administrator on 2016/7/12.
 */
public class Article {
    String id;
    String content;
    boolean rm;

    //mybatis
    public Article() {
    }

    public Article(String id, String content, boolean rm) {
        this.id = id;
        this.content = content;
        this.rm = rm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRm() {
        return rm;
    }

    public void setRm(boolean rm) {
        this.rm = rm;
    }
}
