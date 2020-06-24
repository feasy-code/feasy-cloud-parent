package org.feasy.cloud.swagger.global;

import org.feasy.cloud.common.result.Result;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  用于定义全局相应码
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/24
 */
@RestController
@ApiResponses({@ApiResponse(code = 500, message = "服务器内部错误", response = Result.class)})
public class ResponseController {
}
