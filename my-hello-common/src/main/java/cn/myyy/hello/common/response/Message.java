package cn.myyy.hello.common.response;

import java.io.Serializable;

/**
 * 通用业务消息接口.
 * 各业务系统应定义一个枚举类实现该接口。
 * 约定：
 * 以"1"开头的为系统级别的消息。
 * 各业务系统实现此接口时，不能使用以"1"开头的respCode.
 *
 * @author SUNYAFEI
 * @version : 1.0
 */
public interface Message extends Serializable {

    String getRespCode();

    String getRespMsg();

}
