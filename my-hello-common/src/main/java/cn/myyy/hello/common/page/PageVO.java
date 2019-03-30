package cn.myyy.hello.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * @project: my-hello
 * @date: 2018/01/29
 * @author: SUNYAFEI
 */
public class PageVO<T> implements Serializable{
    private static final long serialVersionUID = 1569490394750160594L;
    private List<T> rows;

    private Long total;

    public PageVO() {

    }

    public PageVO(List<T> rows, Long total) {
        this.rows = rows;
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * 如果结果集只有一个那么返回，其余则返回null
     * @return
     */
    public T getExpectOne(){
        if(getRows() == null || getRows().size() != 1){
            return null;
        }
        return getRows().get(0);
    }

    /**
     * 获取第一个
     * @return
     */
    public T getOne(){
        if(getRows() == null || getRows().size() == 0){
            return null;
        }
        return getRows().get(0);
    }
}
