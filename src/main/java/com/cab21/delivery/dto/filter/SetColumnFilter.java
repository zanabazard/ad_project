package com.cab21.delivery.dto.filter;

import java.util.List;

public class SetColumnFilter extends ColumnFilter {

    private List<String> values;

    public SetColumnFilter() {
    }

    public SetColumnFilter(List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "SetColumnFilter{" + "values=" + values + '}';
    }

}

