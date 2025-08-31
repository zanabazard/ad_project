package com.cab21.delivery.dto.request;

import java.io.Serializable;

public class DefaultParam implements Serializable {

    private String key;
    private Object value;

    public DefaultParam() {
    }

    public DefaultParam(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}

