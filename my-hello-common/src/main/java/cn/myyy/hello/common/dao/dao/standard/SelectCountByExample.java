package cn.myyy.hello.common.dao.dao.standard;

import org.apache.ibatis.annotations.Param;

public interface SelectCountByExample<S> {

    Long selectCountByExample(@Param("search") S search);
}
