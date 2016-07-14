package org.darkgem.io.article;

/**
 * Created by Administrator on 2016/7/12.
 */
public class Article {
    String id;
    String content;
    Type type;

    public Article(String id, String content, Type type) {
        this.id = id;
        this.content = content;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        IT,
        LIFT;
    }
}
