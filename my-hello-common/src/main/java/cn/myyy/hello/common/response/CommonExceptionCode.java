package cn.myyy.hello.common.response;

/**
 * @description: ${description}
 * @date: 2019/04/17 21:49
 * @author: syfei49@163.com
 * @sine: 1.0.0
 */
public enum CommonExceptionCode implements IExceptionCode<String,String,String>{
    SYSTEM_ERROR("9999", "系统异常，请联系管理员"),
    ID_NULL_ERROR("9998", "ID没有传"),
    CHECK_YOUR_PARAM_ERROR("9997", "请校验您的参数"),
    UPDATE_LINE_IS_ZERO_WARN("9996","warn", "[{0}]更新条目为空"),
    ;
    private String code;
    /**
     * error.warn
     */
    private String type;
    private String message;

    CommonExceptionCode(String code, String message) {
        this.code = code;
        this.type = "error";
        this.message = message;
    }

    CommonExceptionCode(String code, String type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }
    public String getCode() {
        return code;
    }
    public String getType() {
        return type;
    }
    public String getMessage() {
        return message;
    }
}
