package com.up2date.training.repository;

public enum RoleEnum {

    USER("User"),
    ADMIN("Admin");

    private final String displayValue;

    private RoleEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
