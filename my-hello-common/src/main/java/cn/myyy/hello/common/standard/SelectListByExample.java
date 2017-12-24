package cn.myyy.hello.common.standard;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 根据搜索对象返回列表结果集
 * @param <T>
 * @param <S>
 */
public interface SelectListByExample<T, S> {

    List<T> selectListByExample(@Param("search") S search);

}
