package cn.myyy.hello.common.standard;

public abstract class AbstractOrder {
    protected String sortType;
    protected String col;

    protected AbstractOrder(String sortType, String col) {
        this.sortType = sortType;
        this.col = col;
    }

    public String getSortType() {
        return sortType;
    }

    public String getCol() {
        return col;
    }
}
