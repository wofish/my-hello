package cn.myyy.hello.common.dao.dao.standard;

import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface BatchInsert<T> {
    /**
     * 批量插入
     * @param list
     * @return
     */
    Integer batchInsert(@Param("list") Collection<T> list);
}
