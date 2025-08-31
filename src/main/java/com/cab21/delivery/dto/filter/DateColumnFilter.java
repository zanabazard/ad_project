package com.cab21.delivery.dto.filter;

public class DateColumnFilter extends ColumnFilter {

    private String type;
    private String filter;
    private String filterTo;

    public DateColumnFilter() {
    }

    public DateColumnFilter(String type, String filter, String filterTo) {
        this.type = type;
        this.filter = filter;
        this.filterTo = filterTo;
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

    public String getFilterTo() {
        return filterTo;
    }

    public void setFilterTo(String filterTo) {
        this.filterTo = filterTo;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    @Override
    public String toString() {
        return "DateColumnFilter{" + "type=" + type + ", filter=" + filter + ", filterTo=" + filterTo + '}';
    }

}

