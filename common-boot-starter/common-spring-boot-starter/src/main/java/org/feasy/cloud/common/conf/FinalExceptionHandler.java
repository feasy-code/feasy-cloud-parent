package org.feasy.cloud.common.conf;

import org.feasy.cloud.common.result.Result;
import org.feasy.cloud.common.result.ResultBuilder;
import org.feasy.cloud.common.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 主要是解决未被全局异常处理的错误
 * @author yangxiaohui
 */
@Slf4j
@RestController
public class FinalExceptionHandler implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }
    @GetMapping(value = "/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<Object> error(HttpServletRequest request, HttpServletResponse response) {
        log.error("response error,httpCode:" + response.getStatus());
        // 错误处理逻辑
        int status = response.getStatus();
        if (status == 404) {
            return ResultBuilder.error(ResultEnum.NOT_FOUND_RESOURCE,"小伙子你有点调皮哦！(*^▽^*)");
        } else if (status == 500) {
            return ResultBuilder.error(ResultEnum.SYSTEM_ERROR,"小伙子你麻烦大了！(*^▽^*)");
        } else if (status >= 100 && status < 200) {
            return ResultBuilder.error(ResultEnum.UNKNOWN_ERROR);
        } else if (status >= 300 && status < 400) {
            // 请求被重定向，需要进一步操作
            return ResultBuilder.error(ResultEnum.REQUEST_REDIRECT);
        } else if (status >= 400 && status < 500) {
            // 未找到相应资源
            return ResultBuilder.error(ResultEnum.NOT_FOUND_RESOURCE,"未找到有效的资源【"+status+"】！");
        } else {
            return ResultBuilder.error(ResultEnum.UNKNOWN_ERROR, "小伙子你麻烦大了！(*^▽^*)");
        }
    }
}
