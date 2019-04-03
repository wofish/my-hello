package cn.myyy.hello.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * @project: my-hello
 * @date: 2018/01/29
 * @author: SUNYAFEI
 */
public class PageVO<T extends Serializable> implements Serializable{
    private static final long serialVersionUID = 1569490394750160594L;
    private List<T> listObj;

    private Long total;

    public PageVO() {

    }

    public PageVO(List<T> listObj, Long total) {
        this.listObj = listObj;
        this.total = total;
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

    /**
     * 如果结果集只有一个那么返回，其余则返回null
     * @return
     */
    public T getExpectOne(){
        if(getListObj() == null || getListObj().size() != 1){
            return null;
        }
        return getListObj().get(0);
    }

    /**
     * 获取第一个
     * @return
     */
    public T getOne(){
        if(getListObj() == null || getListObj().size() == 0){
            return null;
        }
        return getListObj().get(0);
    }
}
