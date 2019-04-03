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
    private List<T> listObj;

    private Long total;

    public PageVO() {

    }

    public List<T> getListObj() {
        return listObj;
    }

    public void setListObj(List<T> listObj) {
        this.listObj = listObj;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
    public T popExpectOne(){
        return getListObj() != null && getListObj().size() == 1 ? getListObj().get(0) : null;
    }
    public T popOne(){
        return getListObj() != null ? getListObj().get(0) : null;
    }
}
