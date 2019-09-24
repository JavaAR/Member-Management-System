package cn.DeepBlue.contorller;


import cn.DeepBlue.Exception.UserNoLoginException;
import cn.DeepBlue.pojo.dto.Dto;
import cn.DeepBlue.utils.DtoUtil;
import cn.DeepBlue.utils.ErrorCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionResolverContorller {


    /**
     * 全局运行时异常处理
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Dto handleException(Exception e){
            return DtoUtil.returnFail("系统异常", ErrorCode.AUTH_UNKNOWN);
    }

    /**
     * 业务异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(UserNoLoginException.class)
    @ResponseBody
    public Dto handleOpdRuntimeException(UserNoLoginException e){
        return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_AUTHENTICATION_FAILED);
    }
}
