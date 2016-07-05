package org.darkgem.web.support.msg;

/**
 * Created by Administrator on 2015/4/3.
 */
public class GeneralErrorCodeException extends RuntimeException {
    GeneralErrorCode code;

    public GeneralErrorCodeException(GeneralErrorCode code, String s) {
        super(s);
        this.code = code;
    }

    public GeneralErrorCode getCode() {
        return this.code;
    }

}
