package org.darkgem.web.support.msg;

/**
 * 通用的错误代码, 为<strong>负整数</strong>
 */
public enum GeneralErrorCode {
    /**
     * 服务器错误,错误代码code:-1
     *
     * @value
     */
    SERVER_EXCEPTION(-1),
    /**
     * 内部服务验证失败
     */
    AUTH_FAIL(-2),
    /**
     * TOKEN失效/不存在,错误代码code:-3
     *
     * @value
     */
    TOKEN_INVALID(-3);

    int value = 0;

    GeneralErrorCode(int i) {
        this.value = i;
    }

    public int value() {
        return value;
    }
}
