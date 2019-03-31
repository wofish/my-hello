package cn.myyy.hello.common.standard;

public class PageModel<S> {

    private Integer page;
    private Integer rows;
    private Integer total;
    private String[] sort;
    private String[] order;
    private S search;
    private SortMap[] sortMaps;

    public PageModel() {
        this.setPage(1);
        this.setRows(10);
    }
    public static <S> PageModel defaultSearch(S s){
        PageModel<S> pageModel = new PageModel<>();
        pageModel.setSearch(s);
        return pageModel;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public S getSearch() {
        return search;
    }

    public void setSearch(S search) {
        this.search = search;
    }

    public String[] getSort() {
        return sort;
    }

    public void setSort(String[] sort) {
        this.sort = sort;
    }

    public String[] getOrder() {
        return order;
    }

    public void setOrder(String[] order) {
        this.order = order;
    }

    public Integer getLastIndex(){
        return page * rows;
    }

    public Integer getFirstIndex(){
        return (page - 1) * rows;
    }

    public void setSortMaps(SortMap[] sortMaps) {
        this.sortMaps = sortMaps;
    }

    public SortMap[] getSortMaps() {
        if (this.sortMaps != null) {
            return this.sortMaps;
        }
        if (sort == null) {
            return null;
        }
        if (sort.length == 0) {
            return null;
        }
        if (order == null) {
            return null;
        }
        if (order.length == 0) {
            return null;
        }
        if (sort.length != order.length) {
            return null;
        }
        SortMap[] sortMaps = new SortMap[sort.length];
        for (int i = 0; i < sort.length; i++) {
            sortMaps[i] = new SortMap(sort[i], order[i]);
        }
        this.sortMaps = sortMaps;
        return this.sortMaps;
    }
}
