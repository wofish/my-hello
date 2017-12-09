package cn.myyy.hello.common.dao.dao.standard;

import java.io.Serializable;

/**
 * 根据主键ID查询对象
 * @param <T>
 * @param <S>
 */
public interface SelectById<T, S extends Serializable> {

    T selectById(S id);

}
