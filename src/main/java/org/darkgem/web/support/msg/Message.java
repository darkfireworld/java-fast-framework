package org.darkgem.web.support.msg;


/**
 * Logic 服务器交互交口
 * <ul>
 * <strong>错误代码</strong>
 * <li>1.通用错误代码按负数排序{@link MessageErrorCode}</li>
 * <li>2.正确执行为0</li>
 * <li>3.自定义错误为正数</li>
 * </ul>
 */
public class Message {

    /**
     * 错误数值
     */
    int code;
    /**
     * 具体的信息,大致的类型有Integer,String,Object,Array,null
     */
    Object msg;

    /**
     * 构造函数
     *
     * @param code 错误代码, 且必须大于等于0
     * @param msg  信息提示
     */
    private Message(int code, Object msg) {
        if (code <= 0) {
            throw new RuntimeException("构造函数的Code 必须要大于 0");
        }
        this.code = code;
        this.msg = msg;
    }

    /**
     * 通用错误代码
     *
     * @param code 错误代码{@link MessageErrorCode}
     * @param msg  错误信息
     */
    private Message(MessageErrorCode code, Object msg) {
        this.code = code.value();
        if (this.code >= 0) {
            throw new RuntimeException("构造函数的Code 必须要小于 0");
        }
        this.msg = msg;
    }

    /**
     * 正确执行
     *
     * @param msg 提示信息
     */
    private Message(Object msg) {
        this.code = 0;
        this.msg = msg;
    }

    /**
     * 正确执行
     *
     * @param msg 返回信息
     */
    public static Message okMessage(Object msg) {
        return new Message(msg);
    }

    /**
     * 通用错误代码
     *
     * @param code 通用错误代码{@link MessageErrorCode}
     * @param msg  提示信息
     */
    public static Message generalErrorMessage(MessageErrorCode code, Object msg) {
        return new Message(code, msg);
    }

    /**
     * 自定义错误代码
     *
     * @param code 错误代码, 必须大于0
     * @param msg  提示信息
     */
    public static Message selfErrorMessage(int code, Object msg) {
        if (code <= 0)
            throw new RuntimeException("Code 必须大于0");
        return new Message(code, msg);
    }

    /**
     * 是否是成功的消息
     */
    public boolean ok() {
        return code == 0;
    }

    /**
     * 获取代码
     *
     * @return 代码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置代码
     *
     * @param code 代码数值
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 设置提示信息
     *
     * @return 返回提示信息
     */
    public Object getMsg() {
        return msg;
    }

    /**
     * 设置提示信息
     *
     * @param msg 提示信息
     */
    public void setMsg(Object msg) {
        this.msg = msg;
    }

}
