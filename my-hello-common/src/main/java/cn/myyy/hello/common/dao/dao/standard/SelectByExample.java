package cn.myyy.hello.common.dao.dao.standard;

import org.apache.ibatis.annotations.Param;

/**
 * 根据搜索对象查返回单一结果集
 * @param <T>
 * @param <S>
 */
public interface SelectByExample<T, S> {

    T selectByExample(@Param("search") S search);

}
