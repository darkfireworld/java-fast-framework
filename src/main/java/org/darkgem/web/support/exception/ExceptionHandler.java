package org.darkgem.web.support.exception;

import org.darkgem.web.support.msg.GeneralErrorCode;
import org.darkgem.web.support.msg.GeneralErrorCodeException;
import org.darkgem.web.support.msg.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理
 */
public class ExceptionHandler extends AbstractHandlerExceptionResolver {
    Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        logger.error(e.getMessage(), e);
        if (e instanceof GeneralErrorCodeException) {
            GeneralErrorCodeException exception = (GeneralErrorCodeException) e;
            mv.addObject(Message.generalErrorMessage(exception.getCode(), exception.getMessage()));
        } else {
            mv.addObject(Message.generalErrorMessage(GeneralErrorCode.SERVER_EXCEPTION, "服务器出错"));
        }
        return mv;
    }
}
