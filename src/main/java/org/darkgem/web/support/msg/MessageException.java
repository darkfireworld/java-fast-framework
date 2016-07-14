package org.darkgem.web.support.msg;

/**
 * 消息错误异常
 */
public class MessageException extends RuntimeException {
    MessageErrorCode code;

    public MessageException(MessageErrorCode code, String s) {
        super(s);
        this.code = code;
    }

    public MessageErrorCode getCode() {
        return this.code;
    }

}
