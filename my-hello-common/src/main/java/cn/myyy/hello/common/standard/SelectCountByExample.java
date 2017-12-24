package cn.myyy.hello.common.standard;

import org.apache.ibatis.annotations.Param;

public interface SelectCountByExample<S> {

    Long selectCountByExample(@Param("search") S search);
}
