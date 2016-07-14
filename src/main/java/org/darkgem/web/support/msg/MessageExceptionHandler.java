package org.darkgem.web.support.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理
 */
public class MessageExceptionHandler extends AbstractHandlerExceptionResolver {
    Logger logger = LoggerFactory.getLogger(MessageExceptionHandler.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        logger.error(e.getMessage(), e);
        if (e instanceof MessageException) {
            MessageException messageException = (MessageException) e;
            mv.addObject(Message.generalErrorMessage(messageException.getCode(), messageException.getMessage()));
        } else {
            mv.addObject(Message.generalErrorMessage(MessageErrorCode.SERVER_EXCEPTION, "服务器出错"));
        }
        return mv;
    }
}
