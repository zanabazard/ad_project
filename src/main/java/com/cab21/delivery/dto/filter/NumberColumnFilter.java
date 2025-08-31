package com.cab21.delivery.dto.filter;

import java.math.BigDecimal;

public class NumberColumnFilter extends ColumnFilter {

    private String type;
    private BigDecimal filter;
    private BigDecimal filterTo;

    public NumberColumnFilter() {
    }

    public NumberColumnFilter(String type, BigDecimal filter, BigDecimal filterTo) {
        this.type = type;
        this.filter = filter;
        this.filterTo = filterTo;
    }

    public String getFilterType() {
        return filterType;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getFilter() {
        return filter;
    }

    public BigDecimal getFilterTo() {
        return filterTo;
    }

    @Override
    public String toString() {
        return "NumberColumnFilter{" + "type=" + type + ", filter=" + filter + ", filterTo=" + filterTo + '}';
    }

}

