package com.up2date.training.repository;

public enum RoleEnum {
    //Declare here the possible roles allowed for the employees
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
