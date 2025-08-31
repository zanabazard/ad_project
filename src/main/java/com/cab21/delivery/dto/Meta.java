package com.cab21.delivery.dto;

public class Meta {

    private int page;
    private int perpage;
    private Long total;
    private String sort;
    private String field;

    public Meta() {
    }

    public Meta(int page, int perpage, Long total, String sort, String field) {
        this.page = page;
        this.perpage = perpage;
        this.total = total;
        this.sort = sort;
        this.field = field;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}

