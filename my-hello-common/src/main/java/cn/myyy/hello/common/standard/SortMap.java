package cn.myyy.hello.common.standard;

public class SortMap {

    private String sort;
    private String order;

    public SortMap() {
    }

    public SortMap(String sort, String order) {
        this.sort = sort;
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
