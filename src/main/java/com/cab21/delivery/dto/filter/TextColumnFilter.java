package com.cab21.delivery.dto.filter;

public class TextColumnFilter extends ColumnFilter {

    private String type;
    private String filter;

    public TextColumnFilter() {
    }

    public TextColumnFilter(String type, String filter) {
        this.type = type;
        this.filter = filter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    @Override
    public String toString() {
        return "TextColumnFilter{" + "type=" + type + ", filter=" + filter + '}';
    }

}
