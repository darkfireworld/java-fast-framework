package org.darkgem.web.support.token;

import org.darkgem.web.support.msg.GeneralErrorCode;
import org.darkgem.web.support.msg.GeneralErrorCodeException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class TokenResolver implements HandlerMethodArgumentResolver {

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
        Token token = parameter.getParameterAnnotation(Token.class);
        String paramName = token.value();
        if (paramName == null || paramName.equals("")) {
            throw new GeneralErrorCodeException(GeneralErrorCode.SERVER_EXCEPTION, parameter.toString() + Token.class.getName() + "注解Value不能为空");
        }
        String paramValue = webRequest.getParameter(paramName);
        if (paramValue == null || paramValue.equals("")) {
            throw new GeneralErrorCodeException(GeneralErrorCode.TOKEN_INVALID, "参数" + paramName + "不能为空");
        }
        return paramValue;
    }
}
