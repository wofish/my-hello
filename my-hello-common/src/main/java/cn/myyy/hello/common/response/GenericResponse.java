package cn.myyy.hello.common.response;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 该类GenericResponse是一个通用业务返回模型。
 * 其中属性body不强制要求实现Serializable,
 * 但如果作为该类的实例作为DUBBO服务的返回类型，则必须实现Serializable接口。
 * 用法：
 * 1. 返回一个成功的响应对象，body为null。
 * GenericResponse GenericResponse = GenericResponse.SUCCESS;
 *    
 * 2. 返回一个失败的响应对象，body为null。
 * GenericResponse GenericResponse = GenericResponse.FAIL;
 *    
 * 3. 返回一个参数校验失败的响应对象，body为null。
 * GenericResponse GenericResponse = GenericResponse.ERROR_PARAM;
 *    
 * 4. 返回一个请求不合法的响应对象，body为null。
 * GenericResponse GenericResponse = GenericResponse.ILLEGAL_REQUEST;
 *    
 * 5. 返回一个指定响应消息的响应对象，body为null。
 * MeMessage meMessage = null;//自定义的消息对象
 * GenericResponse GenericResponse =  new GenericResponse(meMessage);
 *    
 * 6. 返回一个指定业务对象的响应对象。响应消息为GlobalResponseEnum.SUCC
 * T body = null;//自定义的业务对象
 * GenericResponse<T> GenericResponse =  new GenericResponse<T>(body);
 *    
 * 7. 返回一个指定消息及业务对象的响应对象。
 * MeMessage meMessage = null;//自定义的消息对象
 * T body = null;//自定义的业务对象
 * GenericResponse<T> GenericResponse =  new GenericResponse<T>(meMessage, body);
 *
 * @author SUNYAFEI
 * @version : 1.0
 * @see GlobalResponseEnum
 */
public class GenericResponse<T> implements Serializable {

    /**
     * 通用业务返回对象模型常量,body 都为null。
     */
    public static final GenericResponse SUCCESS = new GenericResponse(GlobalResponseEnum.SUCC);
    public static final GenericResponse FAIL = new GenericResponse(GlobalResponseEnum.FAIL);
    public static final GenericResponse ERROR_PARAM = new GenericResponse(GlobalResponseEnum.ERROR_PARAM);
    public static final GenericResponse ILLEGAL_REQUEST = new GenericResponse(GlobalResponseEnum.ILLEGAL_REQUEST);
    public static final GenericResponse NO_RESULT = new GenericResponse(GlobalResponseEnum.NO_RESULT);

    /**
     * 响应码
     */
    private String respCode;
    /**
     * 响应信息
     */
    private String respMsg;

    /**
     * 业务对象
     */
    private T body;

    /**
     * 默认无参构造器。
     * 子类可重写该构造器。但不允许使用者直接使用。
     */
    protected GenericResponse() {

    }

    /**
     * 使用{@link Message}对象构造GenericResponse的实例。
     * 其中属性body的值null。
     * 业务系统应定义一个enum类型的类实现{@link Message}接口。
     * @param meMessage
     * @see Message
     */
    public GenericResponse(Message meMessage) {
        this(meMessage, null);
    }

    /**
     * 使用业务对象T body构造code>GenericResponse的实例。
     * 其中默认使用{@link GlobalResponseEnum#SUCC}对象设置respCode,respMsg属性。
     * @param body 业务对象
     * @see GlobalResponseEnum#SUCC
     */
    public GenericResponse(T body) {
        this(GlobalResponseEnum.SUCC, body);
    }

    /**
     * 使用{@link Message}对象及业务对象T body构造code>GenericResponse的实例。
     * @param meMessage
     * @param body
     */
    public GenericResponse(Message meMessage, T body) {
        this(meMessage.getRespCode(), meMessage.getRespMsg(), body);
    }

    /**
     * 子类可根据需要重写此方法。
     * 但不允许使用者直接使用。
     * @param respCode
     * @param respMsg
     * @param body
     */
    protected GenericResponse(String respCode, String respMsg, T body) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.body = body;
    }

    public String getRespCode() {
        return respCode;
    }

    protected GenericResponse<T> setRespCode(String respCode) {
        this.respCode = respCode;
        return this;
    }

    public String getRespMsg() {
        return respMsg;
    }

    /**
     * 该方法用于重新设置属性respMsg的值。
     * 支持链式访问。
     * @param respMsg
     * @return 返回一个当前的实例对象。
     * @throws IllegalArgumentException 当尝试改变全局实例对象
     *                                  {@link GenericResponse#SUCCESS}
     *                                  {@link GenericResponse#FAIL}
     *                                  {@link GenericResponse#ERROR_PARAM}
     *                                  {@link GenericResponse#ILLEGAL_REQUEST}
     *                                  {@link GenericResponse#NO_RESULT}
     *                                  的respMsg的值时，抛出该异常
     */
    public GenericResponse<T> setRespMsg(String respMsg) {
//      checkGlobalResponse();
        this.respMsg = respMsg;
        return this;
    }

    /**
     * 该方法用于追加属性respMsg的值。
     * 支持链式访问。
     * 例子：
     * MeMessage meMessage = null;//业务系统定义的MeMessage对象。
     * GenericResponse GenericResponse = new GenericResponse(meMessage);// 其中respMsg的值为："授权失败"
     * GenericResponse.appendRespMsg("SC0001");
     * 那么respMsg的值为 : "授权失败[SC0001]"
     * @param args
     * @return 返回一个当前的实例对象。
     * @throws IllegalArgumentException 当尝试改变全局实例对象
     *                                  {@link GenericResponse#SUCCESS}
     *                                  {@link GenericResponse#FAIL}
     *                                  {@link GenericResponse#ERROR_PARAM}
     *                                  {@link GenericResponse#ILLEGAL_REQUEST}
     *                                  {@link GenericResponse#NO_RESULT}
     *                                  的respMsg的值时，抛出该异常
     */
    public GenericResponse<T> appendRespMsg(Object... args) {
//      checkGlobalResponse();
        return setRespMsg(getRespMsg() + Arrays.asList(args));
    }

    /**
     * 使用MessageFormat格式化respMsg中的点位符.
     * 支持链式访问.
     * @param args
     * @return
     */
    public GenericResponse<T> formatRespMsg(Object... args) {
        checkGlobalResponse();
        return setRespMsg(MessageFormat.format(getRespMsg(), args));
    }

    private void checkGlobalResponse() {
        if (isGlobalResponse()) {
            throw new IllegalArgumentException("You can't change the respMsg of global response!");
        }
    }

    public T getBody() {
        return body;
    }

    /**
     * 设置body的值。支持链式访问。
     * @param body
     * @return 返回一个当前的实例对象
     */
    protected GenericResponse<T> setBody(T body) {
        this.body = body;
        return this;
    }

    /**
     * 判断该响应是否成功。
     * @return true 成功响应
     * false 失败响应
     * @see GlobalResponseEnum#SUCC
     */
    public boolean success() {
        return GlobalResponseEnum.SUCC.getRespCode().equals(getRespCode());
    }

    /**
     * 子类可根据需求重写此方法.
     * @return
     */
    protected boolean isGlobalResponse() {
        return this == SUCCESS || this == FAIL || this == ERROR_PARAM || this == ILLEGAL_REQUEST || this == NO_RESULT;
    }
}
