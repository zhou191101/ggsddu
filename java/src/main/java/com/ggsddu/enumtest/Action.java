package com.ggsddu.enumtest;

public enum Action {

    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private Action(String value) {
        this.value = value;
    }
}
