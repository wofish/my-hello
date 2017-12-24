package cn.myyy.hello.common.response;

public enum GlobalResponseEnum implements Message {
    SUCC("1000", "成功!"),
    FAIL("1001", "失败!"),
    ERROR_PARAM("1002", "传入参数校验失败"),
    ILLEGAL_REQUEST("1003", "非法请求"),
    NO_RESULT("1003", "没有结果")
    ;

    private String respCode;
    private String respMsg;

    GlobalResponseEnum(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }
}
