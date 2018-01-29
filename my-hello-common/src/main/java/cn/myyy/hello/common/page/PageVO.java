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
}
