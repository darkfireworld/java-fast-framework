package org.darkgem.web.support.handler;

import org.darkgem.web.support.msg.MessageErrorCode;
import org.darkgem.web.support.msg.MessageException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 解析Token
 */
public class TokenHandler implements HandlerMethodArgumentResolver {
    //默认token参数
    final static String TOKEN = "token";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Token token = parameter.getParameterAnnotation(Token.class);
        if (token != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String paramValue = webRequest.getParameter(TOKEN);
        if (paramValue == null || "".equals(paramValue.trim())) {
            throw new MessageException(MessageErrorCode.TOKEN_INVALID, String.format("参数%s不能为空", TOKEN));
        }
        return paramValue;
    }
}
