package com.cab21.delivery.dto.response;
import java.util.List;
import java.util.Map;

import com.cab21.delivery.dto.Meta;

public class GridResponse {

    private List<Map<String, Object>> data;
    private int lastRow;
    private List<String> secondaryColumnFields;

    /**
     * Global meta parameter.
     */
    // @NotNull
    private Meta meta = new Meta();

    public GridResponse() {
    }

    public GridResponse(List<Map<String, Object>> data, int lastRow, List<String> secondaryColumnFields, Long total) {
        this.data = data;
        this.lastRow = lastRow;
        this.secondaryColumnFields = secondaryColumnFields;
        this.meta.setTotal(total);
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public List<String> getSecondaryColumnFields() {
        return secondaryColumnFields;
    }

    public void setSecondaryColumns(List<String> secondaryColumnFields) {
        this.secondaryColumnFields = secondaryColumnFields;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}

